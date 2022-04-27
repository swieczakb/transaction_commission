package pl.swieczakb.transaction_commission.transaction.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import pl.swieczakb.transaction_commission.transaction.adapter.clientservice.FakeClientService;
import pl.swieczakb.transaction_commission.transaction.adapter.exchangerate.ExchangeRateClient;
import pl.swieczakb.transaction_commission.transaction.adapter.exchangerate.ExternalExchangeRateService;
import pl.swieczakb.transaction_commission.transaction.adapter.transactiondb.DatabaseTransactionService;
import pl.swieczakb.transaction_commission.transaction.adapter.transactiondb.TransactionJpaRepository;
import pl.swieczakb.transaction_commission.transaction.domain.CommissionCalculator;
import pl.swieczakb.transaction_commission.transaction.domain.DefaultCommissionRule;
import pl.swieczakb.transaction_commission.transaction.domain.HighTurnoverDiscountCommissionRule;
import pl.swieczakb.transaction_commission.transaction.domain.SpecialClientDiscountCommissionRule;
import pl.swieczakb.transaction_commission.transaction.domain.port.ClientRepository;
import pl.swieczakb.transaction_commission.transaction.domain.port.ExchangeRateService;
import pl.swieczakb.transaction_commission.transaction.domain.port.TransactionRepository;
import pl.swieczakb.transaction_commission.transaction.domain.port.TransactionService;

@Profile("local")
@Configuration
public class LocalTransactionModule {

  @Bean
  public TransactionService transactionService(CommissionCalculator commissionCalculator,
      TransactionRepository transactionRepository, ExchangeRateService exchangeRateService,
      ClientRepository clientRepository) {
    return new TransactionService(transactionRepository, exchangeRateService, commissionCalculator,
        clientRepository);
  }

  @Bean
  public ExchangeRateService exchangeRateService(RestTemplate restTemplate,
      @Value("${exchange.rate.service.url}") String baseUrl) {
    final ExchangeRateClient exchangeRateClient = new ExchangeRateClient(restTemplate, baseUrl);
    return new ExternalExchangeRateService(exchangeRateClient);
  }

  @Bean
  public CommissionCalculator commissionCalculator(ClientRepository clientService,
      TransactionRepository transactionRepository) {
    final HighTurnoverDiscountCommissionRule rootRule = new HighTurnoverDiscountCommissionRule(
        transactionRepository);
    SpecialClientDiscountCommissionRule specialClientDiscountCommissionRule = new SpecialClientDiscountCommissionRule(clientService);
    DefaultCommissionRule defaultCommissionRule = new DefaultCommissionRule();
    rootRule.setNextRule(specialClientDiscountCommissionRule);
    specialClientDiscountCommissionRule.setNextRule(defaultCommissionRule);
    return new CommissionCalculator(rootRule);
  }

  @Bean
  public ClientRepository fakeClientRepository() {
    return new FakeClientService();
  }

  @Bean
  public TransactionRepository transactionRepository(TransactionJpaRepository repository) {
    return new DatabaseTransactionService(repository);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}

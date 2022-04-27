package pl.swieczakb.transaction_commission.transaction.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import pl.swieczakb.transaction_commission.transaction.adapter.clientservice.FakeClientService;
import pl.swieczakb.transaction_commission.transaction.adapter.exchangerate.ExchangeRateClient;
import pl.swieczakb.transaction_commission.transaction.adapter.exchangerate.ExternalExchangeRateService;
import pl.swieczakb.transaction_commission.transaction.adapter.transactiondb.FakeTransactionRepository;
import pl.swieczakb.transaction_commission.transaction.domain.CommissionCalculator;
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
    return new CommissionCalculator(transactionRepository, clientService);
  }

  @Bean
  public ClientRepository fakeClientRepository() {
    return new FakeClientService();
  }

  @Bean
  public TransactionRepository fakeTransactionRepository() {
    return new FakeTransactionRepository();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
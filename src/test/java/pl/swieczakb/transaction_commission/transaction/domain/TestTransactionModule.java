package pl.swieczakb.transaction_commission.transaction.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import pl.swieczakb.transaction_commission.transaction.adapter.clientservice.ClientEntity;
import pl.swieczakb.transaction_commission.transaction.adapter.clientservice.FakeClientService;
import pl.swieczakb.transaction_commission.transaction.adapter.exchangerate.FakeExchangeRateService;
import pl.swieczakb.transaction_commission.transaction.adapter.exchangerate.NewestExchangeRateResponse;
import pl.swieczakb.transaction_commission.transaction.adapter.transactiondb.DatabaseTransactionService;
import pl.swieczakb.transaction_commission.transaction.adapter.transactiondb.TransactionJpaRepository;
import pl.swieczakb.transaction_commission.transaction.domain.port.ClientRepository;
import pl.swieczakb.transaction_commission.transaction.domain.port.ExchangeRateService;
import pl.swieczakb.transaction_commission.transaction.domain.port.TransactionRepository;
import pl.swieczakb.transaction_commission.transaction.domain.port.TransactionService;

@Configuration
@Profile("test")
public class TestTransactionModule {

  @Bean
  public TransactionService testTransactionService(CommissionCalculator commissionCalculator,
      TransactionRepository transactionRepository, ExchangeRateService exchangeRateService,
      ClientRepository clientRepository) {
    return new TransactionService(transactionRepository, exchangeRateService, commissionCalculator,
        clientRepository);
  }

  @Bean
  public ExchangeRateService testExchangeRateService(ObjectMapper mapper) throws IOException {
    return new FakeExchangeRateService(
        mapper.readValue(new ClassPathResource("data/exchangeRateResponse.json").getFile(),
            NewestExchangeRateResponse.class));
  }

  @Bean
  public CommissionCalculator testCommissionCalculator(ClientRepository clientService,
      TransactionRepository transactionRepository) {
    final HighTurnoverDiscountCommissionRule rootRule = new HighTurnoverDiscountCommissionRule(
        transactionRepository);
    SpecialClientDiscountCommissionRule specialClientDiscountCommissionRule = new SpecialClientDiscountCommissionRule(
        clientService);
    DefaultCommissionRule defaultCommissionRule = new DefaultCommissionRule();
    rootRule.setNextRule(specialClientDiscountCommissionRule);
    specialClientDiscountCommissionRule.setNextRule(defaultCommissionRule);
    return new CommissionCalculator(rootRule);
  }

  @Bean
  public ClientRepository testFakeClientRepository() {
    return new FakeClientService(List.of(new ClientEntity(1, false), new ClientEntity(42, true)));
  }

  @Bean
  public TransactionRepository testTransactionRepository(TransactionJpaRepository repository) {
    return new DatabaseTransactionService(repository);
  }

  @Bean
  public ObjectMapper testObjectMapper(){
    return new ObjectMapper().registerModule(new JavaTimeModule());
  }
}

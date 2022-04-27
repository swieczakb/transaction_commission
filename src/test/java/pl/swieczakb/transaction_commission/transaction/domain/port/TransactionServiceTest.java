package pl.swieczakb.transaction_commission.transaction.domain.port;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import pl.swieczakb.transaction_commission.transaction.adapter.api.DomainException;
import pl.swieczakb.transaction_commission.transaction.adapter.clientservice.ClientEntity;
import pl.swieczakb.transaction_commission.transaction.adapter.clientservice.FakeClientService;
import pl.swieczakb.transaction_commission.transaction.adapter.exchangerate.FakeExchangeRateService;
import pl.swieczakb.transaction_commission.transaction.adapter.exchangerate.NewestExchangeRateResponse;
import pl.swieczakb.transaction_commission.transaction.adapter.transactiondb.FakeTransactionRepository;
import pl.swieczakb.transaction_commission.transaction.domain.CommissionCalculator;
import pl.swieczakb.transaction_commission.transaction.domain.model.Amount;
import pl.swieczakb.transaction_commission.transaction.domain.model.ClientId;
import pl.swieczakb.transaction_commission.transaction.domain.model.ExchangeCurrency;
import pl.swieczakb.transaction_commission.transaction.domain.model.OriginCurrency;
import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCommission;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionDate;

class TransactionServiceTest {

  private final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
  private TransactionRepository transactionRepository;
  private ExchangeRateService exchangeRateService;
  private CommissionCalculator commissionCalculator;
  private ClientRepository clientRepository;
  private TransactionService transactionService;

  @BeforeEach
  void setUp() throws IOException {
    this.transactionRepository = new FakeTransactionRepository();
    this.exchangeRateService = new FakeExchangeRateService(
        OBJECT_MAPPER.readValue(new ClassPathResource("data/exchangeRateResponse.json").getFile(),
            NewestExchangeRateResponse.class));
    this.clientRepository = new FakeClientService(
        List.of(new ClientEntity(1, false), new ClientEntity(42, true)));
    this.commissionCalculator = new CommissionCalculator(transactionRepository, clientRepository);
    this.transactionService = new TransactionService(transactionRepository, exchangeRateService,
        commissionCalculator, clientRepository);
  }

  @Test
  void shouldPropagateTransactionForSpecialClient() throws DomainException {
    //given
    final Transaction transaction = Transaction.of(TransactionDate.of(LocalDate.of(2020, 4, 1)),
        Amount.of(new BigDecimal("100.00")), OriginCurrency.of("EUR"), ClientId.of(42L));

    //when
    final TransactionCommission result = transactionService.propagateTransaction(transaction);

    //then
    assertThat(result.getAmount()).isEqualTo(Amount.of(new BigDecimal("0.05")));
    assertThat(result.getCurrencyCode()).isEqualTo("EUR");
  }

  @Test
  void shouldPropagateTransactionWithNormalPrice() throws DomainException {
    //given
    final Transaction transaction = Transaction.of(TransactionDate.of(LocalDate.of(2020, 4, 1)),
        Amount.of(new BigDecimal("100.00")), OriginCurrency.of("EUR"), ClientId.of(1L));

    //when
    final TransactionCommission result = transactionService.propagateTransaction(transaction);

    //then
    assertThat(result.getAmount()).isEqualTo(Amount.of(new BigDecimal("0.50")));
    assertThat(result.getCurrencyCode()).isEqualTo("EUR");
  }

  @Test
  void shouldPropagateTransactionWithHighTurnoverDiscount() throws DomainException {
    //given
    final Transaction transaction = Transaction.of(TransactionDate.of(LocalDate.of(2020, 4, 1)),
        Amount.of(new BigDecimal("999.00")), OriginCurrency.of("EUR"), ClientId.of(1L));
    transactionRepository.save(transaction.getDate(), transaction.getAmount(),
        ExchangeCurrency.of(Currency.getInstance("EUR")), transaction.getClientId());

    //when
    final TransactionCommission result = transactionService.propagateTransaction(transaction);

    //then
    assertThat(result.getAmount()).isEqualTo(Amount.of(new BigDecimal("0.03")));
    assertThat(result.getCurrencyCode()).isEqualTo("EUR");
  }
}
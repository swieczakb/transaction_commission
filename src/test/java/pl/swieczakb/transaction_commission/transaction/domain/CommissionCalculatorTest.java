package pl.swieczakb.transaction_commission.transaction.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.swieczakb.transaction_commission.transaction.domain.model.Amount;
import pl.swieczakb.transaction_commission.transaction.domain.model.ClientId;
import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCommission;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCurrency;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionDate;
import pl.swieczakb.transaction_commission.transaction.domain.port.ClientRepository;
import pl.swieczakb.transaction_commission.transaction.domain.port.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class CommissionCalculatorTest {

  @Mock
  private ClientRepository clientService;

  @Mock
  private TransactionRepository transactionRepository;

  private CommissionCalculator commissionCalculator;

  @BeforeEach
  void setUp() {
    final HighTurnoverDiscountCommissionRule rootRule = new HighTurnoverDiscountCommissionRule(
        transactionRepository);
    SpecialClientDiscountCommissionRule specialClientDiscountCommissionRule = new SpecialClientDiscountCommissionRule(
        clientService);
    DefaultCommissionRule defaultCommissionRule = new DefaultCommissionRule();
    rootRule.setNextRule(specialClientDiscountCommissionRule);
    specialClientDiscountCommissionRule.setNextRule(defaultCommissionRule);
    commissionCalculator = new CommissionCalculator(rootRule);
  }

  @Test
  void shouldCalculateDefaultCommission() {
    //given
    final ClientId givenClientId = ClientId.of(1L);
    final TransactionDate givenTransactionDate = TransactionDate.of(LocalDate.now());
    final Transaction givenTransaction = new Transaction(givenTransactionDate,
        Amount.of(BigDecimal.valueOf(500)), TransactionCurrency.of("EUR"), givenClientId);
    when(transactionRepository.hasClientHighTurnoverDiscount(givenClientId,
        givenTransactionDate)).thenReturn(false);
    when(clientService.isSpecialClient(givenClientId)).thenReturn(false);

    //when
    final TransactionCommission result = commissionCalculator.calculate(givenTransaction);

    //then
    assertThat(result.getAmount().getValue()).isEqualTo(
        BigDecimal.valueOf(2.50).setScale(2, RoundingMode.CEILING));
  }

  @Test
  void shouldCalculateCommissionForClientWithHighTurnover() {
    //given
    final ClientId givenClientId = ClientId.of(1L);
    final TransactionDate givenTransactionDate = TransactionDate.of(LocalDate.now());
    final Transaction givenTransaction = new Transaction(givenTransactionDate,
        Amount.of(BigDecimal.valueOf(500)), TransactionCurrency.of("EUR"), givenClientId);
    when(transactionRepository.hasClientHighTurnoverDiscount(givenClientId,
        givenTransactionDate)).thenReturn(true);

    //when
    final TransactionCommission result = commissionCalculator.calculate(givenTransaction);

    //then
    assertThat(result.getAmount().getValue()).isEqualTo(BigDecimal.valueOf(0.03));
  }

  @Test
  void shouldCalculateCommissionForClientWithSpecialDiscount() {
    //given
    final ClientId givenClientId = ClientId.of(1L);
    final TransactionDate givenTransactionDate = TransactionDate.of(LocalDate.now());
    final Transaction givenTransaction = new Transaction(givenTransactionDate,
        Amount.of(BigDecimal.valueOf(500)), TransactionCurrency.of("EUR"), givenClientId);
    when(transactionRepository.hasClientHighTurnoverDiscount(givenClientId,
        givenTransactionDate)).thenReturn(false);
    when(clientService.isSpecialClient(givenClientId)).thenReturn(true);

    //when
    final TransactionCommission result = commissionCalculator.calculate(givenTransaction);

    //then
    assertThat(result.getAmount().getValue()).isEqualTo(BigDecimal.valueOf(0.05));
  }
}
package pl.swieczakb.transaction_commission.transaction.domain;

import java.math.BigDecimal;
import pl.swieczakb.transaction_commission.transaction.domain.model.Amount;
import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCommission;
import pl.swieczakb.transaction_commission.transaction.domain.port.ClientRepository;
import pl.swieczakb.transaction_commission.transaction.domain.port.TransactionRepository;

public class CommissionCalculator {
  private static final BigDecimal MIN_VALUE = BigDecimal.valueOf(0.05);
  private final TransactionRepository transactionRepository;
  private final ClientRepository clientRepository;

  public CommissionCalculator(
      TransactionRepository transactionRepository,
      ClientRepository clientRepository) {
    this.transactionRepository = transactionRepository;
    this.clientRepository = clientRepository;
  }

  public TransactionCommission calculate(Transaction transaction) {
    if (transactionRepository.hasClientHighTurnoverDiscount(transaction.getClientId(),
        transaction.getDate())) {
      return TransactionCommission.of(BigDecimal.valueOf(0.03), transaction.getCurrency());
    }
    if (clientRepository.isSpecialClient(transaction.getClientId())) {
      return TransactionCommission.of(BigDecimal.valueOf(0.05), transaction.getCurrency());
    }

    final Amount amount = transaction.getAmount();
    final BigDecimal commission = amount.countPercentage(BigDecimal.valueOf(0.5));
    if (commission.compareTo(MIN_VALUE) <= 0) {
      return TransactionCommission.of(MIN_VALUE, transaction.getCurrency());
    }
    return TransactionCommission.of(commission, transaction.getCurrency());
  }
}

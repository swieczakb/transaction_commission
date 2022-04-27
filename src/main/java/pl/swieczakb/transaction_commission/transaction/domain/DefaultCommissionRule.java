package pl.swieczakb.transaction_commission.transaction.domain;

import java.math.BigDecimal;
import pl.swieczakb.transaction_commission.transaction.domain.model.Amount;
import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCommission;

public class DefaultCommissionRule extends CommissionRule {

  @Override
  TransactionCommission calculate(Transaction transaction) {
    final Amount amount = transaction.getAmount();
    final BigDecimal commission = amount.countPercentage(BigDecimal.valueOf(0.5));
    if (commission.compareTo(CommissionConstants.MINIMAL_COMMISSION.getAmount()) <= 0) {
      return TransactionCommission.of(CommissionConstants.MINIMAL_COMMISSION.getAmount(),
          transaction.getCurrency());
    }
    return TransactionCommission.of(commission, transaction.getCurrency());
  }
}

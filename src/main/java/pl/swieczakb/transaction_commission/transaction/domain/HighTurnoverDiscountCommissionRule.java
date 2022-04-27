package pl.swieczakb.transaction_commission.transaction.domain;

import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCommission;
import pl.swieczakb.transaction_commission.transaction.domain.port.TransactionRepository;

public class HighTurnoverDiscountCommissionRule extends CommissionRule {

  private final TransactionRepository transactionRepository;

  public HighTurnoverDiscountCommissionRule(
      TransactionRepository transactionRepository) {
    super();
    this.transactionRepository = transactionRepository;
  }

  @Override
  TransactionCommission calculate(Transaction transaction) {
    if (transactionRepository.hasClientHighTurnoverDiscount(transaction.getClientId(),
        transaction.getDate())) {
      return TransactionCommission.of(
          CommissionConstants.HIGH_TURNOVER_DISCOUNT_COMMISSION.getAmount(),
          transaction.getCurrency());
    }
    return nextRule.calculate(transaction);
  }
}

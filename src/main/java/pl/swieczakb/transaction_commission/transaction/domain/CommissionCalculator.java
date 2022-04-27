package pl.swieczakb.transaction_commission.transaction.domain;

import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCommission;

public class CommissionCalculator {

  private final CommissionRule chainOfRules;

  public CommissionCalculator(
      CommissionRule chainOfRules) {
    this.chainOfRules = chainOfRules;
  }

  public TransactionCommission calculate(Transaction transaction) {
    return chainOfRules.calculate(transaction);
  }
}

package pl.swieczakb.transaction_commission.transaction.domain;

import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCommission;

public abstract class CommissionRule {

  protected CommissionRule nextRule;

  public void setNextRule(CommissionRule nextRule) {
    this.nextRule = nextRule;
  }

  abstract TransactionCommission calculate(Transaction transaction);

}

package pl.swieczakb.transaction_commission.transaction.domain;

import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCommission;
import pl.swieczakb.transaction_commission.transaction.domain.port.ClientRepository;

public class SpecialClientDiscountCommissionRule extends CommissionRule {

  private final ClientRepository clientRepository;

  public SpecialClientDiscountCommissionRule(
      ClientRepository clientRepository) {
    super();
    this.clientRepository = clientRepository;
  }

  @Override
  TransactionCommission calculate(Transaction transaction) {
    if (clientRepository.isSpecialClient(transaction.getClientId())) {
      return TransactionCommission.of(
          CommissionConstants.SPECIAL_CLIENT_DISCOUNT_COMMISSION.getAmount(),
          transaction.getCurrency());
    }
    return nextRule.calculate(transaction);
  }
}

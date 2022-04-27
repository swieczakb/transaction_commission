package pl.swieczakb.transaction_commission.transaction.adapter.api;

import java.math.BigDecimal;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCommission;

public class DelegateTransactionResponse {
  private final BigDecimal amount;
  private final String currency;

  public DelegateTransactionResponse(BigDecimal amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  public static DelegateTransactionResponse of(TransactionCommission transactionCommission) {
    return new DelegateTransactionResponse(transactionCommission.getAmount().getValue(), transactionCommission.getCurrencyCode());
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }
}

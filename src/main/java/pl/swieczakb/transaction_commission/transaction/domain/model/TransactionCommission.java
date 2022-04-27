package pl.swieczakb.transaction_commission.transaction.domain.model;


import java.math.BigDecimal;

public class TransactionCommission {
  private final Amount amount;
  private final TransactionCurrency currency;

  public TransactionCommission(
      Amount amount,
      TransactionCurrency currency) {
    this.amount = amount;
    this.currency = currency;
  }

  public static TransactionCommission of(BigDecimal amount, TransactionCurrency currency) {
    return new TransactionCommission(Amount.of(amount), currency);
  }

  public String getCurrencyCode(){
    return currency.getCurrency().getCurrencyCode();
  }

  public Amount getAmount() {
    return amount;
  }
}

package pl.swieczakb.transaction_commission.transaction.domain.model;


import java.math.BigDecimal;

public class TransactionCommission {
  private final Amount amount;
  private final OriginCurrency currency;

  public TransactionCommission(
      Amount amount,
      OriginCurrency currency) {
    this.amount = amount;
    this.currency = currency;
  }

  public static TransactionCommission of(BigDecimal amount, OriginCurrency currency) {
    return new TransactionCommission(Amount.of(amount), currency);
  }

  public String getCurrencyCode(){
    return currency.getCurrency().getCurrencyCode();
  }

  public Amount getAmount() {
    return amount;
  }
}

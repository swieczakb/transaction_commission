package pl.swieczakb.transaction_commission.transaction.domain.model;

public class Transaction {

  private final TransactionDate date;
  private final Amount amount;
  private final OriginCurrency currency;
  private final ClientId clientId;

  public Transaction(
      TransactionDate date,
      Amount amount,
      OriginCurrency currency,
      ClientId clientId) {
    this.date = date;
    this.amount = amount;
    this.currency = currency;
    this.clientId = clientId;
  }

  public TransactionDate getDate() {
    return date;
  }

  public Amount getAmount() {
    return amount;
  }

  public OriginCurrency getCurrency() {
    return currency;
  }

  public ClientId getClientId() {
    return clientId;
  }
}

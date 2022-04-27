package pl.swieczakb.transaction_commission.transaction.domain.model;

import pl.swieczakb.transaction_commission.transaction.domain.port.ClientRepository;

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

  public static Transaction of(TransactionDate transactionDate, Amount amount,
      OriginCurrency currency, ClientId clientId) {
    return new Transaction(transactionDate, amount, currency, clientId);
  }

  public void validate(ClientRepository clientRepository) throws ValidationException {
    date.validate();
    amount.validate();
    currency.validate();
    clientId.validate(clientRepository);
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

package pl.swieczakb.transaction_commission.transaction.adapter.transactiondb;

import java.math.BigDecimal;
import java.time.LocalDate;
import pl.swieczakb.transaction_commission.transaction.domain.model.Amount;
import pl.swieczakb.transaction_commission.transaction.domain.model.ClientId;
import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCurrency;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionDate;

public class TransactionEntity {

  private final BigDecimal amount;

  private final String currency;

  private final LocalDate executionDate;

  private final long clientId;

  public TransactionEntity(BigDecimal amount, String currency, LocalDate executionDate,
      long clientId) {
    this.amount = amount;
    this.currency = currency;
    this.executionDate = executionDate;
    this.clientId = clientId;
  }

  public static TransactionEntity of(TransactionDate transactionDate, Amount amount,
      TransactionCurrency currency, ClientId clientId) {
    return new TransactionEntity(amount.getValue(), currency.getCurrency().getCurrencyCode(),
        transactionDate.getDate(), clientId.getId());
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public LocalDate getExecutionDate() {
    return executionDate;
  }

  public long getClientId() {
    return clientId;
  }

  public Transaction toDomain() {
    return new Transaction(TransactionDate.of(executionDate), Amount.of(amount),
        TransactionCurrency.of(currency), ClientId.of(clientId));
  }
}

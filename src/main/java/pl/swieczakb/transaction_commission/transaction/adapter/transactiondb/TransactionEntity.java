package pl.swieczakb.transaction_commission.transaction.adapter.transactiondb;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import pl.swieczakb.transaction_commission.transaction.domain.model.Amount;
import pl.swieczakb.transaction_commission.transaction.domain.model.ClientId;
import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCurrency;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionDate;

@Entity
@Table(name = "TRANSACTION")
public class TransactionEntity {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  private BigDecimal amount;

  private String currency;

  @Column(name = "execution_date")
  private LocalDate executionDate;

  //Here can be relation as foreign key to Client table
  @Column(name = "client_id")
  private Long clientId;

  protected TransactionEntity() {
  }

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

  public Long getId() {
    return id;
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

  public Long getClientId() {
    return clientId;
  }

  public Transaction toDomain() {
    return new Transaction(TransactionDate.of(executionDate), Amount.of(amount),
        TransactionCurrency.of(currency), ClientId.of(clientId));
  }
}

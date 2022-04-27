package pl.swieczakb.transaction_commission.transaction.adapter.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import pl.swieczakb.transaction_commission.transaction.domain.model.Amount;
import pl.swieczakb.transaction_commission.transaction.domain.model.ClientId;
import pl.swieczakb.transaction_commission.transaction.domain.model.OriginCurrency;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionDate;

public class DelegateTransactionRequest {

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;

  private BigDecimal amount;

  private String currency;

  @JsonProperty("client_id")
  private long clientId;

  public TransactionDate getTransactionDate() {
    return TransactionDate.of(date);
  }

  public Amount getAmount() {
    return Amount.of(amount);
  }

  public OriginCurrency getCurrency() {
    return OriginCurrency.of(currency);
  }

  public ClientId getClientId() {
    return ClientId.of(clientId);
  }
}

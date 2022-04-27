package pl.swieczakb.transaction_commission.transaction.adapter.exchangerate;

import java.math.BigDecimal;
import java.util.Currency;
import pl.swieczakb.transaction_commission.transaction.domain.model.Amount;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCurrency;

public class ExchangeResult {

  private final BigDecimal amount;
  private final Currency currency;

  public ExchangeResult(BigDecimal amount, Currency currency) {
    this.amount = amount;
    this.currency = currency;
  }

  public static ExchangeResult of(BigDecimal amount, Currency currency) {
    return new ExchangeResult(amount, currency);
  }

  public Amount getAmount() {
    return Amount.of(amount);
  }

  public TransactionCurrency getCurrency() {
    return TransactionCurrency.of(currency.getCurrencyCode());
  }
}

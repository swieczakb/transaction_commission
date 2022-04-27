package pl.swieczakb.transaction_commission.transaction.domain.model;

import java.util.Currency;

public class ExchangeCurrency {

  private final Currency currency;

  public ExchangeCurrency(Currency currency) {
    this.currency = currency;
  }

  public static ExchangeCurrency of(Currency currency) {
    return new ExchangeCurrency(currency);
  }

  public String getCurrencyCode(){
    return currency.getCurrencyCode();
  }
}

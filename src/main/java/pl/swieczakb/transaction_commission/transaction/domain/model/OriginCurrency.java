package pl.swieczakb.transaction_commission.transaction.domain.model;

import java.util.Currency;

public class OriginCurrency {

  private final Currency currency;

  public OriginCurrency(Currency currency) {
    this.currency = currency;
  }

  public static OriginCurrency of(String currency) {
    return new OriginCurrency(Currency.getInstance(currency));
  }

  public void validate() throws ValidationException {
    if(currency == null ){
      throw new ValidationException("Currency can't be null!");
    }
  }

  public Currency getCurrency() {
    return currency;
  }
}

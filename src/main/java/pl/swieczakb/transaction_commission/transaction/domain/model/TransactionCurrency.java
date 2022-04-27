package pl.swieczakb.transaction_commission.transaction.domain.model;

import java.util.Currency;
import pl.swieczakb.transaction_commission.transaction.domain.model.exception.ValidationException;

public class TransactionCurrency {

  private final Currency currency;

  public TransactionCurrency(Currency currency) {
    this.currency = currency;
  }

  public static TransactionCurrency of(String currency) {
    return new TransactionCurrency(Currency.getInstance(currency));
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

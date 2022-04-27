package pl.swieczakb.transaction_commission.transaction.domain.model;

import java.time.LocalDate;
import pl.swieczakb.transaction_commission.transaction.domain.model.exception.ValidationException;

public class TransactionDate {

  private final LocalDate date;

  public TransactionDate(LocalDate date) {
    this.date = date;
  }

  public static TransactionDate of(LocalDate date) {
    return new TransactionDate(date);
  }

  public void validate() throws ValidationException {
    if (date == null ){
      throw new ValidationException("Execution date can't be null!");
    }
  }

  public LocalDate getDate() {
    return date;
  }
}

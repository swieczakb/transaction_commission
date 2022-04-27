package pl.swieczakb.transaction_commission.transaction.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Amount implements Comparable<Amount> {

  private final BigDecimal value;

  public Amount(BigDecimal amount) {
    this.value = amount;
  }

  public static Amount of(BigDecimal amount) {
    return new Amount(amount);
  }

  public BigDecimal getValue() {
    return value;
  }

  public void validate() throws ValidationException {
    if (value == null) {
      throw new ValidationException("Amount can not be null!");
    }
    if (value.compareTo(BigDecimal.ZERO) < 0) {
      throw new ValidationException("Amount must be greater than 0!");
    }
  }

  public BigDecimal countPercentage(BigDecimal percentage) {
    return value.multiply(percentage.divide(BigDecimal.valueOf(100)))
        .setScale(2, RoundingMode.CEILING);
  }

  @Override
  public int compareTo(Amount amount) {
    return amount.getValue().compareTo(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Amount)) {
      return false;
    }

    Amount amount = (Amount) o;

    return Objects.equals(value, amount.value);
  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }
}

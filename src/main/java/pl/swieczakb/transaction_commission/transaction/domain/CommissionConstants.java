package pl.swieczakb.transaction_commission.transaction.domain;

import java.math.BigDecimal;

enum CommissionConstants {
  MINIMAL_COMMISSION(new BigDecimal("0.05")),
  HIGH_TURNOVER_DISCOUNT_COMMISSION(new BigDecimal("0.03")),
  SPECIAL_CLIENT_DISCOUNT_COMMISSION(new BigDecimal("0.05"));

  private final BigDecimal amount;

  CommissionConstants(BigDecimal amount) {
    this.amount = amount;
  }

  BigDecimal getAmount() {
    return amount;
  }
}

package pl.swieczakb.transaction_commission.transaction.adapter.exchangerate;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class NewestExchangeRateResponse {

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;

  private Map<String, BigDecimal> rates;

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Map<String, BigDecimal> getRates() {
    return rates;
  }

  public void setRates(Map<String, BigDecimal> rates) {
    this.rates = rates;
  }

  public Optional<BigDecimal> findRateFor(Currency currency) {
    return rates.entrySet()
        .stream()
        .filter(entry -> entry.getKey().equals(currency.getCurrencyCode()))
        .map(Entry::getValue)
        .findFirst();
  }
}

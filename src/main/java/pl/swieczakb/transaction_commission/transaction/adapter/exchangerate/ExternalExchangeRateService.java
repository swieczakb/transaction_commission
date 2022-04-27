package pl.swieczakb.transaction_commission.transaction.adapter.exchangerate;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Currency;
import pl.swieczakb.transaction_commission.transaction.domain.port.ExchangeRateService;

public class ExternalExchangeRateService implements ExchangeRateService {

  private final ExchangeRateClient exchangeRateClient;

  public ExternalExchangeRateService(ExchangeRateClient exchangeRateClient) {
    this.exchangeRateClient = exchangeRateClient;
  }

  @Override
  public ExchangeResult exchangeToEuro(BigDecimal amount, Currency currency,
      LocalDate executionDate) {
    final NewestExchangeRateResponse exchangeRateResponse = exchangeRateClient.getExchangeRateFrom(
        executionDate);
    return exchangeRateResponse.findRateFor(currency)
        .map(rate -> ExchangeResult.of(exchange(amount, rate), currency))
        .orElseThrow(() -> new IllegalStateException("Currency doesn't exist!"));
  }

  private BigDecimal exchange(BigDecimal amount, BigDecimal rate) {
    return amount.multiply(rate).setScale(2, RoundingMode.CEILING);
  }
}

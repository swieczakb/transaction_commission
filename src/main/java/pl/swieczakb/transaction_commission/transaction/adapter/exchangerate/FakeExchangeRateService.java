package pl.swieczakb.transaction_commission.transaction.adapter.exchangerate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Optional;
import pl.swieczakb.transaction_commission.transaction.domain.port.ExchangeRateService;

public class FakeExchangeRateService implements ExchangeRateService {

  private final NewestExchangeRateResponse mockedExchangeRateResponse;

  public FakeExchangeRateService(
      NewestExchangeRateResponse mockedExchangeRateResponse) {
    this.mockedExchangeRateResponse = mockedExchangeRateResponse;
  }

  @Override
  public ExchangeResult exchangeToEuro(BigDecimal amount, Currency currency,
      LocalDate executionDate) {
    final Optional<BigDecimal> rateFor = mockedExchangeRateResponse.findRateFor(currency);
    return ExchangeResult.of(rateFor.get().multiply(amount),currency);
  }
}

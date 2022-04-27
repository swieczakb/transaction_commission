package pl.swieczakb.transaction_commission.transaction.domain.port;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import pl.swieczakb.transaction_commission.transaction.adapter.exchangerate.ExchangeResult;

public interface ExchangeRateService {

  ExchangeResult exchangeToEuro(BigDecimal amount, Currency currency, LocalDate executionDate);

}

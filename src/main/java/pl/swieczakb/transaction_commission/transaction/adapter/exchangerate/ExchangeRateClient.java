package pl.swieczakb.transaction_commission.transaction.adapter.exchangerate;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.RestTemplate;

public class ExchangeRateClient {

  private final RestTemplate restTemplate;
  private final String baseUri;

  public ExchangeRateClient(RestTemplate restTemplate, String baseUri) {
    this.restTemplate = restTemplate;
    this.baseUri = baseUri;
  }

  @Cacheable("exchangeRate")
  public NewestExchangeRateResponse getExchangeRateFrom(LocalDate date){
    return restTemplate.getForObject(
        URI.create(baseUri.concat(date.format(DateTimeFormatter.ISO_LOCAL_DATE))), NewestExchangeRateResponse.class);
  }
}

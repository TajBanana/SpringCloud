package tajbanana.currencyconversionservice.controller;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tajbanana.currencyconversionservice.model.CurrencyConversion;
import tajbanana.currencyconversionservice.proxy.CurrencyExchangeProxy;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

  private final Environment environment;
  private final CurrencyExchangeProxy currencyExchangeProxy;

  public CurrencyConversionController(Environment environment, CurrencyExchangeProxy currencyExchangeProxy) {
    this.environment = environment;
    this.currencyExchangeProxy = currencyExchangeProxy;
  }

  @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable BigDecimal quantity, @PathVariable String to) {

    HashMap<String, String> uriVariables = new HashMap<>();
    uriVariables.put("from", from);
    uriVariables.put("to", to);

    ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange" +
        "/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);


    CurrencyConversion currencyConversion = responseEntity.getBody();
    return new CurrencyConversion(currencyConversion.getId(), from, to,
        quantity, currencyConversion.getConversionMultiple(),
        quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment());
  }

  @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity" +
      "/{quantity}")
  public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable BigDecimal quantity, @PathVariable String to) {

    CurrencyConversion currencyConversion =
        currencyExchangeProxy.retrieveExchangeValue(from, to);

    return new CurrencyConversion(currencyConversion.getId(), from, to,
        quantity, currencyConversion.getConversionMultiple(),
        quantity.multiply(currencyConversion.getConversionMultiple()),
        currencyConversion.getEnvironment() + " feign");
  }
}

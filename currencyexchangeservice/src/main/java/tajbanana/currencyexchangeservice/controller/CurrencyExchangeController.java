package tajbanana.currencyexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tajbanana.currencyexchangeservice.model.CurrencyExchange;
import tajbanana.currencyexchangeservice.repository.CurrencyExchangeRepository;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

  private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

  private final Environment environment;
  private final CurrencyExchangeRepository currencyExchangeRepository;

  public CurrencyExchangeController(Environment environment, CurrencyExchangeRepository currencyExchangeRepository) {
    this.environment = environment;
    this.currencyExchangeRepository = currencyExchangeRepository;
  }

  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public CurrencyExchange retrieveExchangeValue(@PathVariable String from,
                                                @PathVariable String to) {
    System.out.println(from + to);

    logger.info("retrieveExchangeValue called with {} to {}", from, to);

    String port = environment.getProperty("local.server.port");
    CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);

    if (currencyExchange == null) throw new RuntimeException("unable to find " +
        "currency");

    currencyExchange.setEnvironment(port);
    return currencyExchange;
  }
}

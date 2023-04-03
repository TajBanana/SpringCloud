package tajbanana.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tajbanana.springcloud.limitsservice.bean.Limits;
import tajbanana.springcloud.limitsservice.configuration.Configuration;

@RestController
public class LimitsController {

  private final Configuration configuration;

  public LimitsController(Configuration configuration) {
    this.configuration = configuration;
  }

  @GetMapping("/limits")
  public Limits retrieveLimits() {
    return new Limits(configuration.getMinimum(), configuration.getMaximum());
  }
}

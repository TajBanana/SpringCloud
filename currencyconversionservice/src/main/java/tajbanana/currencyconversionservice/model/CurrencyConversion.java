package tajbanana.currencyconversionservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CurrencyConversion {
  private Long id;
  private String from;
  private String to;
  private BigDecimal quantity;
  private BigDecimal conversionMultiple;
  private BigDecimal totalCalculatedAmount;
  private String environment;

}

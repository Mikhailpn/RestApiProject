package JsonObjects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Measurement {

    private BigDecimal temperature;
    private Boolean raining;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date measuretime;
    private Sensor sensor;
}

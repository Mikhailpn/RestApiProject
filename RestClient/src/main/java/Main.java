import WebUtils.WebClientRequest;
import JsonObjects.Measurement;
import JsonObjects.Sensor;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        ApiClient apiClient = new ApiClient();

        /*
        Sensor sensor = new Sensor("Sensor new");
        System.out.println(apiClient.addSensor(sensor));

        Measurement measurement = new Measurement(new BigDecimal("30"), false, new Date(), sensor);
        System.out.println(apiClient.addMeasurement(measurement));
        */

        System.out.println(apiClient.getMeasurements());

    }


}

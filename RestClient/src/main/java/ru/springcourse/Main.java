package ru.springcourse;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.springcourse.ApiClient;
import ru.springcourse.Generators.MeasurementGenerator;
import ru.springcourse.Generators.SensorGenerator;
import ru.springcourse.JsonObjects.Measurement;
import ru.springcourse.JsonObjects.Sensor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        ApiClient apiClient = context.getBean("apiClient", ApiClient.class);
        SensorGenerator sensorGenerator = context.getBean("sensorGenerator", SensorGenerator.class);
        MeasurementGenerator measurementGenerator = context.getBean("measurementGenerator", MeasurementGenerator.class);



        try {
            Sensor sensor = sensorGenerator.generate();
            apiClient.addSensor(sensor);

            for (int i =0; i< 100; i++) {
                Measurement measurement = measurementGenerator.generate(sensor);
                apiClient.addMeasurement(measurement);
            }

            List<Measurement> measurementList = apiClient.getMeasurements();

            measurementList.sort(Comparator.comparing(Measurement::getMeasuretime));

            //Draw graph
            double[] yData = measurementList.stream().mapToDouble(x->x.getTemperature().doubleValue()).toArray();
            double[] xData = measurementList.stream().mapToDouble(x->Double.valueOf(x.getMeasuretime().getTime())).toArray();

            // Create Chart
            XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);

            // Show it
            new SwingWrapper(chart).displayChart();


        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        context.close();

    }


}

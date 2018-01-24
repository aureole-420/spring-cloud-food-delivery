package demo;


import com.fasterxml.jackson.databind.ObjectMapper;
import demo.domain.PaymentInfo;
import demo.service.DeliveryEstimator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;

import java.io.IOException;
import java.util.Date;

@Slf4j
@EnableBinding(Sink.class)   // consumer
public class DeliveryEstimationSink {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeliveryEstimator deliveryEstimator;

    @ServiceActivator(inputChannel = Sink.INPUT)
    public void deliveryTimeEstimator(String input) throws IOException {
        log.info("<Consumer> Valid order/payment input into deliveryEstimator: " + input + "\n");
        PaymentInfo paymentInfo = this.objectMapper.readValue(input, PaymentInfo.class);
        log.info("<Consumer> Payment date: " + paymentInfo.getPaymentTime() + "\n");
        Date estimatedDeliveryTime = deliveryEstimator.estimateDeliveryTime(paymentInfo);

        log.info("<Consumer> Estimated delivery time: " + estimatedDeliveryTime);
        // use websocket to push the delivery time to FrontEnd;
    }
}

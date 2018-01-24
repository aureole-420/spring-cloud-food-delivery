package demo.rest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableBinding(Source.class)
@RestController
@Slf4j
public class OrderSource {

    @Autowired
    private MessageChannel output;


    @RequestMapping(path="/mq/orders", method = RequestMethod.POST)
    public void post(@RequestBody String validatedPaymentInfo) {
        log.info("Receiving validated paymentInfo from order-payment service: " + validatedPaymentInfo);

        this.output.send(MessageBuilder.withPayload(validatedPaymentInfo).build());
    }
}

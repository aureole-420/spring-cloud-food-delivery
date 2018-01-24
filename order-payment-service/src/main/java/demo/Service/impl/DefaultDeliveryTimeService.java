package demo.Service.impl;

import demo.Service.DeliveryTimeService;
import demo.domain.PaymentInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class DefaultDeliveryTimeService implements DeliveryTimeService {

    @Value("${com.yuhui.cs504.order.distribution.service}")
    private String orderDistribution;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void estimateDeliveryTime(PaymentInfo paymentInfo) {
        log.info("The order-payment-service is calling the REST API of order-distribution-service");
        restTemplate.postForLocation(orderDistribution+"/mq/orders", paymentInfo);
    }
}

package demo.Service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import demo.Service.DeliveryTimeService;
import demo.domain.PaymentInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class DefaultDeliveryTimeService implements DeliveryTimeService {

    @Value("${com.yuhui.cs504.order.distribution.service}")
    private String orderDistribution;


    @LoadBalanced
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private DiscoveryClient discoveryClient;


    @HystrixCommand(fallbackMethod = "estimateDeliveryTimeFallBack")
    @Override
    public void estimateDeliveryTime(PaymentInfo paymentInfo) {

//            discoveryClient.getInstances("order-distribution-service");
//            String orderDistribution = "http://order-distribution-service";
            log.info("The order-payment-service is calling the REST API of order-distribution-service");
            log.info("<http://order-distribution-service> is " + orderDistribution);
            this.restTemplate.postForLocation(orderDistribution + "/mq/orders", paymentInfo);

    }

    public void estimateDeliveryTimeFallBack(PaymentInfo paymentInfo) {
        log.error("<order-payment-service>: " + "Hystrix Fallback method. Unable to send message for distribution");
    }
}

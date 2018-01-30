package demo.rest;

import demo.Service.DeliveryTimeService;
import demo.Service.ValidateCreditCardService;
import demo.domain.OrderInfo;
import demo.domain.OrderInfoRepository;
import demo.domain.PaymentInfo;
import demo.domain.PaymentInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class OrderPaymentRestController {

    private OrderInfoRepository orderInfoRepository;
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private ValidateCreditCardService validateCreditCardService;

    @Autowired
    public OrderPaymentRestController(OrderInfoRepository orderInfoRepository, PaymentInfoRepository paymentInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
        this.paymentInfoRepository = paymentInfoRepository;
    }

    @Autowired
    private DeliveryTimeService deliveryTimeService;

    @RequestMapping(value="/orderInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void placeOrder(@RequestBody OrderInfo orderInfo) {
        this.orderInfoRepository.save(orderInfo);
    }

    @RequestMapping(value="/orderInfo/retrieve", method=RequestMethod.GET)
    public List<OrderInfo> getOrders() {
        return this.orderInfoRepository.findAll();
    }


    @RequestMapping(value="/paymentInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void processPayment(@RequestBody PaymentInfo paymentInfo) {
        boolean isPaymentValid = validateCreditCardService.checkCreditCard(paymentInfo);
        if (!isPaymentValid) {
            // push message to FE through websocket -- that the payment failed.
//            System.out.println("<Rest.processPayment> credit card service failed.");
            log.error("<Rest.processPayment> credit card service failed.");
        } else {
            // 0. save to the database.
            // 1. push message to FE through websocket -- that the payment is successful --- not implemented.
            // 2. distribute the paymentInfo to MQ
            // 3. send it
            paymentInfoRepository.save(paymentInfo); // step. 0 completed
            // step. 1  unimplemented

            try {
                deliveryTimeService.estimateDeliveryTime(paymentInfo); // step. 2 completed
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    @RequestMapping(value="/paymentInfo/retrieve", method = RequestMethod.GET)
    public List<PaymentInfo> getPaymentInfo() {
        return paymentInfoRepository.findAll();
    }



}

package demo.service.impl;

import demo.domain.PaymentInfo;
import demo.service.DeliveryEstimator;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DefaultDeliveryEstimator implements DeliveryEstimator {
    private final long MINUTE_IN_MILISECONDS = 60 * 1000;
    private final long timeForDelivery = 10 * MINUTE_IN_MILISECONDS;
    private final long timeForEveryDish = 2 * MINUTE_IN_MILISECONDS;

    @Override
    public Date estimateDeliveryTime(PaymentInfo paymentInfo) {
        Date paymentTime = paymentInfo.getPaymentTime();

        int numOfDishes = paymentInfo.getOrderItemsList().size();
        long timeCost = timeForDelivery + timeForEveryDish * numOfDishes + (long) (Math.random() * 5 * MINUTE_IN_MILISECONDS);
        timeCost = timeCost <= 25 * MINUTE_IN_MILISECONDS ? timeCost : 25 * MINUTE_IN_MILISECONDS;

        return new Date(paymentTime.getTime() + timeCost);
    }
}

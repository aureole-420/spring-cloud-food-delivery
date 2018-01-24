package demo.service;

import demo.domain.PaymentInfo;

import java.util.Date;

public interface DeliveryEstimator {

    Date estimateDeliveryTime(PaymentInfo paymentInfo);
}

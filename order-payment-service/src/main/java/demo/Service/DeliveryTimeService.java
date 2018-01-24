package demo.Service;

import demo.domain.PaymentInfo;

public interface DeliveryTimeService {

    void estimateDeliveryTime(PaymentInfo paymentInfo);
}

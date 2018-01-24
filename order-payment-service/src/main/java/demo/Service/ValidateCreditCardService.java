package demo.Service;

import demo.domain.PaymentInfo;

public interface ValidateCreditCardService {

    boolean checkCreditCard(PaymentInfo paymentInfo);
}

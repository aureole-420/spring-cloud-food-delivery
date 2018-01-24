package demo.Service.impl;

import demo.Service.ValidateCreditCardService;
import demo.domain.CreditCardChecker;
import demo.domain.PaymentInfo;
import org.springframework.stereotype.Service;


@Service
public class DefaultValidateCreditCardService implements ValidateCreditCardService {

    @Override
    public boolean checkCreditCard(PaymentInfo paymentInfo) {
        return CreditCardChecker.checkCreditCard(paymentInfo);
    }

}

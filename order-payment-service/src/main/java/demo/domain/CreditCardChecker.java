package demo.domain;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreditCardChecker {


    public static boolean checkCreditCard(PaymentInfo paymentInfo) {
        String creditCardNumber = paymentInfo.getCreditCardNumber();

        // sample: o8 18
        String creditCardExpiration = paymentInfo.getCreditCardExpiration();

        // should be three digit number
        String cvcNumber = paymentInfo.getCvcNumber();

        return checkCardNum(creditCardNumber) && checkExpiration(creditCardExpiration) && checkCVC(cvcNumber);
    }

    // check if
    private static boolean checkCardNum(String creditCardNumber) {
        if (creditCardNumber == null || creditCardNumber.length() == 0) {
            log.info("<CreditCardChecker.checkCardNum()> cardNumber is not valid.");
            return false;
        }
        String tempString = creditCardNumber.replaceAll("\\s+","");
        char[] arr = tempString.toCharArray();
        if (arr.length != 16) {
            log.info("<CreditCardChecker.checkCardNum()> cardNumber is not valid. (not 16 digits)");
            log.info("cardNumber.length = " + arr.length);
            return false;
        }
        for (char c : arr) {
            try {
                int aa = Integer.parseInt(""+c);
            } catch (Exception e) {
                // should log exception here.
                log.info("<CreditCardChecker.checkCardNum()> cardNumber is not valid (exception parsingInt).");
                return false;
            }
        }
        return true;
    }

    // check if first two char makes a char smaller than 13
    private static boolean checkExpiration(String creditCardExpiration) {
        if (creditCardExpiration == null || creditCardExpiration.length() == 0) {
            log.info("<CreditCardChecker.checkExpiration()> card expiration is not valid.");
            return false;
        }
        String tempString = creditCardExpiration.replaceAll("\\s+","");
        char[] arr = tempString.toCharArray();
        if (arr.length != 4) {
            log.info("<CreditCardChecker.checkExpiration()> card expiration is not valid. (not 4 digits)");
            return false;
        }
        try {
            int month = Integer.parseInt(""+arr[0]) * 10 + Integer.parseInt(""+arr[1]);
            int year = Integer.parseInt(""+arr[2]) * 10 + Integer.parseInt(""+arr[3]);
            if (month >= 0 && month <= 12 && year >= 18) {
                return true;
            } else {
                log.info("<CreditCardChecker.checkExpiration()> card expiration is not valid. (invalid month/year)");
                return false;
            }
        } catch (NumberFormatException nfe) {
            log.info("<CreditCardChecker.creditCardExpiration()> card expiration is not valid (exception parsingInt).");
            return false;
        }
    }

    private static boolean checkCVC(String cvcNumber) {
        if (cvcNumber == null || cvcNumber.length() == 0) {
            log.info("<CreditCardChecker.checkCVC()> card CVC is not valid.");
            return false;
        }
        String tempString = cvcNumber.replaceAll("\\s+", "");
        char[] arr = tempString.toCharArray();
        if (arr.length != 3) {
            log.info("<CreditCardChecker.checkCVC()> card CVC is not valid. (not 3 digits)");
            return false;
        }

        try{
            int cvc = 0;
            for (char c : arr) {
                cvc = 10*cvc + Integer.parseInt(""+c);
            }
            if ( cvc >= 0 && cvc <= 999) {
                return true;
            } else {
                log.info("<CreditCardChecker.checkCVC()> card CVC is not valid. (cvc number invalid)");
                return false;
            }
        } catch (NumberFormatException nfe) {
            log.info("<CreditCardChecker.checkCVC()> card CVC is not valid. (exception parsingInt)");
            return false;
        }
    }
}

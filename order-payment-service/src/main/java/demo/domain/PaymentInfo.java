package demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document
@Data
public class PaymentInfo {

    @Id
    String id;

    String customerName;

    @JsonFormat(pattern="yyyy-MM-dd hh:mm")
    Date paymentTime = new Date();
    String address;

    List<OrderItem> orderItemsList;

    String orderNote;
    double billAmount;

    String creditCardNumber;
    String creditCardExpiration; // sample: o8 18
    String cvcNumber; // should be three digit number


    public PaymentInfo() {
    }

    @JsonCreator
    public PaymentInfo(@JsonProperty("customerName") String customerName,
                       @JsonProperty("orderItemsList") List<OrderItem> orderItemsList,
                       @JsonProperty("creditCardNumber") String creditCardNumber,
                       @JsonProperty("creditCardExpiration") String creditCardExpiration,
                       @JsonProperty("cvcNumber") String cvcNumber) {
        this.customerName = customerName;
        this.orderItemsList = orderItemsList;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiration = creditCardExpiration;
        this.cvcNumber = cvcNumber;
        this.billAmount = calBill(orderItemsList);
    }

    private double calBill(List<OrderItem> orderItemsList) {
        double res = 0;
        for (OrderItem oi : orderItemsList) {
            res += oi.getPrice() * oi.getQuantity();
        }
        return res;
    }
}

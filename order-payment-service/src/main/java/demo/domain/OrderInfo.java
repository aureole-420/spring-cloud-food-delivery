package demo.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderInfo {

    @Id
    String id;

    String customerName;

    @JsonFormat(pattern="yyyy-MM-dd hh:mm")
    Date orderTime;
    String address;

    List<OrderItem> orderItemsList;

    String orderNote;
    double billAmount;

    public OrderInfo() {
    }

    @JsonCreator
    public OrderInfo(@JsonProperty("customerName") String customerName, // Date orderTime,
                      @JsonProperty("orderItemsList") List<OrderItem> orderItemsList) {
        this.customerName = customerName;
        // this.orderTime = orderTime;
        this.orderItemsList = orderItemsList;
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

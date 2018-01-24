package demo.domain;

import lombok.Data;

@Data
public class OrderItem {
    String dishName;
    double price; // per dish
    int quantity; // the quantity of this item in the a single order.

    public OrderItem() {
    }

    public OrderItem(String dishName, double price, int quantity) {
        this.dishName = dishName;
        this.price = price;
        this.quantity = quantity;
    }
}

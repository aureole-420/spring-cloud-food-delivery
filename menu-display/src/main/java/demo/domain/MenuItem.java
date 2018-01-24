package demo.domain;


import lombok.Data;



@Data
public class MenuItem {

    int itemId;
    String itemName;
    double itemPrice;

    public MenuItem() {
    }

    public MenuItem( int itemId, String itemName, double itemPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}

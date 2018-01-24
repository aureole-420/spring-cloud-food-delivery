package demo.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Document
public class Menu {
    @Id
    String id;

    String menuName;

    List<MenuItem> menuItems;


    public Menu() { // no argument constructor

    }

    @JsonCreator
    public Menu(@JsonProperty("menuName") String menuName, @JsonProperty("menuItems") List<MenuItem> menuItems) {
        this.menuName = menuName;
        this.menuItems = menuItems;
    }
}

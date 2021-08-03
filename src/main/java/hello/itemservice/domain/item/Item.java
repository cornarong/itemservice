package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// @Data // Getter, Setter를 포함한 대부분의 기능을 제공하기에 위험하다. -> DTD(데이터를 왔다갔다 하는 경우)는 괜찮다.
@Getter @Setter
public class Item {
    private Long id;
    private String itemName;
    private Integer price; // 값이 없을 경우를 대비하여 Integer
    private Integer quantity; // 값이 없을 경우를 대비하여 Integer

    // 생성자 2개 (기본 생성자, ID를 제외한 생성자)
    public Item(){
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}

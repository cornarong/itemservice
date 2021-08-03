package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository // 안에 @Component가 있다 -> ComponentScan의 대상이 된다.
public class ItemRepository {

    // static을 안하면 다른곳에서 new로 생성할 때 따로 생성되어 버린다.
    private static final Map<Long, Item> store = new HashMap<>(); // static , 동시성이 문제가 되는 상황에서만 ConcurrentHashMap을 사용하면 된다.
    private static Long sequence = 0L; // static, 여러개가 동시에 접근하는 환경(멀티 쓰레드)에서는 Long쓰면 안된다. (AtomicLong 사용 해야함)

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(),item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore(){
        store.clear();
    }
}

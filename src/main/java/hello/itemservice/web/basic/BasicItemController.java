package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // 설정 시 final이 붙은 변수의 생성자를 만들어준다.
public class BasicItemController {

    private final ItemRepository itemRepository;

    /*@Autowired
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }*/
    // 아이템 전체 보기
    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }
    // 아이템 상세 보기
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }
    // get 저장 페이지 이동
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }
    // post 저장
    @PostMapping("/add")
    // @ModelAttribute > Item 객체를 생성해서 파라미터로 받은 값들을 자동으로 넣어준다. 속성값 "item"은 모델에 넣어서 그대로 사용할 수 있도록 해준다.
    public String add(@ModelAttribute("item") Item item, RedirectAttributes redirectAttributes){
        Item saveItem = itemRepository.save(item);
//        model.addAttribute("item",item); // @ModelAttribute 속성 덕분에 지워도 된다. (자동 추가, 생략 가능)
        redirectAttributes.addAttribute("savedId", saveItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{savedId}";
    }
    // get 수정 페이지 이동
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }
    // post 수정
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute("item") Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }
    @PostConstruct
    public void init(){
        // 테스트용 데이터 (초기 데이터)
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }
}

package org.semicorp.mscitemapi.domain.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService studentService) {
        this.itemService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        log.info("Get all items");
        List<Item> items = itemService.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable(value="id") String id)  {
        log.info(String.format("Get item by id: %s", id));
        Item item = itemService.getStudent(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Item>> getUserItems(@PathVariable(value="userId") String userId)  {
        log.info(String.format("Get items for user %s", userId));
        List<Item> items = itemService.getUserItem(userId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping
    public void addItem() {
        log.info("Adding item");
    }

}

package org.semicorp.mscitemapi.item;

import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.item.dao.ItemDAO;
import org.semicorp.mscitemapi.item.exceptions.ItemNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.semicorp.mscitemapi.item.ItemsConstants.STUDENT_NOT_FOUND;

@Service
@Slf4j
public class ItemService {

    private final Jdbi jdbi;

    public ItemService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<Item> getAllItems() {
        return jdbi.onDemand(ItemDAO.class).findAll();
    }

    public Item getStudent(String id) {
        Item student = jdbi.onDemand(ItemDAO.class).findById(id);
        try {
            if (student == null) {
                String errorMessage = STUDENT_NOT_FOUND + " ID: " + id;
                throw new ItemNotFoundException(errorMessage);
            }
        } catch(RuntimeException e) {
            log.warn(e.getMessage());
        }
        return student;
    }

    public List<Item> getUserItem(String userId) {
        List<Item> items = jdbi.onDemand(ItemDAO.class).findAllByUserId(userId);
        return items;
    }
}

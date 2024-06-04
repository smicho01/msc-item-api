package org.semicorp.mscitemapi.domain.item.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {}

    public ItemNotFoundException(String message) {
        super(message);
    }
}

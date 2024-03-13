package com.bsm.bsm.generic;

public interface Activable <T>{
    void activate(T item);
    void deactivate(T item);
    boolean isActive(T item);
}

package com.bsm.bsm.commonInterface;

import com.bsm.bsm.order.Order;

import java.util.List;

public interface SearchableOrder extends Searchable<Order>{
    List<Order> search(String keyword, String condition);
}

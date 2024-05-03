package com.bsm.bsm.commonInterface;

import com.bsm.bsm.order.Order;

import java.util.List;

public interface DisplayableOrder extends  Displayable<Order> {
    List<Order> display(String condition);
}

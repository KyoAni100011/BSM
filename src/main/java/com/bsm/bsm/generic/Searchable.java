package com.bsm.bsm.generic;

import java.util.List;

public interface Searchable <T>{
    List<T> search(String keyword);
}

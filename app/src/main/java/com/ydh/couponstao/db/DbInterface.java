package com.ydh.couponstao.db;

public interface DbInterface<T> {
    void success(T result);

    void fail();
}

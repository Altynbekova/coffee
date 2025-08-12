package com.altynbekova.top.practice.repository;

public interface BaseRepository<T, ID> {
    void save(T entity);
    T find(ID id);
    void update(T entity);
    void delete(ID id);
}

package com.peopleManagement.services.generics;

import java.util.Collection;

public interface IGenericCrud<T, ID> {
    Collection<T> findAll();
    T findById(ID id) throws Exception;
    T save(T entity);
    T update(ID id, T entity) throws Exception;
    boolean delete(ID id) throws Exception;
}

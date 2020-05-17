package repository;

import java.util.List;

public interface IRepository<T> {
    List<T> getAll();

    T create(T entity);

    T update(T entity);

//    T delete(T entity);
}

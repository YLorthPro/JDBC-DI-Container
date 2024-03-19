package be.ylorthioir.dal;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    
    void create(T entity);

    List<T> getAll();

    Optional<T> getOne(Long id);
    
    void update(T entity, Long id);
    
    void delete(Long id);
}

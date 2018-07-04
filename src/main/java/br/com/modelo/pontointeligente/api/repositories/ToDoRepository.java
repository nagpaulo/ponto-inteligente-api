package br.com.modelo.pontointeligente.api.repositories;

import br.com.modelo.pontointeligente.api.entities.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ToDoRepository extends CrudRepository<ToDo, Long>{

    @Query("SELECT to FROM ToDo to WHERE to.toDo LIKE :toDo ORDER BY e.id")
    Page<ToDo> findToDoLike(@Param("toDo") String toDo, Pageable pageable);
}

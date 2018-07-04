package br.com.modelo.pontointeligente.api.services;

import br.com.modelo.pontointeligente.api.entities.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ToDoServices {
    Page<ToDo> buscarPorToDo(String buscar, PageRequest pageRequest);
}

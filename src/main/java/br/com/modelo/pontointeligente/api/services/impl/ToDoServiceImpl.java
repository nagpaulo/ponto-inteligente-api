package br.com.modelo.pontointeligente.api.services.impl;

import br.com.modelo.pontointeligente.api.entities.ToDo;
import br.com.modelo.pontointeligente.api.repositories.ToDoRepository;
import br.com.modelo.pontointeligente.api.services.ToDoServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ToDoServiceImpl implements ToDoServices{
    private static final Logger log = LoggerFactory.getLogger(ToDoServiceImpl.class);

    @Autowired
    private ToDoRepository toDoRepository;

    @Override
    public Page<ToDo> buscarPorToDo(String buscar, PageRequest pageRequest) {
        log.info("Bucando ToDo por palavra", buscar);
        return this.toDoRepository.findToDoLike(buscar,pageRequest);
    }
}

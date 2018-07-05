package br.com.modelo.pontointeligente.api.controllers;

import br.com.modelo.pontointeligente.api.dtos.ToDoDto;
import br.com.modelo.pontointeligente.api.entities.ToDo;
import br.com.modelo.pontointeligente.api.response.Response;
import br.com.modelo.pontointeligente.api.services.ToDoServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Optional;


@RestController
@RequestMapping("api/todo")
@CrossOrigin(origins = "*")
public class ToDoController {

    private static Logger log = LoggerFactory.getLogger(ToDoController.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ToDoServices toDoServices;

    @Value("${paginacao.qtd_por_pagina}")
    private int qtdPorPagina;

    @GetMapping(value = "search/{buscar}")
    public ResponseEntity<Response<Page<ToDoDto>>> listarToDo(
            @PathVariable("buscar") String buscar,
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir
    ){
        log.info("Listando tarefas");
        Response<Page<ToDoDto>> response = new Response<Page<ToDoDto>>();

        PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Sort.Direction.valueOf(dir), ord);
        Page<ToDo> todos = this.toDoServices.buscarPorToDo(buscar,pageRequest);
        Page<ToDoDto> toDoDtos = todos.map(toDo -> this.converteToDoDto(toDo));

        response.setData(toDoDtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Response<ToDoDto>> getToDoById(
            @PathVariable("id") Long id
    ){
        log.info("Pesquisando por id");

        Response<ToDoDto> response = new Response<ToDoDto>();
        Optional<ToDo> toDo = this.toDoServices.buscarPorId(id);

        if(!toDo.isPresent()){
            log.info("ToDo não encontrado para o id {}", id);
            response.getErrors().add("ToDo não encontrado para o id  " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.converteToDoDto(toDo.get()));
        return ResponseEntity.ok(response);
    }

    private ToDoDto converteToDoDto(ToDo toDo){
        ToDoDto toDoDto = new ToDoDto();
        toDoDto.setId(Optional.of(toDo.getId()));
        toDoDto.setToDo(toDo.getToDo());
        toDoDto.setDone(toDo.getDone());
        toDoDto.setUsuarioId(toDo.getUsuario().getId());
        toDoDto.setDataCriacao(toDo.getDataCriacao().toString());

        String dtAtualizacao = null;
        try {
            dtAtualizacao = toDo.getDataAtualizacao().toString();
        }catch (Exception e){
            log.warn("Data de Atualizacao vazia. {}", e.getMessage());
        }
        toDoDto.setDataAtualizacao(dtAtualizacao);

        return toDoDto;
    }
}

package kz.attractorschool.todoapp.todo.controller;

import jakarta.validation.Valid;
import kz.attractorschool.todoapp.todo.dto.TodoCreateRequest;
import kz.attractorschool.todoapp.todo.dto.TodoResponse;
import kz.attractorschool.todoapp.todo.dto.TodoUpdateRequest;
import kz.attractorschool.todoapp.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponse create(@Valid @RequestBody TodoCreateRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<TodoResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TodoResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public TodoResponse update(@PathVariable Long id, @Valid @RequestBody TodoUpdateRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
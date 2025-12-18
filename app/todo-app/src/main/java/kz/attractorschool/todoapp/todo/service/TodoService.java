package kz.attractorschool.todoapp.todo.service;

import kz.attractorschool.todoapp.todo.dto.TodoCreateRequest;
import kz.attractorschool.todoapp.todo.dto.TodoResponse;
import kz.attractorschool.todoapp.todo.dto.TodoUpdateRequest;

import java.util.List;

public interface TodoService {
    TodoResponse create(TodoCreateRequest request);
    List<TodoResponse> findAll();
    TodoResponse findById(Long id);
    TodoResponse update(Long id, TodoUpdateRequest request);
    void delete(Long id);
}
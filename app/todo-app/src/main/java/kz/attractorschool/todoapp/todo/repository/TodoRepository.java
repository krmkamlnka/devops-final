package kz.attractorschool.todoapp.todo.repository;

import kz.attractorschool.todoapp.todo.entity.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoItem, Long> {}
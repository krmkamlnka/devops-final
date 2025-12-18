package kz.attractorschool.todoapp.todo.dto;

import kz.attractorschool.todoapp.todo.entity.TodoPriority;

import java.time.Instant;

public record TodoResponse(
        Long id,
        String title,
        String description,
        boolean completed,
        TodoPriority priority,
        Instant createdAt,
        Instant updatedAt
) {}
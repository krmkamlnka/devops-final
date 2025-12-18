package kz.attractorschool.todoapp.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kz.attractorschool.todoapp.todo.entity.TodoPriority;

public record TodoUpdateRequest(
        @NotBlank(message = "title must not be blank")
        @Size(max = 120, message = "title max length is 120")
        String title,

        @Size(max = 500, message = "description max length is 500")
        String description,

        boolean completed,
        TodoPriority priority
) {}
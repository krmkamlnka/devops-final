package kz.attractorschool.todoapp.todo.service;

import kz.attractorschool.todoapp.todo.dto.TodoCreateRequest;
import kz.attractorschool.todoapp.todo.dto.TodoResponse;
import kz.attractorschool.todoapp.todo.dto.TodoUpdateRequest;
import kz.attractorschool.todoapp.todo.entity.TodoItem;
import kz.attractorschool.todoapp.todo.entity.TodoPriority;
import kz.attractorschool.todoapp.todo.exception.NotFoundException;
import kz.attractorschool.todoapp.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repository;

    public TodoServiceImpl(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public TodoResponse create(TodoCreateRequest request) {
        TodoItem item = new TodoItem();
        item.setTitle(request.title());
        item.setDescription(request.description());
        item.setPriority(request.priority() == null ? TodoPriority.MEDIUM : request.priority());
        item.setCompleted(false);

        TodoItem saved = repository.save(item);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TodoResponse findById(Long id) {
        TodoItem item = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Todo item not found: id=" + id));
        return toResponse(item);
    }

    @Override
    @Transactional
    public TodoResponse update(Long id, TodoUpdateRequest request) {
        TodoItem item = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Todo item not found: id=" + id));

        item.setTitle(request.title());
        item.setDescription(request.description());
        item.setCompleted(request.completed());
        item.setPriority(request.priority() == null ? TodoPriority.MEDIUM : request.priority());

        TodoItem saved = repository.save(item);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Todo item not found: id=" + id);
        }
        repository.deleteById(id);
    }

    private TodoResponse toResponse(TodoItem item) {
        return new TodoResponse(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.isCompleted(),
                item.getPriority(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}
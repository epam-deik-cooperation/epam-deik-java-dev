package com.example.demo.service;

import com.example.demo.entity.Todo;
import com.example.demo.repository.TodoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

  @Autowired
  private TodoRepository todoRepository;

  public Todo getTodoById(Long todoId) {
    return todoRepository.findById(todoId).get();
  }

  public Todo createTodo(Todo todo) {
    return todoRepository.save(todo);
  }

  public List<Todo> getTodos() {
    return todoRepository.findAll();
  }
}

package com.example.demo.controller;

import com.example.demo.entity.Todo;
import com.example.demo.service.TodoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

  @Autowired
  private TodoService todoService;

  @GetMapping("/todo")
  public List<Todo> getTodos() {
    return todoService.getTodos();
  }

  @PostMapping("/todo")
  public Todo createTodo(@RequestBody Todo todo) {
    return todoService.createTodo(todo);
  }

  @GetMapping("/todo/{todoId}")
  public Todo getTodoById(@PathVariable Long todoId) {
    return todoService.getTodoById(todoId);
  }

}

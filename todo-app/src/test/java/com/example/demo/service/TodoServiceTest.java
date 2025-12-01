package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.example.demo.entity.Todo;
import com.example.demo.repository.TodoRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

  @Mock
  private TodoRepository todoRepository;

  @InjectMocks
  private TodoService underTest;

  @Test
  void testFetToDoByIdWhenIdFound() {
    //GIVEN
    Long todoId = 1L;
    Todo expectedTodo = new Todo(todoId, "Test Todo", false);
    Optional<Todo> expectedOptional = Optional.of(expectedTodo);
    when(todoRepository.findById(todoId)).thenReturn(expectedOptional);

    //WHEN
    Todo actualTodo = underTest.getTodoById(todoId);

    //THEN
    assertNotNull(actualTodo);
    assertEquals(expectedTodo.getTitle(), actualTodo.getTitle());
    assertFalse(actualTodo.isCompleted());
  }

}

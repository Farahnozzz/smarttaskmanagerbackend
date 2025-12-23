package com.smarttaskmanager.backend.controller;

import com.smarttaskmanager.backend.dto.AnalyticsDTO;
import com.smarttaskmanager.backend.model.Comment;
import com.smarttaskmanager.backend.model.Subtask;
import com.smarttaskmanager.backend.model.Task;
import com.smarttaskmanager.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            if (updatedTask == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            boolean deleted = taskService.deleteTask(id);
            if (!deleted) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Subtask endpoints
    @PostMapping("/{taskId}/subtasks")
    public ResponseEntity<Subtask> addSubtask(@PathVariable Long taskId, @RequestBody Subtask subtask) {
        try {
            Subtask createdSubtask = taskService.addSubtask(taskId, subtask);
            if (createdSubtask == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSubtask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{taskId}/subtasks/{subtaskId}")
    public ResponseEntity<Subtask> updateSubtask(
            @PathVariable Long taskId,
            @PathVariable Long subtaskId,
            @RequestBody Subtask subtask) {
        try {
            Subtask updatedSubtask = taskService.updateSubtask(taskId, subtaskId, subtask);
            if (updatedSubtask == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedSubtask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{taskId}/subtasks/{subtaskId}")
    public ResponseEntity<Void> deleteSubtask(@PathVariable Long taskId, @PathVariable Long subtaskId) {
        try {
            boolean deleted = taskService.deleteSubtask(taskId, subtaskId);
            if (!deleted) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Comment endpoints
    @PostMapping("/{taskId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long taskId, @RequestBody Comment comment) {
        try {
            Comment createdComment = taskService.addComment(taskId, comment);
            if (createdComment == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{taskId}/comments")
    public ResponseEntity<List<Comment>> getTaskComments(@PathVariable Long taskId) {
        try {
            List<Comment> comments = taskService.getTaskComments(taskId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
package com.smarttaskmanager.backend.service;

import com.smarttaskmanager.backend.model.Comment;
import com.smarttaskmanager.backend.model.Subtask;
import com.smarttaskmanager.backend.model.Task;
import com.smarttaskmanager.backend.repository.SubtaskRepository;
import com.smarttaskmanager.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubtaskRepository subtaskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElse(null);
    }

    @Transactional
    public Task createTask(Task task) {
        if (task.getCreatedAt() == null) {
            task.setCreatedAt(LocalDateTime.now());
        }
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, Task taskDetails) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            return null;
        }

        Task task = optionalTask.get();

        if (taskDetails.getTitle() != null) {
            task.setTitle(taskDetails.getTitle());
        }
        if (taskDetails.getDescription() != null) {
            task.setDescription(taskDetails.getDescription());
        }
        if (taskDetails.getDueDate() != null) {
            task.setDueDate(taskDetails.getDueDate());
        }
        if (taskDetails.getPriority() != null) {
            task.setPriority(taskDetails.getPriority());
        }
        if (taskDetails.getStatus() != null) {
            task.setStatus(taskDetails.getStatus());
            // If status changed to DONE, set completedAt
            if ("DONE".equals(taskDetails.getStatus()) && task.getCompletedAt() == null) {
                task.setCompletedAt(java.time.LocalDate.now());
            } else if (!"DONE".equals(taskDetails.getStatus())) {
                task.setCompletedAt(null);
            }
        }

        return taskRepository.save(task);
    }

    @Transactional
    public boolean deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            return false;
        }
        taskRepository.deleteById(id);
        return true;
    }

    // Subtask methods
    @Transactional
    public Subtask addSubtask(Long taskId, Subtask subtask) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return null;
        }

        Task task = optionalTask.get();
        subtask.setTask(task);
        Subtask savedSubtask = subtaskRepository.save(subtask);

        // Add subtask to task's list
        task.getSubtasks().add(savedSubtask);
        taskRepository.save(task);

        return savedSubtask;
    }

    @Transactional
    public Subtask updateSubtask(Long taskId, Long subtaskId, Subtask subtaskDetails) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return null;
        }

        Optional<Subtask> optionalSubtask = subtaskRepository.findById(subtaskId);
        if (optionalSubtask.isEmpty()) {
            return null;
        }

        Subtask subtask = optionalSubtask.get();

        if (subtaskDetails.getTitle() != null) {
            subtask.setTitle(subtaskDetails.getTitle());
        }
        if (subtaskDetails.getCompleted() != null) {
            subtask.setCompleted(subtaskDetails.getCompleted());
        }

        return subtaskRepository.save(subtask);
    }

    @Transactional
    public boolean deleteSubtask(Long taskId, Long subtaskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return false;
        }

        if (!subtaskRepository.existsById(subtaskId)) {
            return false;
        }

        Task task = optionalTask.get();
        task.getSubtasks().removeIf(st -> st.getId().equals(subtaskId));
        taskRepository.save(task);
        subtaskRepository.deleteById(subtaskId);

        return true;
    }

    // Comment methods
    @Transactional
    public Comment addComment(Long taskId, Comment comment) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return null;
        }

        Task task = optionalTask.get();
        comment.setTask(task);
        comment.setCreatedAt(LocalDateTime.now());

        task.getComments().add(comment);
        taskRepository.save(task);

        return comment;
    }

    public List<Comment> getTaskComments(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return List.of();
        }
        return optionalTask.get().getComments();
    }

    // Analytics methods
    public long countTotalTasks() {
        return taskRepository.count();
    }

    public long countTasksByStatus(String status) {
        return taskRepository.countByStatus(status);
    }
}
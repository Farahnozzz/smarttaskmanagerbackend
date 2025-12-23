package com.smarttaskmanager.backend.controller;

import com.smarttaskmanager.backend.dto.AnalyticsDTO;
import com.smarttaskmanager.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<AnalyticsDTO> getAnalytics() {
        try {
            long totalTasks = taskService.countTotalTasks();
            long todoTasks = taskService.countTasksByStatus("TODO");
            long inProgressTasks = taskService.countTasksByStatus("IN_PROGRESS");
            long doneTasks = taskService.countTasksByStatus("DONE");

            AnalyticsDTO analytics = new AnalyticsDTO(
                    totalTasks,
                    todoTasks,
                    inProgressTasks,
                    doneTasks
            );

            return ResponseEntity.ok(analytics);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
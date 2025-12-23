package com.smarttaskmanager.backend.repository;

import com.smarttaskmanager.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    long countByStatus(String status);
}

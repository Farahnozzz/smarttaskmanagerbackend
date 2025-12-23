package com.smarttaskmanager.backend.repository;

import com.smarttaskmanager.backend.model.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubtaskRepository extends JpaRepository<Subtask, Long> {}

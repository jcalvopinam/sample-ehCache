package com.jcalvopinam.repository;

import com.jcalvopinam.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by juan.calvopina on 30/03/2017.
 */
public interface TaskRepository extends JpaRepository<Task, Integer> {
}

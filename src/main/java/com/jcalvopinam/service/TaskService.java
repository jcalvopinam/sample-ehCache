package com.jcalvopinam.service;

import com.jcalvopinam.domain.Task;
import com.jcalvopinam.dto.TaskDto;
import com.jcalvopinam.exception.TaskException;

import java.util.List;

/**
 * Created by juan.calvopina on 29/03/2017.
 */
public interface TaskService {

    List<Task> findAll();

    Task save(TaskDto taskDTO);

    Task update(Integer taskId, TaskDto taskDTO) throws TaskException;

    void delete(Integer taskId) throws TaskException;

    void clearCache();

}

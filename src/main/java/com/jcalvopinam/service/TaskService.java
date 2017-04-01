package com.jcalvopinam.service;

import com.jcalvopinam.domain.Task;
import com.jcalvopinam.dto.TaskDTO;

import java.util.List;

/**
 * Created by juan.calvopina on 29/03/2017.
 */
public interface TaskService {

    List<Task> findAll();

    void save(TaskDTO taskDTO);

    void update(TaskDTO taskDTO);

    void delete(Integer taskId);

    void clearCache();

}

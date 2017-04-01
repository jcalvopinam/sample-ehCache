package com.jcalvopinam.service;

import com.jcalvopinam.domain.Task;
import com.jcalvopinam.dto.TaskDTO;
import com.jcalvopinam.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by juan.calvopina on 29/03/2017.
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TaskRepository taskRepository;

    @Override
    @Cacheable("taskFindAll")
    public List<Task> findAll() {
        logger.info("Retrieving tasks");
        return taskRepository.findAll();
    }

    @Override
    public void save(TaskDTO taskDTO) {
        logger.info(String.format("Inserting a new task %s", taskDTO.toString()));
        taskRepository.save(new Task(taskDTO));
    }

    @Override
    public void update(TaskDTO taskDTO) {
        logger.info(String.format("Updating a task %s", taskDTO.toString()));
        Task task = taskRepository.findOne(taskDTO.getId());
        task.setTaskName(taskDTO.getTaskName());
        task.setStatus(taskDTO.getStatus());
        taskRepository.save(task);
    }

    @Override
    public void delete(Integer taskId) {
        logger.info(String.format("Deleting a task %s", taskId));
        taskRepository.delete(taskId);
    }

    @Override
    @CacheEvict(value = "taskFindAll", allEntries = true)
    public void clearCache() {
        // No need to implement anything, because @CacheEvict annotation does everything
    }

}

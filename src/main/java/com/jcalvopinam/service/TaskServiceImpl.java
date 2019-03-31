package com.jcalvopinam.service;

import com.jcalvopinam.domain.Task;
import com.jcalvopinam.dto.TaskDto;
import com.jcalvopinam.exception.TaskException;
import com.jcalvopinam.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
@Slf4j
public class TaskServiceImpl implements TaskService {

    private static final String IGNORED_PROPERTIES = "id";

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Cacheable("taskFindAll")
    public List<Task> findAll() {
        log.info("Retrieving tasks");
        return taskRepository.findAll();
    }

    @Override
    public Task save(final TaskDto taskDTO) {
        log.info(String.format("Inserting a new task %s", taskDTO.toString()));
        final Task task = new Task();
        BeanUtils.copyProperties(taskDTO, task);
        return taskRepository.save(task);
    }

    @Override
    public Task update(final Integer taskId, final TaskDto taskDTO) throws TaskException {
        log.info(String.format("Updating a task %s", taskDTO.toString()));
        Task task = taskRepository.findById(taskId)
                                  .orElseThrow(() -> throwTaskException(taskId));
        BeanUtils.copyProperties(taskDTO, task, IGNORED_PROPERTIES);
        return taskRepository.save(task);
    }

    @Override
    public void delete(final Integer taskId) throws TaskException {
        log.info(String.format("Deleting a task %s", taskId));
        Task task = taskRepository.findById(taskId)
                                  .orElseThrow(() -> throwTaskException(taskId));
        taskRepository.delete(task);
    }

    @Override
    @CacheEvict(value = "taskFindAll", allEntries = true)
    public void clearCache() {
        log.info("Cache was deleted");
        // No need to implement anything, because @CacheEvict annotation does everything
    }

    private TaskException throwTaskException(final int taskId) {
        String message = String.format("TaskId %s not found!", taskId);
        log.info(message);
        return new TaskException(message);
    }

}

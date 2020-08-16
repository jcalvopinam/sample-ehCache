/*
 * MIT License
 *
 * Copyright (c) 2018. JUAN CALVOPINA M
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.jcalvopinam.service;

import com.jcalvopinam.domain.Task;
import com.jcalvopinam.dto.TaskDTO;
import com.jcalvopinam.exception.TaskException;
import com.jcalvopinam.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by juan.calvopina on 29/03/2017.
 */
@Service
@Transactional
@Slf4j
@CacheConfig(cacheNames = {"tasks"})
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ConversionService conversionService;
    private final CacheManager cacheManager;

    @Autowired
    public TaskServiceImpl(final TaskRepository taskRepository, final ConversionService conversionService, final CacheManager cacheManager) {
        this.taskRepository = taskRepository;
        this.conversionService = conversionService;
        this.cacheManager = cacheManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    public List<TaskDTO> findAll() {
        log.info("Retrieving tasks");
        final List<Task> taskList = taskRepository.findAll();
        return taskList.stream()
                       .map(task -> conversionService.convert(task, TaskDTO.class))
                       .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(key = "#taskName")
    public TaskDTO searchByTaskName(final String taskName) {
        log.info("Getting task by the name {}", taskName);
        return conversionService.convert(taskRepository.findByTaskName(taskName)
                                                        .orElseThrow(() -> new TaskException(
                                                            "Task not found")), TaskDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CachePut(key = "#taskDTO.taskName")
    public TaskDTO save(final TaskDTO taskDTO) {
        log.info(String.format("Inserting a new task %s", taskDTO.toString()));
        final Task task = Objects.requireNonNull(conversionService.convert(taskDTO, Task.class));
        return conversionService.convert(taskRepository.save(task), TaskDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CachePut(key = "#taskDTO.taskName")
    public TaskDTO update(final Integer taskId, final TaskDTO taskDTO) {
        log.info(String.format("Updating a task %s", taskDTO.toString()));
        Task currentTask = findByTaskId(taskId);

        final Task taskUpdated = Objects.requireNonNull(conversionService.convert(taskDTO, Task.class));
        taskUpdated.setId(currentTask.getId());
        log.info("The taskDTO modified {}", currentTask.toString());
        return conversionService.convert(taskRepository.save(taskUpdated), TaskDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict
    public void delete(final Integer taskId) {
        log.info(String.format("Deleting a task %s", taskId));
        final Task currentEmployee = findByTaskId(taskId);
        taskRepository.delete(currentEmployee);

        Objects.requireNonNull(cacheManager.getCache("tasks"))
                .clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict(allEntries = true)
    public void clearCache() {
        log.info("Cache was deleted");
        // No need to implement anything, because @CacheEvict annotation does everything
    }

    /**
     * Searches the task by its {@code taskId}.
     *
     * @param taskId receives the id of the task.
     */
    private Task findByTaskId(final Integer taskId) {
        return taskRepository.findById(taskId)
                             .orElseThrow(() -> {
                                 String message = String.format("TaskId %s not found!", taskId);
                                 log.info(message);
                                 return new TaskException(message);
                             });
                }

}

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

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

import java.util.List;
import javax.transaction.Transactional;

import com.jcalvopinam.domain.Task;
import com.jcalvopinam.dto.TaskDTO;
import com.jcalvopinam.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
    public void update(TaskDTO taskDTO) throws Exception {
        logger.info(String.format("Updating a task %s", taskDTO.toString()));
        Task task = taskRepository.findById(taskDTO.getId())
                                  .orElseThrow(() -> new Exception("Please enter the task ID"));
        task.setTaskName(taskDTO.getTaskName());
        task.setStatus(taskDTO.getStatus());
        taskRepository.save(task);
    }

    @Override
    public void delete(Integer taskId) {
        logger.info(String.format("Deleting a task %s", taskId));
        taskRepository.deleteById(taskId);
    }

    @Override
    @CacheEvict(value = "taskFindAll", allEntries = true)
    public void clearCache() {
        // No need to implement anything, because @CacheEvict annotation does everything
    }

}

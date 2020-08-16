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

import com.jcalvopinam.dto.TaskDTO;

import java.util.List;

/**
 * Created by juan.calvopina on 29/03/2017.
 */
public interface TaskService {

    /**
     * Find all tasks.
     *
     * @return a list of TaskDTO.
     */
    List<TaskDTO> findAll();

    /**
     * Find an employee by {code taskName}.
     *
     * @param taskName receives the taskName
     * @return an TaskDTO object.
     */
    TaskDTO searchByTaskName(String taskName);

    /**
     * Create a new task.
     *
     * @param taskDTO receives an TaskDTO object.
     * @return an TaskDTO object.
     */
    TaskDTO save(TaskDTO taskDTO);

    /**
     * Creates a new task.
     *
     * @param taskId  receives the {@code taskId} to be updated.
     * @param taskDTO receives an TaskDTO object.
     * @return an TaskDTO object.
     */
    TaskDTO update(Integer taskId, TaskDTO taskDTO);

    /**
     * Deletes a task by {@code taskId}.
     *
     * @param taskId receives the {@code taskId} to be deleted.
     */
    void delete(Integer taskId);

    /**
     * Clean the cache
     */
    void clearCache();

}

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

package com.jcalvopinam.controller;

import com.jcalvopinam.dto.TaskDTO;
import com.jcalvopinam.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

/**
 * Created by juan.calvopina on 29/03/2017.
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Endpoint that list all tasks.
     *
     * @return a list of TaskDTO.
     */
    @GetMapping
    public ResponseEntity<List<TaskDTO>> findAllTask() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(taskService.findAll());
    }

    /**
     * Endpoint that list a specific task.
     *
     * @param taskName receives the {@code taskName}.
     * @return an TaskDTO object.
     */
    @GetMapping("/{taskName}")
    public ResponseEntity<TaskDTO> searchByTaskDTO(@PathVariable final String taskName) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(taskService.searchByTaskName(taskName.toUpperCase(Locale.ENGLISH)));
    }

    /**
     * Endpoint that create a new task.
     *
     * @param taskDTO receives the {@code taskDTO}.
     * @return an TaskDTO object.
     */
    @PostMapping
    public ResponseEntity<TaskDTO> saveTask(@RequestBody final TaskDTO taskDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(taskService.save(taskDTO));
    }

    /**
     * Endpoint that update an existing task.
     *
     * @param taskId  receives the {@code taskId}.
     * @param taskDTO receives the {@code taskDTO}.
     * @return an TaskDTO object.
     */
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable("taskId") final Integer taskId,
                                              @RequestBody final TaskDTO taskDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(taskService.update(taskId, taskDTO));
    }

    /**
     * Endpoint that delete an existing task.
     *
     * @param taskId receives the {@code taskId}.
     * @return a void.
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") final Integer taskId) {
        taskService.delete(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

    /**
     * Endpoint that clear the cache.
     *
     * @return a void.
     */
    @GetMapping("/clear-cache")
    public ResponseEntity<Void> clearCache() {
        taskService.clearCache();
        return ResponseEntity.status(HttpStatus.OK)
                             .build();
    }

}
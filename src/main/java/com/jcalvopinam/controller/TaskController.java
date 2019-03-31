package com.jcalvopinam.controller;

import com.jcalvopinam.domain.Task;
import com.jcalvopinam.dto.TaskDto;
import com.jcalvopinam.exception.TaskException;
import com.jcalvopinam.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by juan.calvopina on 29/03/2017.
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> findAllTask() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(taskService.findAll());
    }

    @PostMapping
    public ResponseEntity<Task> saveTask(@RequestBody final TaskDto taskDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(taskService.save(taskDTO));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable("taskId") final Integer taskId,
                                           @RequestBody final TaskDto taskDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(taskService.update(taskId, taskDTO));
        } catch (TaskException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") final Integer taskId) {
        try {
            taskService.delete(taskId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (TaskException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCache() {
        taskService.clearCache();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
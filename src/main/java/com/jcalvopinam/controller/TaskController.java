package com.jcalvopinam.controller;

import com.jcalvopinam.domain.Task;
import com.jcalvopinam.dto.TaskDTO;
import com.jcalvopinam.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by juan.calvopina on 29/03/2017.
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Task> findAllTask() {
        return taskService.findAll();
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public String saveTask(@RequestBody TaskDTO taskDTO) {
        taskService.save(taskDTO);
        return "Saved successfully";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateTask(@RequestBody TaskDTO taskDTO) {
        taskService.update(taskDTO);
        return "Updated successfully";
    }

    @RequestMapping(value = "{taskId}/delete", method = RequestMethod.DELETE)
    public String deleteTask(@PathVariable("taskId") Integer taskId) {
        taskService.delete(taskId);
        return "Deleted successfully";
    }

    @RequestMapping(value = "/clear-cache", method = RequestMethod.DELETE)
    public String clearCache() {
        taskService.clearCache();
        return "Cache was delete successfully";
    }

}
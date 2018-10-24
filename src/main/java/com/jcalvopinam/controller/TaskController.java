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

import java.util.List;

import com.jcalvopinam.domain.Task;
import com.jcalvopinam.dto.TaskDTO;
import com.jcalvopinam.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public String updateTask(@RequestBody TaskDTO taskDTO) throws Exception {
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
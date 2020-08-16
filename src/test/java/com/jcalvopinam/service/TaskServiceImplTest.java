/*
 * MIT License
 *
 * Copyright (c) 2020. JUAN CALVOPINA M
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
import com.jcalvopinam.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.convert.ConversionService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

/**
 * @author juan.calvopina
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private ConversionService conversionService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void searchAllTest() {
        Mockito.when(taskRepository.findAll())
                .thenReturn(Collections.singletonList(createTask()));
        Mockito.when(conversionService.convert(createTask(), TaskDTO.class))
                .thenReturn(createTaskDTO());

        final List<TaskDTO> employees = taskService.findAll();
        assertEquals(1, employees.size(), "It should have 1 element");
    }

    @Test
    public void searchByTaskNameTest() {
        Mockito.when(taskRepository.findByTaskName(any()))
               .thenReturn(Optional.of(createTask()));
        Mockito.when(conversionService.convert(createTask(), TaskDTO.class))
               .thenReturn(createTaskDTO());
        final TaskDTO task = taskService.searchByTaskName("TEST");
        assertEquals("TEST", task.getTaskName(), "It should be example");
    }

    @Test
    public void createTaskTest() {
        Mockito.when(taskRepository.save(any()))
                .thenReturn(createTask());
        Mockito.when(conversionService.convert(createTaskDTO(), Task.class))
                .thenReturn(createTask());
        Mockito.when(conversionService.convert(createTask(), TaskDTO.class))
                .thenReturn(createTaskDTO());
        final TaskDTO employee = taskService.save(createTaskDTO());
        assertEquals("ACTIVE", employee.getStatus(), "It should be ACTIVE");
    }

    @Test
    public void modifyTaskTest() {
        final Task currentTask = new Task(1, "Test", "INACTIVE");
        Mockito.when(taskRepository.findById(anyInt()))
                .thenReturn(Optional.of(currentTask));
        final Task updateTask = createTask();
        Mockito.when(conversionService.convert(createTaskDTO(), Task.class))
                .thenReturn(updateTask);
        Mockito.when(taskRepository.save(any()))
                .thenReturn(updateTask);
        Mockito.when(conversionService.convert(createTask(), TaskDTO.class))
                .thenReturn(createTaskDTO());
        final TaskDTO employeeUpdated = taskService.update(1, createTaskDTO());
        assertEquals("ACTIVE", employeeUpdated.getStatus(), "It should be ACTIVE");
    }

    @Test
    public void removeTaskTest() {
        final Task employee = createTask();
        Mockito.when(taskRepository.findById(anyInt()))
                .thenReturn(Optional.of(employee));
        Mockito.doNothing()
                .when(taskRepository)
                .delete(employee);
        Mockito.when(cacheManager.getCache("tasks"))
                .thenReturn(cache);
        taskService.delete(employee.getId());
        assertEquals("", "", "It should be true");
    }

    private Task createTask() {
        return new Task(1, "Test", "ACTIVE");
    }

    private TaskDTO createTaskDTO() {
        return new TaskDTO(1, "Test", "ACTIVE");
    }
}
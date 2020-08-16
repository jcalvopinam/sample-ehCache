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

package com.jcalvopinam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcalvopinam.dto.TaskDTO;
import com.jcalvopinam.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author juan.calvopina
 */
@AutoConfigureMockMvc
@ContextConfiguration
@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskControllerTest {

    private static final String BASE_URL = "/tasks";

    @MockBean
    private TaskService taskService;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .build();
    }

    @Test
    public void searchAllEmployeesTest() throws Exception {
        final ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                        .andExpect(status().isOk());
        assertEquals(HttpStatus.OK.value(), resultActions.andReturn()
                .getResponse()
                .getStatus(), "It should be 200");

    }

    @Test
    public void createEmployeeTest() throws Exception {
        final ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createTaskDTO())
                        .characterEncoding(StandardCharsets.UTF_8.name()))
                        .andExpect(status().isCreated());
        assertEquals(HttpStatus.CREATED.value(), resultActions.andReturn()
                .getResponse()
                .getStatus(), "It should be 201");
    }

    @Test
    public void modifyEmployeeTest() throws Exception {
        final ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createTaskDTO())
                        .characterEncoding(StandardCharsets.UTF_8.name()))
                        .andExpect(status().isOk());
        assertEquals(HttpStatus.OK.value(), resultActions.andReturn()
                .getResponse()
                .getStatus(), "It should be 200");
    }

    @Test
    public void removeEmployeeTest() throws Exception {
        final ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/1"))
                        .andExpect(status().isNoContent());
        assertEquals(HttpStatus.NO_CONTENT.value(), resultActions.andReturn()
                .getResponse()
                .getStatus(), "It should be 204");

    }

    @Test
    public void clearCacheTest() throws Exception {
        final ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/clear-cache"))
                        .andExpect(status().isOk());
        assertEquals(HttpStatus.OK.value(), resultActions.andReturn()
                .getResponse()
                .getStatus(), "It should be 200");

    }

    private String createTaskDTO() throws JsonProcessingException {
        final TaskDTO employee = new TaskDTO(1, "Test", "ACTIVE");
        return new ObjectMapper().writeValueAsString(employee);
    }

}
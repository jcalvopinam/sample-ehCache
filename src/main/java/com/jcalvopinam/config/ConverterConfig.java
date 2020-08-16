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

package com.jcalvopinam.config;

import com.jcalvopinam.converter.TaskDTOToTaskConverter;
import com.jcalvopinam.converter.TaskToTaskDTOConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    private final TaskDTOToTaskConverter taskDTOToTaskConverter;
    private final TaskToTaskDTOConverter taskToTaskDTOConverter;

    public ConverterConfig(final TaskDTOToTaskConverter taskDTOToTaskConverter,
                           final TaskToTaskDTOConverter taskToTaskDTOConverter) {
        this.taskDTOToTaskConverter = taskDTOToTaskConverter;
        this.taskToTaskDTOConverter = taskToTaskDTOConverter;
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(taskDTOToTaskConverter);
        registry.addConverter(taskToTaskDTOConverter);
    }

}

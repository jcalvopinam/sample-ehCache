package com.jcalvopinam.converter;

import com.jcalvopinam.domain.Task;
import com.jcalvopinam.dto.TaskDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TaskDTOToTaskConverter implements Converter<TaskDTO, Task> {

    @Override
    public Task convert(@NonNull final TaskDTO taskDTO) {
        final Task task = new Task();
        BeanUtils.copyProperties(taskDTO, task);
        return task;
    }

}

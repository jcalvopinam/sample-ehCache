package com.jcalvopinam.converter;

import com.jcalvopinam.domain.Task;
import com.jcalvopinam.dto.TaskDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskDTOConverter implements Converter<Task, TaskDTO> {

    @Override
    public TaskDTO convert(@NonNull final Task task) {
        final TaskDTO taskDTO = new TaskDTO();
        BeanUtils.copyProperties(task, taskDTO);
        return taskDTO;
    }

}

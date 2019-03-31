package com.jcalvopinam.dto;

import lombok.Data;

/**
 * Created by juan.calvopina on 30/03/2017.
 */
@Data
public class TaskDto {

    private String taskName;
    private String status;

    @Override
    public String toString() {
        return String.format("TaskDto{taskName='%s', status=%s}", taskName, status);
    }

}

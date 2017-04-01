package com.jcalvopinam.dto;

/**
 * Created by juan.calvopina on 30/03/2017.
 */
public class TaskDTO {

    private Integer id;
    private String taskName;
    private String status;

    public TaskDTO(Integer id, String taskName, String status) {
        this.id = id;
        this.taskName = taskName;
        this.status = status;
    }

    public TaskDTO(){
    }

    public Integer getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TaskDTO{");
        sb.append("id=").append(id);
        sb.append(", taskName='").append(taskName).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

}

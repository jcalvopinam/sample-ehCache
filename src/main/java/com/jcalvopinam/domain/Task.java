package com.jcalvopinam.domain;

import com.jcalvopinam.dto.TaskDTO;

import javax.persistence.*;

/**
 * Created by juan.calvopina on 29/03/2017.
 */

@Entity
@Table(name = "TASK")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "TASK_NAME")
    private String taskName;

    @Column(name = "STATUS")
    private String status;

    public Task(Integer id, String taskName, String status) {
        this.id = id;
        this.taskName = taskName;
        this.status = status;
    }

    public Task(String taskName, String status) {
        this(null, taskName, status);
    }

    public Task(TaskDTO taskDTO) {
        this(taskDTO.getTaskName(), taskDTO.getStatus());
    }

    public Task() {
    }

    public Integer getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (status != task.status) return false;
        if (id != null ? !id.equals(task.id) : task.id != null) return false;
        return this.taskName != null ? this.taskName.equals(task.taskName) : task.taskName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Task{");
        sb.append("id=").append(id);
        sb.append(", taskName='").append(taskName).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

}

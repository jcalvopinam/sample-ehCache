package com.jcalvopinam.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by juan.calvopina on 29/03/2017.
 */

@Data
@Entity
@Table(name = "TASK")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TASK_NAME")
    private String taskName;

    @Column(name = "STATUS")
    private String status;

}

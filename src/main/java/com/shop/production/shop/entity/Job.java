package com.shop.production.shop.entity;

import com.shop.production.shop.entity.enumerated.JobStages;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="job_info")
@Data
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "brief_info")
    private String briefInfo;

    @Column(name = "current_stage")
    @Enumerated(EnumType.STRING)
    private JobStages currentStage;
}

package com.shop.production.shop.repository;

import com.shop.production.shop.entity.Job;
import com.shop.production.shop.entity.enumerated.JobStages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobInfoRepository extends JpaRepository<Job, UUID> {
    Job findByJobName(String name);
    List<Job> findByCurrentStage(JobStages jobStages);
}

package com.cg.ppmtoolapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.ppmtoolapi.domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask,Long> {

	Iterable<ProjectTask> findByProjectIdentifierOrderByPriority(String projectIdentifier);
	
	ProjectTask findByProjectSequence(String projectSequence);
}

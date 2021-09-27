package com.cg.ppmtoolapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ppmtoolapi.domain.Backlog;
import com.cg.ppmtoolapi.domain.Project;
import com.cg.ppmtoolapi.exception.ProjectIdException;
import com.cg.ppmtoolapi.repository.BacklogRepository;
import com.cg.ppmtoolapi.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private BacklogRepository backlogRepository;
	
	public Project saveOrUpdate(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			//When project is getting saved for first time
			if(project.getId()==0) {
				Backlog backlog =new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			//When project is getting updated
			if(project.getId()!=0) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			return projectRepository.save(project);
			}catch(Exception e) {
				throw new ProjectIdException("Project Id :"+project.getProjectIdentifier().toUpperCase()+"already exists");
		}
	}
	
	public Project findByProjectIdentifier(String projectIdentifier) {
		Project project=projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if(project==null) {
			throw new ProjectIdException("Project Id:"+projectIdentifier.toUpperCase()+"does not exist");
		}
		return project;
	}
	
	public Iterable<Project> getAllProjects(){
		return projectRepository.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectIdentifier) {
		Project project=projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if(project==null) {
			throw new ProjectIdException("Cannot delete project with Id:"+projectIdentifier.toUpperCase()+" this project does not exist");
		}
		projectRepository.delete(project);
	}
}

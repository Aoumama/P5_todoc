package com.cleanup.todoc.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.TaskDatabase;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private final ProjectDao projectDao;

    public ProjectRepository(ProjectDao projectDao){
        this.projectDao = projectDao;
    }

    public LiveData<List<Project>> getAllProjects(){
        return projectDao.getAllProjects();
    }


}

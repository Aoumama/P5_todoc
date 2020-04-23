package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    // REPOSITORIES
    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;
    private Executor executor;

    // DATA
    @Nullable
    private LiveData<Project> currentProject;

    public TaskViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.executor = executor;
    }

    // -------------
    // FOR PROJECT
    // -------------

    public LiveData<List<Project>> getAllProjects() {
        return projectRepository.getAllProjects();
    }

    // -------------
    // FOR TASK
    // -------------

    public LiveData<List<Task>> getTasks() {
        return taskRepository.getTasks();
    }

    public void createTask(final Task task) {
        executor.execute(() -> taskRepository.insertTask(task));
    }

    public void deleteTask(Task task) {
        executor.execute(() -> taskRepository.deleteTask(task));
    }



}

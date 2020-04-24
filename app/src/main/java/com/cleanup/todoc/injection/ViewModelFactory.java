package com.cleanup.todoc.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final Executor executor;

    public ViewModelFactory(ProjectRepository projectRepository,
                            TaskRepository taskRepository, Executor executor) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(taskRepository, projectRepository, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

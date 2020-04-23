package com.cleanup.todoc.injection;

import android.content.Context;

import com.cleanup.todoc.database.TaskDatabase;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {


    private static ProjectRepository provideProjectRepository(Context context){
        TaskDatabase database = TaskDatabase.getInstance(context);
        return new ProjectRepository(database.projectDao());
    }

    private static TaskRepository provideTaskRepository(Context context) {
        TaskDatabase database = TaskDatabase.getInstance(context);
        return new TaskRepository(database.taskDao());
    }

    private static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        ProjectRepository projectRepository = provideProjectRepository(context);
        TaskRepository taskRepository = provideTaskRepository(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(projectRepository, taskRepository, executor);
    }

}

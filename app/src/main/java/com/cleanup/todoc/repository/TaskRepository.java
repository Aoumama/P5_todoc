package com.cleanup.todoc.repository;


import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private final TaskDao taskDao;

    public TaskRepository(TaskDao taskDao){
        this.taskDao = taskDao;
    }

    public void insertTask(Task task){
        taskDao.insert(task);
    }

    public void deleteTask(Task task){
        taskDao.delete(task);
    }

    public LiveData<List<Task>> getTasks(){
        return taskDao.getTasks();
    }
}

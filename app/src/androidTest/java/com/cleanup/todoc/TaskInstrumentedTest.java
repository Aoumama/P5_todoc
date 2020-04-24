package com.cleanup.todoc;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.cleanup.todoc.database.TaskDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskInstrumentedTest {

    private TaskDatabase database;
    private static Task task1 = new Task(1, "tâche 1",new Date().getTime());
    private static Task task2 = new Task(2, "tâche 2",new Date().getTime());

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDatabase() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                TaskDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDatabase() {
        database.close();
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        // add the project
        List<Project> items = LiveDataTest.getValue(database.projectDao().getAllProjects());
        assertEquals(0, items.size());

        this.database.projectDao().insertProject(Project.getAllProjects()[0]);
        this.database.projectDao().insertProject(Project.getAllProjects()[1]);
        this.database.projectDao().insertProject(Project.getAllProjects()[2]);


        items = LiveDataTest.getValue(database.projectDao().getAllProjects());
        assertEquals(3, items.size());
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {
        // Add projects in database
        for (Project project : Project.getAllProjects())
            database.projectDao().insertProject(project);
        database.taskDao().insert(task1);
        Task task1 = LiveDataTest.getValue(database.taskDao().getTasks());

        assertTrue(task1.getName().equals(task1.getName())&& task1.getId() == task1.getId());
    }

    @Test
    public void deleteTask() throws InterruptedException {
        // Adding a projects and tasks;
        for (Project project : Project.getAllProjects())
            database.projectDao().insertProject(project);

        database.taskDao().insert(task1);
        database.taskDao().insert(task2);

        List<Task> tasks = LiveDataTest.getValue(database.taskDao().getTasks());

        // Check tasks have been added
        assertEquals(2, tasks.size());

        // delete task1
        database.taskDao().delete(tasks.get(0));

        tasks = LiveDataTest.getValue(database.taskDao().getTasks());

        // task has been deleted
        assertEquals(tasks.get(0).getName(), task2.getName());
        assertEquals(1, tasks.size());
    }

}



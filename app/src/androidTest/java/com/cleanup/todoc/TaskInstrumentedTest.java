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

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                TaskDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void insertTask() throws InterruptedException {
        // add the project
        List<Project> items = LiveDataTest.getValue(database.projectDao().getAllProjects());
        assertEquals(0, items.size());

        this.database.projectDao().insertProject(Project.getAllProjects()[0]);
        this.database.projectDao().insertProject(Project.getAllProjects()[1]);
        this.database.projectDao().insertProject(Project.getAllProjects()[2]);


        items = LiveDataTest.getValue(database.projectDao().getAllProjects());
        assertEquals(3, items.size());
    }


}



package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class},version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {


    // --- SINGLETON ---
    private static volatile TaskDatabase INSTANCE;

    // --- DAO ---
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    // --- INSTANCE ---
    public static TaskDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static RoomDatabase.Callback prepopulateDatabase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new PopulateDBAsyncTask(INSTANCE).execute();
            }
        };
    }

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProjectDao projectDao;
        public TaskDao taskDao;

        private PopulateDBAsyncTask(TaskDatabase db) {
            projectDao = db.projectDao();
            taskDao = db.taskDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 3; i++)
                projectDao.insertProject(Project.getAllProjects()[i]);

            return null;
        }
    }
}

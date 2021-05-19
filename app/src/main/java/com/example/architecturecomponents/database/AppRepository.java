package com.example.architecturecomponents.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.architecturecomponents.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    public static final String LOG = "LOG_TAG";
    private static AppRepository ourInstance;
    public LiveData<List<NoteEntity>> mNotes;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mNotes = getAllNotes();
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG, "SampleData.getNotes() ="+SampleData.getNotes().get(1));
                mDb.noteDao().insertAll(SampleData.getNotes());
                Log.d(LOG, "addSampleData run");
            }
        });
    }

    private LiveData<List<NoteEntity>> getAllNotes() {
        return mDb.noteDao().getAll();
    }

    public void deleteAllNotes() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().deleteAll();
            }
        });

    }
}

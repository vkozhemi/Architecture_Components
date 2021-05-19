package com.example.architecturecomponents;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.architecturecomponents.database.AppDatabase;
import com.example.architecturecomponents.database.NoteDao;
import com.example.architecturecomponents.database.NoteEntity;
import com.example.architecturecomponents.utilities.SampleData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private NoteDao mDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mDao = mDb.noteDao();
        Log.i(TAG, "createDatabase");
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndRetrieveNotes() {
        Log.i(TAG, "createAndRetrieveNotes START");
        mDao.insertAll(SampleData.getNotes());
        int countSampleData = SampleData.getNotes().size();
        int countDao = mDao.getCount();
        Log.i(TAG, "countSampleData = "+countSampleData);
        Log.i(TAG, "countDao = "+countDao);

        assertEquals(countSampleData, countDao);
        Log.i(TAG, "createAndRetrieveNotes END");
    }

    @Test
    public void compareStrings() {
        Log.i(TAG, "compareStrings START");
        mDao.insertAll(SampleData.getNotes());
        NoteEntity noteEntityOriginal = SampleData.getNotes().get(0);
        NoteEntity noteEntityFromDb = mDb.noteDao().getNoteById(1);
        String originalText = noteEntityOriginal.getText();
        String fromDbText = noteEntityFromDb.getText();
        Log.i(TAG, "originalText = "+originalText);
        Log.i(TAG, "fromDbText = "+fromDbText);

        assertEquals(originalText, fromDbText);
        Log.i(TAG, "compareStrings END");
    }

    @Test
    public void getIdFromDb() {
        Log.i(TAG, "getIdFromDb START");
        mDao.insertAll(SampleData.getNotes());
        NoteEntity noteEntityFromDb = mDb.noteDao().getNoteById(1);
        int idFromDb = noteEntityFromDb.getId();
        Log.i(TAG, "idFromDb = "+idFromDb);

        assertEquals(1, idFromDb);
        Log.i(TAG, "getIdFromDb END");
    }

}

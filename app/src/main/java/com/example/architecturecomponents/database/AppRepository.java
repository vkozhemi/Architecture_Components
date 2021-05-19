package com.example.architecturecomponents.database;

import com.example.architecturecomponents.utilities.SampleData;

import java.util.List;

public class AppRepository {
    public static final AppRepository ourInstance = new AppRepository();
    public List<NoteEntity> mNotes;

    public static AppRepository getInstance() {
        return ourInstance;
    }

    private AppRepository() {
        mNotes = SampleData.getNotes();
    }

}

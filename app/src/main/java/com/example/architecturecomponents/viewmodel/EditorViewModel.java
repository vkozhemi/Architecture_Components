package com.example.architecturecomponents.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.architecturecomponents.database.AppRepository;
import com.example.architecturecomponents.database.NoteEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.text.TextUtils.isEmpty;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<NoteEntity> mLiveNote =
            new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
    }

    public void loadData(int noteId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity note = mRepository.getNoteById(noteId);
                mLiveNote.postValue(note);
            }
        });
    }

    public void saveNote(String noteText) {
        NoteEntity note = mLiveNote.getValue();
        if (note == null) {
            if (isEmpty(noteText.trim())) {
                return;
            }
            note = new NoteEntity(new Date(), noteText.trim());
        } else {
            note.setText(noteText.trim());
        }

        mRepository.insertNote(note);
    }
}

package com.example.triviaproject.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.triviaproject.database.TriviaRepository;
import com.example.triviaproject.database.entities.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private TriviaRepository repository;

    public UserViewModel(Application application) {
        super(application);
        this.repository = TriviaRepository.getRepository(application);
    }

    public LiveData<List<User>> getAllUsers() {
        return repository.getAllUsersLiveData();
    }
}

package ua.lukyanov.usermanaging.ui.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

public class ProfileViewModel extends AndroidViewModel {

    private MutableLiveData<String> userName = new MutableLiveData<>("Test User Name");

    public LiveData<String> userName() {
        return userName;
    }

    @Inject
    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }
}

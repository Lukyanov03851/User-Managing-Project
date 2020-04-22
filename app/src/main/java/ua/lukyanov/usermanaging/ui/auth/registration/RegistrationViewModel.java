package ua.lukyanov.usermanaging.ui.auth.registration;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.lukyanov.usermanaging.R;
import ua.lukyanov.usermanaging.network.ApiService;
import ua.lukyanov.usermanaging.network.models.RegistrationRequest;

public class RegistrationViewModel extends AndroidViewModel {

    private ApiService apiService;

    private MutableLiveData<Boolean> dataLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuccessRegistration = new MutableLiveData<>();
    private MutableLiveData<String> dataLoadingErrorMessage = new MutableLiveData<>();
    private MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private MutableLiveData<String> nameErrorMessage = new MutableLiveData<>();
    private MutableLiveData<String> passwordErrorMessage = new MutableLiveData<>();
    private MutableLiveData<String> passwordRepeatErrorMessage = new MutableLiveData<>();

    public LiveData<Boolean> isLoading() {
        return dataLoading;
    }

    public LiveData<Boolean> isSuccessRegistration() {
        return isSuccessRegistration;
    }

    public LiveData<String> getErrorMessage() {
        return dataLoadingErrorMessage;
    }

    public LiveData<String> getLoginErrorMessage() {
        return loginErrorMessage;
    }

    public LiveData<String> getNameErrorMessage() {
        return nameErrorMessage;
    }

    public LiveData<String> getPasswordErrorMessage() {
        return passwordErrorMessage;
    }

    public LiveData<String> getPasswordRepeatErrorMessage() {
        return passwordRepeatErrorMessage;
    }

    @Inject
    public RegistrationViewModel(Application app, ApiService apiService){
        super(app);
        this.apiService = apiService;
    }

    public void makeRegistration(String name, String email, String password, String passwordRepeat) {
        if (validateInputs(name, email, password, passwordRepeat)) {
            dataLoading.setValue(true);
            apiService.register(new RegistrationRequest(name, email, password)).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    dataLoading.setValue(false);
                    if (response.isSuccessful()) {
                        isSuccessRegistration.setValue(true);
                    } else {
                        dataLoadingErrorMessage.setValue("Something was wrong");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    dataLoading.setValue(false);
                    dataLoadingErrorMessage.setValue("Something was wrong");
                }

            });
        }
    }

    private boolean validateInputs(String name, String email, String password, String passwordRepeat) {
        boolean valid = true;

        if (TextUtils.isEmpty(name)) {
            nameErrorMessage.setValue(getApplication().getString(R.string.enter_name));
            valid = false;
        } else {
            nameErrorMessage.setValue(null);
        }

        if (TextUtils.isEmpty(email)) {
            loginErrorMessage.setValue(getApplication().getString(R.string.enter_email));
            valid = false;
        } else {
            loginErrorMessage.setValue(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordErrorMessage.setValue(getApplication().getString(R.string.enter_password));
            valid = false;
        } else {
            passwordErrorMessage.setValue(null);
        }

        if (TextUtils.isEmpty(passwordRepeat)) {
            passwordRepeatErrorMessage.setValue(getApplication().getString(R.string.enter_password_repeat));
            valid = false;
        } else {
            passwordRepeatErrorMessage.setValue(null);
        }

        if (valid && !password.equals(passwordRepeat)) {
            passwordRepeatErrorMessage.setValue(getApplication().getString(R.string.repeat_password_error));
            valid = false;
        }

        return valid;
    }
}
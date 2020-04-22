package ua.lukyanov.usermanaging.ui.auth.login;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.lukyanov.usermanaging.R;
import ua.lukyanov.usermanaging.network.ApiService;
import ua.lukyanov.usermanaging.network.models.LoginRequest;
import ua.lukyanov.usermanaging.network.models.LoginResponse;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = "LoginViewModel";

    private ApiService apiService;

    private MutableLiveData<Boolean> dataLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuccessLogin = new MutableLiveData<>();
    private MutableLiveData<String> dataLoadingErrorMessage = new MutableLiveData<>();
    private MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private MutableLiveData<String> passwordErrorMessage = new MutableLiveData<>();

    public LiveData<Boolean> isLoading() {
        return dataLoading;
    }

    public LiveData<Boolean> isSuccessLogin() {
        return isSuccessLogin;
    }

    public LiveData<String> getErrorMessage() {
        return dataLoadingErrorMessage;
    }

    public LiveData<String> getLoginErrorMessage() {
        return loginErrorMessage;
    }

    public LiveData<String> getPasswordErrorMessage() {
        return passwordErrorMessage;
    }

    @Inject
    public LoginViewModel(Application app, ApiService apiService){
        super(app);
        this.apiService = apiService;
    }

    void makeLogin(String email, String password) {
        if (validateInputs(email, password)) {
            dataLoading.setValue(true);
            apiService.login(new LoginRequest(email, password)).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    dataLoading.setValue(false);
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        Log.v(TAG, "LoginResponse = "+loginResponse);
                        isSuccessLogin.setValue(true);
                    } else {
                        dataLoadingErrorMessage.setValue("Something was wrong");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    dataLoading.setValue(false);
                    dataLoadingErrorMessage.setValue("Something was wrong");
                }

            });
        }
    }

    private boolean validateInputs(String email, String password) {
        boolean valid = true;

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
        return valid;
    }
}

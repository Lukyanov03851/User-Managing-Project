package ua.lukyanov.usermanaging.ui.auth.login;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.lukyanov.usermanaging.R;
import ua.lukyanov.usermanaging.data.prefs.AppPreferenceHelper;
import ua.lukyanov.usermanaging.network.ApiService;
import ua.lukyanov.usermanaging.network.models.LoginRequest;
import ua.lukyanov.usermanaging.network.models.LoginResponse;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = "LoginViewModel";

    private final ApiService apiService;

    public ObservableField<String> loginErrorMessage = new ObservableField<>();
    public ObservableField<String> passwordErrorMessage = new ObservableField<>();
    public ObservableBoolean isLoading = new ObservableBoolean();

    private final MutableLiveData<Boolean> isSuccessLogin = new MutableLiveData<>();
    private final MutableLiveData<String> dataLoadingErrorMessage = new MutableLiveData<>();

    public LiveData<Boolean> isSuccessLogin() {
        return isSuccessLogin;
    }

    public LiveData<String> getErrorMessage() {
        return dataLoadingErrorMessage;
    }

    @Inject
    public LoginViewModel(Application app, ApiService apiService){
        super(app);
        this.apiService = apiService;
    }

    void makeLogin(String email, String password) {
        if (validateInputs(email, password)) {
            isLoading.set(true);
            apiService.login(new LoginRequest(email, password)).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    isLoading.set(false);
                    processLoginResponse(response);
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    Log.v(TAG, "onFailure");
                    isLoading.set(false);
                    dataLoadingErrorMessage.setValue(getApplication().getString(R.string.common_error));
                }

            });
        }
    }

    private void processLoginResponse(Response<LoginResponse> response){
        LoginResponse loginResponse = response.body();
        Log.v(TAG, "LoginResponse = "+loginResponse);

        if (response.isSuccessful() && loginResponse != null) {
            AppPreferenceHelper.setToken(getApplication(), loginResponse.getToken());
            AppPreferenceHelper.setLogin(getApplication(), loginResponse.getEmail());
            AppPreferenceHelper.setUserObjectId(getApplication(), loginResponse.getObjectId());
            isSuccessLogin.setValue(true);
        } else  {
            dataLoadingErrorMessage.setValue(getApplication().getString(R.string.common_error));
        }


    }

    private boolean validateInputs(String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            loginErrorMessage.set(getApplication().getString(R.string.enter_email));
            valid = false;
        } else {
            loginErrorMessage.set(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordErrorMessage.set(getApplication().getString(R.string.enter_password));
            valid = false;
        } else {
            passwordErrorMessage.set(null);
        }
        return valid;
    }
}

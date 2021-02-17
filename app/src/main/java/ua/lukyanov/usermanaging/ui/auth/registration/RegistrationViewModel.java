package ua.lukyanov.usermanaging.ui.auth.registration;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
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
import ua.lukyanov.usermanaging.utils.Utils;

public class RegistrationViewModel extends AndroidViewModel {

    private final ApiService apiService;

    public ObservableField<String> loginErrorMessage = new ObservableField<>();
    public ObservableField<String> nameErrorMessage = new ObservableField<>();
    public ObservableField<String> passwordErrorMessage = new ObservableField<>();
    public ObservableField<String> passwordRepeatErrorMessage = new ObservableField<>();
    public ObservableBoolean isLoading = new ObservableBoolean();

    private final MutableLiveData<Boolean> isSuccessRegistration = new MutableLiveData<>();
    private final MutableLiveData<String> dataLoadingErrorMessage = new MutableLiveData<>();

    public LiveData<Boolean> isSuccessRegistration() {
        return isSuccessRegistration;
    }

    public LiveData<String> getErrorMessage() {
        return dataLoadingErrorMessage;
    }

    @Inject
    public RegistrationViewModel(Application app, ApiService apiService){
        super(app);
        this.apiService = apiService;
    }

    public void makeRegistration(String name, String email, String password, String passwordRepeat) {
        if (validateInputs(name, email, password, passwordRepeat)) {
            isLoading.set(true);
            apiService.register(new RegistrationRequest(name, email, password)).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    isLoading.set(false);
                    if (response.isSuccessful()) {
                        isSuccessRegistration.setValue(true);
                    } else {
                        dataLoadingErrorMessage.setValue(Utils.processError(response.errorBody(), getApplication()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    isLoading.set(false);
                    dataLoadingErrorMessage.setValue(getApplication().getString(R.string.common_error));
                }

            });
        }
    }

    private boolean validateInputs(String name, String email, String password, String passwordRepeat) {
        boolean valid = true;

        if (TextUtils.isEmpty(name)) {
            nameErrorMessage.set(getApplication().getString(R.string.enter_name));
            valid = false;
        } else {
            nameErrorMessage.set(null);
        }

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

        if (TextUtils.isEmpty(passwordRepeat)) {
            passwordRepeatErrorMessage.set(getApplication().getString(R.string.enter_password_repeat));
            valid = false;
        } else {
            passwordRepeatErrorMessage.set(null);
        }

        if (valid && !password.equals(passwordRepeat)) {
            passwordRepeatErrorMessage.set(getApplication().getString(R.string.repeat_password_error));
            valid = false;
        }

        return valid;
    }
}
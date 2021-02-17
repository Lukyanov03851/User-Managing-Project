package ua.lukyanov.usermanaging.ui.profile;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.lukyanov.usermanaging.R;
import ua.lukyanov.usermanaging.data.prefs.AppPreferenceHelper;
import ua.lukyanov.usermanaging.models.UserProperty;
import ua.lukyanov.usermanaging.network.ApiService;
import ua.lukyanov.usermanaging.network.models.ProfilePropertiesResponse;
import ua.lukyanov.usermanaging.utils.Utils;

public class ProfileViewModel extends AndroidViewModel {

    private static final String TAG = "ProfileViewModel";

    private final ApiService apiService;

    public ObservableBoolean isLoading = new ObservableBoolean();
    public ObservableField<String> name = new ObservableField<>();

    private final MutableLiveData<String> dataLoadingErrorMessage = new MutableLiveData<>();

    public LiveData<String> getErrorMessage() {
        return dataLoadingErrorMessage;
    }

    private final MutableLiveData<List<UserProperty>> userProperties = new MutableLiveData<>();

    public LiveData<List<UserProperty>> getUserProperties() {
        return userProperties;
    }


    @Inject
    public ProfileViewModel(@NonNull Application application, ApiService apiService) {
        super(application);
        this.apiService = apiService;
    }

    void obtainUserProperties() {
        isLoading.set(true);
        apiService.obtainUserProperties(
                AppPreferenceHelper.getUserObjectId(getApplication()),
                "name,email").enqueue(new Callback<ProfilePropertiesResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfilePropertiesResponse> call, @NonNull Response<ProfilePropertiesResponse> response) {
                isLoading.set(false);
                processUserPropertiesResponse(response);
            }

            @Override
            public void onFailure(@NonNull Call<ProfilePropertiesResponse> call, @NonNull Throwable t) {
                Log.v(TAG, "onFailure");
                isLoading.set(false);
                dataLoadingErrorMessage.setValue(getApplication().getString(R.string.common_error));
            }

        });
    }

    private void processUserPropertiesResponse(Response<ProfilePropertiesResponse> response){
        ProfilePropertiesResponse propertiesResponse = response.body();

        if (response.isSuccessful() && propertiesResponse != null){
            name.set(propertiesResponse.getName());

            ArrayList<UserProperty> resultList = new ArrayList<>();
            resultList.add(new UserProperty(getApplication().getString(R.string.phone_number), propertiesResponse.getPhone()));
            resultList.add(new UserProperty(getApplication().getString(R.string.email), propertiesResponse.getEmail()));
            resultList.add(new UserProperty(getApplication().getString(R.string.password), getApplication().getString(R.string.password_mask)));
            resultList.add(new UserProperty(getApplication().getString(R.string.address), propertiesResponse.getAddress()));

            userProperties.setValue(resultList);
        } else {
            dataLoadingErrorMessage.setValue(Utils.processError(response.errorBody(), getApplication()));
        }

    }
}

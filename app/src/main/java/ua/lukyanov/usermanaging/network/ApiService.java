package ua.lukyanov.usermanaging.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ua.lukyanov.usermanaging.network.models.LoginRequest;
import ua.lukyanov.usermanaging.network.models.LoginResponse;
import ua.lukyanov.usermanaging.network.models.RegistrationRequest;

public interface ApiService {

    @POST("users/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("users/register")
    Call<ResponseBody> register(@Body RegistrationRequest registerRequest);
}

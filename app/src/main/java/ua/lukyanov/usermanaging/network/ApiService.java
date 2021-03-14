package ua.lukyanov.usermanaging.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ua.lukyanov.usermanaging.network.model.request.LoginRequest;
import ua.lukyanov.usermanaging.network.model.response.LoginResponse;
import ua.lukyanov.usermanaging.network.model.response.ProfilePropertiesResponse;
import ua.lukyanov.usermanaging.network.model.request.RegistrationRequest;

public interface ApiService {

    @POST("users/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("users/register")
    Call<ResponseBody> register(@Body RegistrationRequest registerRequest);

    @GET("users/{userId}")
    Call<ProfilePropertiesResponse> obtainUserProperties(@Path("userId") String userId, @Query("props") String properties);

}

package ua.lukyanov.usermanaging.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.lukyanov.usermanaging.network.ApiService;

@Module
public class NetworkModule {

    private static final String API_BASE_URL = "https://eu-api.backendless.com/";
    private static final String REST_APP_ID = "67E36E68-EFEB-FA00-FF96-BB9DDB586E00";
    private static final String REST_API_KEY = "97C6F541-058E-44C2-8EEC-A820D343D984";

    private String getApiUrl()  {
        return API_BASE_URL + REST_APP_ID + "/" + REST_API_KEY + "/";
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(OkHttpClient okHttpClient)  {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getApiUrl())
                .client(okHttpClient)
                .build();
    }

    @Provides
    OkHttpClient providesOkHttp(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    ApiService provideProductsListApi(Retrofit retrofit)  {
        return retrofit.create(ApiService.class);
    }

}

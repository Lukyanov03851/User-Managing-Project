package ua.lukyanov.usermanaging.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import ua.lukyanov.usermanaging.R;
import ua.lukyanov.usermanaging.network.models.ErrorResponse;

public class Utils {

    public static String processError(ResponseBody response, Context context){
        String errorMsg = context.getString(R.string.common_error);

        if (response != null){
            try {
                Gson gson = new Gson();
                ErrorResponse errorResponse = gson.fromJson(response.string(), ErrorResponse.class);
                if (errorResponse != null){
                    errorMsg = errorResponse.getErrorMessage();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return errorMsg;
    }

}

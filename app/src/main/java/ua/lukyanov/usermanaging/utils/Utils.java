package ua.lukyanov.usermanaging.utils;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import ua.lukyanov.usermanaging.R;
import ua.lukyanov.usermanaging.network.model.response.ErrorResponse;

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

    public static void hideKeyboard(View view){
        if (view != null){
            InputMethodManager inputManager =  (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            IBinder binder = view.getWindowToken();
            inputManager.hideSoftInputFromWindow(
                    binder,
                    InputMethodManager.HIDE_NOT_ALWAYS
            );
        }
    }

    public static void  showKeyboard(View view){
        if (view != null){
            view.requestFocus();
            InputMethodManager inputManager =  (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }



}

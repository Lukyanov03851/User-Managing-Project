package ua.lukyanov.usermanaging.network.models;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("code")
    String errorCode;

    @SerializedName("message")
    String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}

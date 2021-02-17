package ua.lukyanov.usermanaging.network.models;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("login")
    private final String login;

    @SerializedName("password")
    private final String password;

    public LoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

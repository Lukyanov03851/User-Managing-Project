package ua.lukyanov.usermanaging.network.models;

public class LoginRequest {

    private final String login;
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

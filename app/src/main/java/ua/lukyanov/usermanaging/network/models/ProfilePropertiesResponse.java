package ua.lukyanov.usermanaging.network.models;

import com.google.gson.annotations.SerializedName;

public class ProfilePropertiesResponse {

    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("phone")
    String phone;

    @SerializedName("address")
    String address;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "ProfilePropertiesResponse{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

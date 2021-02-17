package ua.lukyanov.usermanaging.network.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{
    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("objectId")
    String objectId;

    @SerializedName("ownerId")
    String ownerId;

    @SerializedName("user-token")
    String token;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", objectId='" + objectId + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

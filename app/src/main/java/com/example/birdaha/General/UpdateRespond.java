package com.example.birdaha.General;

import com.google.gson.annotations.SerializedName;

public class UpdateRespond {
    @SerializedName("success")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

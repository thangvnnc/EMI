package com.gmail.thangvnnc.emi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
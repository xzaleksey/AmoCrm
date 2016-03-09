package com.valyakinaleksey.amocrm.models;

import com.google.gson.annotations.SerializedName;

public class Response<T> {
    @SerializedName("response")
    public T response;
}

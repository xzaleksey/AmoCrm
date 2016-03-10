package com.valyakinaleksey.amocrm.models.api;

import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("subdomain")
    public String subdomain;
    @SerializedName("language")
    public String language;
    @SerializedName("timezone")
    public String timzeone;
}

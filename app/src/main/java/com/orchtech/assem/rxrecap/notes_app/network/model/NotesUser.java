package com.orchtech.assem.rxrecap.notes_app.network.model;

import com.google.gson.annotations.SerializedName;

public class NotesUser extends NotesBaseResponse {

    @SerializedName("api_key")
    String apiKey;

    public String getApiKey() {
        return apiKey;
    }}

package com.example.adindariztiaputri.creditapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsKodepos {
    @SerializedName("kodepos")
    private List<ListKodepos> allKodePos;

    public List<ListKodepos> getAllKodePos(){
        return allKodePos;
    }
}

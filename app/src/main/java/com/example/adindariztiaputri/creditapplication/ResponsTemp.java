package com.example.adindariztiaputri.creditapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsTemp {

    @SerializedName("tempats")
    private List<ListTemp> alltempat;


    public List<ListTemp> getAlltempat() {
        return alltempat;
    }

//    @Override
//    public String toString(){
//        return
//                "ResponsTemp{" +
//                        "allProvinceList = '" + allProvinceList + '\'' +
//                        "}";
//    }
}

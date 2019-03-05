package com.example.adindariztiaputri.creditapplication;

import com.google.gson.annotations.SerializedName;

public class Output {
    @SerializedName("output")
    private String output;

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public String toString(){
        return "output: " + output;
    }
}

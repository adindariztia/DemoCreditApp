package com.example.adindariztiaputri.creditapplication;

import com.google.gson.annotations.SerializedName;

public class ListTemp {

    @SerializedName("id")
    private int id;

    @SerializedName("nama")
    private String nama;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    @Override
    public String toString() {
        return nama;
    }

    public boolean equals(Object obj){
        if (obj instanceof ListTemp){
            ListTemp lt = (ListTemp)obj;
            if (lt.getNama().equals(nama) && lt.getId()==id) return true;
        }

        return false;
    }
}

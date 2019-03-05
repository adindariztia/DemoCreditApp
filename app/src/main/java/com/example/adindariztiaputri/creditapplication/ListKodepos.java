package com.example.adindariztiaputri.creditapplication;

import com.google.gson.annotations.SerializedName;

public class ListKodepos {
    @SerializedName("id")
    private int id;

    @SerializedName("kodepos")
    private String kodepos;

    @SerializedName("kelurahan")
    private String kelurahan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodepos() {
        return kodepos;
    }

    public void setKodepos(String kodepos) {
        this.kodepos = kodepos;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    @Override
    public String toString() {
        return kodepos;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ListKodepos) {
            ListKodepos lk = (ListKodepos) obj;
            if (lk.getKelurahan().equals(kelurahan) && lk.getKodepos() == kodepos) return true;
        }

        return false;

    }

}

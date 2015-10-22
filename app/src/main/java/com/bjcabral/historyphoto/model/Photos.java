package com.bjcabral.historyphoto.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Bruno on 11/10/2015.
 */
public class Photos implements Serializable{
    public String titulo;
    public String local;
    public String data;
    @SerializedName("descricao")
    public String historia;
    @SerializedName("photo")
    public String urlImg;


    public Photos(String titulo, String local, String data, String historia) {
        this.titulo = titulo;
        this.local = local;
        this.data = data;
        this.historia = historia;
    }

    @Override
    public String toString() {
        return this.titulo;
    }

}

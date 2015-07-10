package com.addapp.izum.Model;

/**
 * Created by ILDAR on 16.06.2015.
 */
public class ModelLogin {

    private String name;

    public ModelLogin(){
        this.name = "";
    }

    public ModelLogin(String name){
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}

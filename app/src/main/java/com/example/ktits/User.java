package com.example.ktits;

public class User {
    String FIO, pass, login;
    int id_group;
    public User(String FIO, int id_group, String pass, String login){
        this.FIO=FIO;
        this.id_group=id_group;
        this.login=login;
        this.pass=pass;
    }
    public User(String login,String pass){
        this.login=login;
        this.pass=pass;
        this.id_group=-1;
        this.FIO="";
    }
}
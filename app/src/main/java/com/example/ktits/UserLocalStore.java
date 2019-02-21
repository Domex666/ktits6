package com.example.ktits;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {

    public static final String SP_NAME="userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME,0);

    }

    public void storeUserData(User user){
    SharedPreferences.Editor spEditor = userLocalDatabase.edit();
    spEditor.putString("FIO", user.FIO);
    spEditor.putInt("id_group", user.id_group);
    spEditor.putString("login", user.login);
    spEditor.putString("pass", user.pass);
    spEditor.commit();


    }

    public User getLoggedInUser(){
        String FIO=userLocalDatabase.getString("FIO","");
        int id_group=userLocalDatabase.getInt("id_group",-1);
        String login=userLocalDatabase.getString("login","");
        String pass=userLocalDatabase.getString("pass","");
        User storedUser=new User(FIO, id_group, login, pass);

        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn",loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if (userLocalDatabase.getBoolean("loggedIn",false)==true){
            return true;
        }else {
            return false;
        }
    }




    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}

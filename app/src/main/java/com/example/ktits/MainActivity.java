package com.example.ktits;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button bLogout, rasp;
    EditText eGroups, eFIO, eLogin;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eGroups = (EditText)findViewById(R.id.eGroups);
        eFIO = (EditText)findViewById(R.id.eFIO);
        eLogin = (EditText)findViewById(R.id.eLogin);
        bLogout = (Button)findViewById(R.id.bLogout);
        bLogout.setOnClickListener(this);
        rasp = (Button)findViewById(R.id.rasp);
        rasp.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);



    }


    @Override
    public void onStart(){
        super.onStart();
        if (authenticate()== true){
        displayUserDetails();
        } else {
            startActivity(new Intent(MainActivity.this,logpass.class ));
        }
    }

    private boolean authenticate(){
    return userLocalStore.getUserLoggedIn();
    }

    public void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();
        eLogin.setText(user.login);
        eFIO.setText(user.FIO);
        eGroups.setText(user.id_group + "");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
               startActivity(new Intent(this, logpass.class));
                break;
        }
            switch (v.getId()) {
                case R.id.rasp:
                Intent intent = new Intent(this, Main2Activity.class);
                intent.putExtra("grp", eGroups.getText().toString());
                startActivity(intent);
                break;
            }
    }


}

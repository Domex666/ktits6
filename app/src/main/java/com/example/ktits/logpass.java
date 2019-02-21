package com.example.ktits;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class logpass extends AppCompatActivity implements View.OnClickListener {

    Button bLogin;
    EditText eLogin, ePass;
    TextView mRegLink;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logpass2);
        eLogin=(EditText)findViewById(R.id.eLogin);
        ePass=(EditText)findViewById(R.id.ePass);
        bLogin=(Button)findViewById(R.id.bLogin);
        mRegLink=(TextView) findViewById(R.id.mRegLink);
        bLogin.setOnClickListener(this);
        mRegLink.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                String login = eLogin.getText().toString();
                String pass = ePass.getText().toString();
                User user=new User(login,pass);
                authenticate(user);
                userLocalStore.storeUserData(user);
            userLocalStore.setUserLoggedIn(true);
        break;

            case R.id.mRegLink:
                startActivity(new Intent(this, regist.class));
                break;
        }
    }
    private void authenticate(User user){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchUserDataInBackground(user, new GetUserCallback()
        {
            @Override
            public void done(User returnedUser) {
                if (returnedUser==null){
                    showErrorMessage();
                }else {
                    logUserIn(returnedUser);
                }
            }
        });

        }
    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(logpass.this);
        dialogBuilder.setMessage("Данные о пользователе не верны");
        dialogBuilder.setPositiveButton("Ок", null);
        dialogBuilder.show();

    }
    private void logUserIn(User returnedUser){
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        startActivity(new Intent(this, MainActivity.class));
    }
}

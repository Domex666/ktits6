package com.example.ktits;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class regist extends AppCompatActivity implements View.OnClickListener {
    Button bReg;
    EditText eGroups, eFIO, ePass, eLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        eGroups = (EditText)findViewById(R.id.eGroups);
        eFIO = (EditText)findViewById(R.id.eFIO);
        ePass = (EditText)findViewById(R.id.ePass);
        eLogin = (EditText)findViewById(R.id.eLogin);
        bReg = (Button)findViewById(R.id.bReg);
        bReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bReg:
                String FIO=eFIO.getText().toString();
                String pass=ePass.getText().toString();
                String login=eLogin.getText().toString();
                int id_group=Integer.parseInt(eGroups.getText().toString());
                User user = new User(FIO, id_group, login, pass);
                regUser(user);
                break;
        }
    }
    private void regUser(User user){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
              startActivity(new Intent(regist.this, logpass.class));
            }
        });

    }
}

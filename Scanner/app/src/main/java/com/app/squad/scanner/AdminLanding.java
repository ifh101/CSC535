package com.app.squad.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AdminLanding extends AppCompatActivity implements View.OnClickListener{
    Button bAddUser, bChangePassword, bDeleteUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing);

        bAddUser = (Button) findViewById(R.id.bAddUser);
        bAddUser.setOnClickListener(this);

        bChangePassword = (Button) findViewById(R.id.bChangePassword);
        bChangePassword.setOnClickListener(this);

        bDeleteUser = (Button) findViewById(R.id.bDeleteUser);
        bDeleteUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bAddUser:
                startActivity(new Intent(this, RegisterUserScreen.class));
                break;

            case R.id.bChangePassword:
                startActivity(new Intent(this, ChangePasswordScreen.class));
                break;

            case R.id.bDeleteUser:
                startActivity(new Intent(this, DeleteUserScreen.class));
                break;
        }
    }
}
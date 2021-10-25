package com.example.mycashbookv10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText mUsername, mPassword;
    Button mBtnLogin;
    TextView mBtnRegister;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar2);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mBtnRegister = findViewById(R.id.btnRegister);

        //register
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });

        //login
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String strUsername  = mUsername.getText().toString();
                String strPassword = mPassword.getText().toString();
                Boolean masuk =  db.checkLogin(strUsername,strPassword);
                if (masuk == true){
                    Cursor cursor = db.userByUsername(strUsername);
                    cursor.moveToFirst();
                    String userId = cursor.getString(0).toString();
                    Boolean updateSession = db.upgradeSession(userId,1);
                    if (updateSession == true){
                        Toast.makeText(getApplicationContext(), "Berhasil Login" + userId, Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Username / Password Salah", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
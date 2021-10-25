package com.example.mycashbookv10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PengaturanActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText txtPasswordLama,txtPasswordBaru;
    Button kembali, simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        db = new DatabaseHelper(this);

        txtPasswordBaru = (EditText) findViewById(R.id.txtPasswordBaru);
        txtPasswordLama = (EditText) findViewById(R.id.txtPasswordLama);
        kembali = (Button) findViewById(R.id.btnMain);
        simpan = (Button) findViewById(R.id.btnSimpan);

        //Kembali
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        //Register
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strPassBaru = txtPasswordBaru.getText().toString();
                String strPassLama = txtPasswordLama.getText().toString();
                Cursor crSession = db.sessionUser();
                crSession.moveToFirst();
                String userID = crSession.getString(1).toString();
                Cursor crUserId = db.userById(userID);
                crUserId.moveToFirst();
                String userPass = crUserId.getString(2).toString();
                if (strPassLama.equals(userPass) && !strPassLama.equals(strPassBaru)){
                    Boolean update = db.updatePassword(userID,strPassBaru);
                    if (update == true){
                        Toast.makeText(getApplicationContext(), "Berhasil Mengubah Password", Toast.LENGTH_SHORT).show();
                        Boolean updtSession = db.upgradeSession("kosong", 1);
                        if (updtSession == true){
                            Toast.makeText(getApplicationContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Gagal Mengubah Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Pastikan Anda Mengisikan Dengan Benar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
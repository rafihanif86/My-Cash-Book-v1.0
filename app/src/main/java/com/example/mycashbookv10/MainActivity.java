package com.example.mycashbookv10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    Button logout;
    ImageButton pemasukan, pengeluaran, cashflow, setting;
    TextView textUsername,textPemasukan, textPengeluaran, textSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        logout = (Button) findViewById(R.id.btnLogout);
        pemasukan = (ImageButton) findViewById(R.id.btnPemasukan);
        pengeluaran = (ImageButton) findViewById(R.id.btnPengeluaran);
        cashflow = (ImageButton) findViewById(R.id.btnList);
        setting = (ImageButton) findViewById(R.id.btnSetting);

        textUsername = findViewById(R.id.textUsername);
        textPemasukan = findViewById(R.id.textPemasukan);
        textPengeluaran = findViewById(R.id.textPengeluaran);
        textSaldo = findViewById(R.id.textSaldo);

        Boolean checkSession = db.checkSession();
        if (checkSession == false){
            Intent loginIntent =  new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

        //mengambil data user login
        Cursor crUserId = db.sessionUser();
        crUserId.moveToFirst();
        String userID = crUserId.getString(1).toString();
        Cursor crUsername = db.userById(userID);
        crUsername.moveToFirst();
        String username = crUsername.getString(1).toString();
        textUsername.setText(username);

        //mengambil nominal pemasukan
        Cursor crPemasukan = db.jumlahKas(userID,"in");
        String strpemasukan = "0";
//        Log.d("cound data", String.valueOf(crPemasukan));
        if (crPemasukan.getCount() > 0){
            crPemasukan.moveToFirst();
            strpemasukan = crPemasukan.getString(1).toString();
         }
        textPemasukan.setText("Rp. " + strpemasukan);

        //mengambil nominal pengeluaran
        Cursor crPengeluaran = db.jumlahKas(userID,"out");
        String strpengeluaran = "0";
        if (crPengeluaran.getCount() > 0) {
            crPengeluaran.moveToFirst();
            strpengeluaran = crPengeluaran.getString(1).toString();

        }
        textPengeluaran.setText("Rp. " + strpengeluaran);

        //saldo
        int saldo = Integer.parseInt(strpemasukan) - Integer.parseInt(strpengeluaran);
        textSaldo.setText("Rp. " + String.valueOf(saldo));


        //logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean updtSession = db.upgradeSession("kosong", 1);
                if (updtSession == true){
                    Toast.makeText(getApplicationContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        });

        //Pemasukkan
        pemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PemasukanActivity.class));
                finish();
            }
        });

        //Pengeluaran
        pengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PengeluaranActivity.class));
                finish();
            }
        });

        //cashflow
        cashflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CashFlowActivity.class));
                finish();
            }
        });

        //Pengaturan
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PengaturanActivity.class));
                finish();
            }
        });
    }
}
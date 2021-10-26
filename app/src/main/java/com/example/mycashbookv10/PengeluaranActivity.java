package com.example.mycashbookv10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class PengeluaranActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper db;
    Button kembali, btntanggal, btnwaktu, simpan;
    private EditText txttanggal, txtwaktu, nominal, keterangan;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran);

        db = new DatabaseHelper(this);

        kembali = (Button) findViewById(R.id.btnMain);
        btntanggal = (Button) findViewById(R.id.btntanggal);
        btnwaktu = (Button) findViewById(R.id.btnwaktu);
        txttanggal = (EditText) findViewById(R.id.txttanggal);
        txtwaktu = (EditText) findViewById(R.id.txtwaktu);
        nominal = (EditText) findViewById(R.id.editTextNominal);
        keterangan = (EditText) findViewById(R.id.txtKeterangan);
        simpan = (Button) findViewById(R.id.btnSimpan);


        btntanggal.setOnClickListener(this);
        btnwaktu.setOnClickListener(this);

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
                String strTanggal = txttanggal.getText().toString() + " " + txtwaktu.getText().toString();
//                String strWaktu = txtwaktu.getText().toString();
                String strNominal = nominal.getText().toString();
                String strKeterangan = keterangan.getText().toString();
                Cursor crUserId = db.sessionUser();
                crUserId.moveToFirst();
                String userID = crUserId.getString(1).toString();
                if (strTanggal != "" || strNominal != "" || strKeterangan != ""){
                    Boolean insert = db.insertCashFlow(strTanggal,userID,"out",strNominal, strKeterangan);
                    if (insert == true){
                        Toast.makeText(getApplicationContext(), "Berhasil Menambahkan Pengeluaran", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), CashFlowActivity.class));
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Gagal Menambahkan Pengeluaran", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Pastikan Anda Mengisikan Dengan Benar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btntanggal:

                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txttanggal.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.btnwaktu:

                c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                txtwaktu.setText(hourOfDay + ":" + minute );
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
                break;
        }
    }
}
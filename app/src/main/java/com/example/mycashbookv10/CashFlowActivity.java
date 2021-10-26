package com.example.mycashbookv10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class CashFlowActivity extends AppCompatActivity {

    DatabaseHelper db;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    Button kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_flow);

        db = new DatabaseHelper(this);

        findId();
        displayData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));

        kembali = (Button) findViewById(R.id.btnMain);

        //Kembali
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

    private void displayData() {
        Cursor crUserId = db.sessionUser();
        crUserId.moveToFirst();
        String userID = crUserId.getString(1).toString();

        sqLiteDatabase = db.getReadableDatabase();
        Cursor crKas = sqLiteDatabase.rawQuery("SELECT * FROM kas WHERE userid = ? ORDER BY id DESC, tanggal ASC", new String[]{userID});
        ArrayList<Model>models = new ArrayList<>();
        while (crKas.moveToNext()){
            int id = Integer.parseInt(crKas.getString(0));
            String jenis = crKas.getString(2).toString();
            String nominal = crKas.getString(3).toString();
            String keterangan = crKas.getString(4).toString();
            String tanggal =crKas.getString(5).toString();
            models.add(new Model(id,tanggal,nominal,keterangan,jenis));
        }
        crKas.close();
        recyclerViewAdapter = new RecyclerViewAdapter(this, R.layout.card_item, models, sqLiteDatabase);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void findId() {
        recyclerView = findViewById(R.id.rvCashFlow);
    }
}
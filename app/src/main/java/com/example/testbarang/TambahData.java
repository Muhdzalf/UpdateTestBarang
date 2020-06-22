package com.example.testbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class TambahData extends AppCompatActivity {
    private DatabaseReference database;

    private Button btSubmit;
    private EditText etKode;
    private EditText etnama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        etKode = (EditText) findViewById(R.id.editNo);
        etnama = (EditText) findViewById(R.id.editNama);
        btSubmit = (Button) findViewById(R.id.btnOk);

        database = FirebaseDatabase.getInstance().getReference();

        final Barang barang = (Barang) getIntent().getSerializableExtra("data");

        if (barang != null){
            etKode.setText(barang.getKode());
            etnama.setText(barang.getNama());
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    barang.setKode(etKode.getText().toString());
                    barang.setNama(etnama.getText().toString());
                    updateBarang(barang);
                }
            });
        }else {
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(etKode.getText().toString().isEmpty())&&
                            !(etnama.getText().toString().isEmpty()))
                        submitBrg(new Barang(etKode.getText().toString(),
                                etnama.getText().toString()));
                    else
                        Toast.makeText(getApplicationContext(),"Data tidak boleh kosong",
                                Toast.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etKode.getWindowToken(),0);
                }
            });
        }

    }
    public void submitBrg (Barang brg){
        database.child("Barang").push().setValue(brg).addOnSuccessListener(this,
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        etKode.setText("");
                        etnama.setText("");
                        Toast.makeText(getApplicationContext(),"Data berhasil ditambahkan",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
    public static Intent getActIntent(Activity activity){
        return new Intent(activity, TambahData.class);
    }

    private void updateBarang(Barang brg) {
        database.child("Barang")
                .child(brg.getKode())
                .setValue(brg)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Data berhasil di Update",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}

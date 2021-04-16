package com.achoi.latihanuki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTodo extends AppCompatActivity {
    EditText edtTitle, edtDesc, edtDate;
    Button btnSubmit, btnCancel;
    DatabaseHelper myDb;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        edtTitle = findViewById(R.id.edt_title);
        edtDesc = findViewById(R.id.edt_desc);
        edtDate = findViewById(R.id.edt_date);
        btnSubmit = findViewById(R.id.btn_add);
        btnCancel = findViewById(R.id.btn_can);

        myDb = new DatabaseHelper(this);
        myCalender = Calendar.getInstance();


        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalender.set(Calendar.YEAR, year);
                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();//update label
            }
        };
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTodo.this, date,
                        myCalender.get(Calendar.YEAR),
                        myCalender.get(Calendar.MONTH),
                        myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String desc = edtDesc.getText().toString();
                String date = edtDate.getText().toString();
                if (title.equals("") || date.equals("") || desc.equals("")) {
                    if (title.equals("")) {
                        edtTitle.setError("Judul Harus Diisi...");
                    }
                    if (desc.equals("")) {
                        edtDesc.setError("Deskripsi Harus Diisi...");
                    }
                    if (date.equals("")) {
                        edtDate.setError("Tanggal Harus Diisi...");
                    }
                } else {
                    boolean isInserd = myDb.insertData(title, desc, date);
                    if (isInserd) {
                        Toast.makeText(AddTodo.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddTodo.this, "Data Gagal ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(AddTodo.this, MainActivity.class));
                    finish();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddTodo.this,MainActivity.class));
                finish();
            }
        });
    }

    //untukmengupdate
    private void updateLabel() {
        String myformat = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myformat, Locale.US);
        edtDate.setText(simpleDateFormat.format(myCalender.getTime()));
    }
}
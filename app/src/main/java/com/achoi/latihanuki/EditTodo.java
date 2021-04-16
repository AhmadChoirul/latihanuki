package com.achoi.latihanuki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
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

public class EditTodo extends AppCompatActivity {
    EditText edtTitle,edtDesc,edtDate;
    Button btnUpdate,btnDelete;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalender;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        edtTitle = findViewById(R.id.edt_title);
        edtDesc = findViewById(R.id.edt_desc);
        edtDate = findViewById(R.id.edt_date);
        btnUpdate = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_del);

        myCalender = Calendar.getInstance();
        myDb = new DatabaseHelper(this);

        edtTitle.setText(getIntent().getStringExtra("titletodo"));
        edtDesc.setText(getIntent().getStringExtra("desctodo"));
        edtDate.setText(getIntent().getStringExtra("datetodo"));


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
                new DatePickerDialog(EditTodo.this, date,
                        myCalender.get(Calendar.YEAR),
                        myCalender.get(Calendar.MONTH),
                        myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = edtTitle.getText().toString();
                String desc = edtDesc.getText().toString();
                String date = edtDate.getText().toString();
                String id = getIntent().getStringExtra("idtodo");

                if(title.equals("")||desc.equals("")||date.equals("")){
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
                    boolean isUpdate = myDb.updateData(title, desc, date,id);

                    if(isUpdate){
                        Toast.makeText(EditTodo.this, "Data berhasil diubah",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(EditTodo.this,"Data Gagal diubah",Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(EditTodo.this,MainActivity.class));
                    finish();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getIntent().getStringExtra("idtodo");
                Integer deleteRows = myDb.deleteData(id);

                if(deleteRows > 0){
                    Toast.makeText(EditTodo.this,"Data berhasil dihapus",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditTodo.this,"Data Gagal dihapus",Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(EditTodo.this,MainActivity.class));
                finish();
            }
        });
    }

    private void updateLabel() {
        String myformat = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myformat, Locale.US);
        edtDate.setText(simpleDateFormat.format(myCalender.getTime()));
    }
}
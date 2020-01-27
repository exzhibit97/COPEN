package com.example.copen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.copen.Classes.Exam;
import com.example.copen.Extensions.DatabaseHelper;
import com.example.copen.Extensions.ExamAdapter;
import com.example.copen.Extensions.GlobalClass;

import java.util.ArrayList;
import java.util.List;

public class ExamsActivity extends AppCompatActivity {

    List<Exam> examList;
    SQLiteDatabase myDb;
    DatabaseHelper dbHelp;
    ListView listViewExams;
    ExamAdapter adapter;
    GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);

        listViewExams = findViewById(R.id.listViewExams);
        examList = new ArrayList<>();
        globalClass = (GlobalClass) getApplicationContext();
        myDb = openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, MODE_PRIVATE, null);

        showExams();
    }

    public void showExams(){

        Cursor cursor = myDb.rawQuery("select * from "+ DatabaseHelper.TABLE_NAME, null);

        if(cursor.moveToFirst()){
            do {
                examList.add(new Exam(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            }while(cursor.moveToNext());
        }

        cursor.close();

        adapter = new ExamAdapter(this, R.layout.list_layout_exams, examList, myDb, globalClass);

        listViewExams.setAdapter(adapter);
    }
}

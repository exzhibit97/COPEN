package com.example.copen.Extensions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.copen.Classes.Exam;
import com.example.copen.R;

import java.util.List;

public class ExamAdapter extends ArrayAdapter<Exam> {
    Context context;
    int listLayoutRes;
    List<Exam> examList;
    SQLiteDatabase myDb;
    private GlobalClass globalClass;

    public ExamAdapter(Context context, int listLayoutRes, List<Exam> examsList, SQLiteDatabase myDb, GlobalClass globalClass) {
        super(context, listLayoutRes, examsList);

        this.globalClass = globalClass;
        this.context = context;
        this.listLayoutRes = listLayoutRes;
        this.examList = examsList;
        this.myDb = myDb;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(listLayoutRes, null);

        //getting exams of the specified position
        final Exam exam = examList.get(position);


        //getting views
        final TextView examName = view.findViewById(R.id.examNameTV);
        TextView examKey = view.findViewById(R.id.examKeyTV);
        TextView createDate = view.findViewById(R.id.createdDateTV);

        //adding data to views
        examName.setText(exam.getName());
        examKey.setText(exam.getKey());
        createDate.setText(exam.getCreateDate());

        //we will use these buttons later for update, delete and get key operation
        Button buttonDelete = view.findViewById(R.id.deleteExamBT);
        Button buttonEdit = view.findViewById(R.id.editExamBT);
        Button buttonKey = view.findViewById(R.id.getExamKeyBT);

        buttonKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String sql = "select " + com.example.sqliteapp.DatabaseHelper.COL_3 + " from " + com.example.sqliteapp.DatabaseHelper.TABLE_NAME + " where id = ?";
//                String sql = "select "+ DatabaseHelper.COL_2 +", "+ DatabaseHelper.COL_3 +" from "+ DatabaseHelper.TABLE_NAME+" where id = ?";

//                Cursor cursor = myDb.rawQuery("select " + com.example.sqliteapp.DatabaseHelper.COL_3 + " from " + com.example.sqliteapp.DatabaseHelper.TABLE_NAME + " where id = " + exam.getId(), null);
                Cursor cursor = myDb.rawQuery("select " + DatabaseHelper.COL_2 + ", " + DatabaseHelper.COL_3 + " from " + DatabaseHelper.TABLE_NAME + " where id = " + +exam.getId(), null);
                if (cursor.moveToFirst()) {
                    do {
                        String key = cursor.getString(1);
                        String name = cursor.getString(0);
                        Toast.makeText(context, key + " " + name, Toast.LENGTH_SHORT).show(); // tutaj przekazanie klucza do innej aktywności/widoku
                        globalClass.setTestName(name);
                        globalClass.setAnswers(key.toCharArray());
                    } while (cursor.moveToNext());
                }
                cursor.close();

            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateExam(exam);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM " + DatabaseHelper.TABLE_NAME + " WHERE id = ?";
                        myDb.execSQL(sql, new Integer[]{exam.getId()});
                        reloadExamsFromDatabase();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    private void updateExam(final Exam exam) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update_exam, null);
        builder.setView(view);


        final EditText editTextName = view.findViewById(R.id.editNameText);
        final EditText editTextKey = view.findViewById(R.id.editKeyText);

        editTextName.setText(exam.getName());
        editTextKey.setText(exam.getKey());

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.updateExamBT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String key = editTextKey.getText().toString().trim();

                if (name.isEmpty()) {
                    editTextName.setError("Name can't be blank");
                    editTextName.requestFocus();
                    return;
                }

                if (key.isEmpty()) {
                    editTextKey.setError("Key can't be blank");
                    editTextKey.requestFocus();
                    return;
                }

                String sql = "UPDATE " + DatabaseHelper.TABLE_NAME + " \n" +
                        "SET NAME = ?, \n" +
                        "ANSWER_KEY = ? \n" +
                        "WHERE id = ?;\n";

                myDb.execSQL(sql, new String[]{name, key, String.valueOf(exam.getId())});
                Toast.makeText(context, "Test Updated", Toast.LENGTH_SHORT).show();
                reloadExamsFromDatabase();

                dialog.dismiss();
            }
        });


    }

    private void reloadExamsFromDatabase() {
        Cursor cursor = myDb.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            examList.clear();
            do {
                examList.add(new Exam(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        notifyDataSetChanged();
    }
}

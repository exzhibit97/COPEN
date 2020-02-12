package com.example.copen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.copen.Extensions.DatabaseHelper;


public class KeyActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDb;
    EditText editName, editKey;
    TextView viewTests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);

        myDb = new DatabaseHelper(this);

        editName = (EditText)findViewById(R.id.nameText);
        editKey = (EditText)findViewById(R.id.keyText);
        //btnAddData = (Button)findViewById(R.id.saveBT);
        viewTests = (TextView) findViewById(R.id.testListTV);

        findViewById(R.id.saveBT).setOnClickListener(this);
        viewTests.setOnClickListener(this);

    }

    private boolean inputsAreCorrect(String name, String key) {
        if (name.isEmpty()) {
            editName.setError("Please enter a name");
            editName.requestFocus();
            return false;
        }

        if (key.isEmpty() || key.length() < 20) {
            editKey.setError("Please enter a key");
            editKey.requestFocus();
            return false;
        }
        return true;
    }

    public void addData(){
        if (inputsAreCorrect(editName.getText().toString().trim(), editKey.getText().toString().trim()))
        {
            myDb.insertData(editName.getText().toString().trim(), editKey.getText().toString().trim());
            Toast.makeText(KeyActivity.this, "Dodano do bazy", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(KeyActivity.this, "Nie dodano do bazy", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveBT:

                addData();

                break;

            case R.id.testListTV:

                startActivity(new Intent(this, ExamsActivity.class));
        }
    }
}

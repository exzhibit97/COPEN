package com.example.copen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.copen.Classes.GradeSummary;
import com.example.copen.Extensions.GlobalClass;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.File;

import static com.example.copen.Extensions.PerspectiveCorrection.correctPerspective;

public class ViewActivity extends AppCompatActivity {

    private Handler recognizeHandler = new Handler();

    private ImageView imageView2;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private String recognitionResult;
    private Mat result;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), CameraActivity.class));
        finish();

    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        OpenCVLoader.initDebug();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        imageView2 = findViewById(R.id.imageView2);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        Log.d("successfull", "SUCCESS");
        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        String key = new String(globalClass.getAnswers());
        Bundle extras = getIntent().getExtras();


        result = new Mat();
        if (extras != null) {
            String path = extras.getString("imgPath");
            String resultRec = extras.getString("result");
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (directory.exists()) {
                Log.d("image source:", directory.toString());
                result = correctPerspective(path);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap resultBitmap = Bitmap.createBitmap(result.cols(), result.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(result, resultBitmap);
                imageView2.setImageBitmap(resultBitmap);
                textView.setText(String.format("Read from camera:\n%s", resultRec));
                if (globalClass.getAnswers() != null) {
//                    String answers = new String(globalClass.getAnswers());
                    textView2.setText(String.format("Chosen key:\n%s", key));
                    GradeSummary summary = new GradeSummary();
                    summary = checkAnswers(resultRec, key);
                    textView3.setText("Points scored:\n" + summary.toString());
                } else {
                    textView2.setText("No key chosen!");
                }
            } else {
                Log.d("path wrong", directory.toString());
            }
        } else {
            Log.d("Extras empty", extras.toString());
        }

        TableLayout tl = findViewById(R.id.answerTable);
        int w = 0;
        TableRow tr_head = new TableRow(this);
        tr_head.setId(10 + w);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1f));

        TextView label_Answer = new TextView(this);
        label_Answer.setGravity(Gravity.CENTER);
        label_Answer.setId(20 + w);
        label_Answer.setText("Answer");
        label_Answer.setTextColor(Color.WHITE);
        label_Answer.setPadding(5, 5, 5, 5);
        tr_head.addView(label_Answer);// add the column to the table row here

        TextView label_Key = new TextView(this);
        label_Key.setGravity(Gravity.CENTER);
        label_Key.setId(21 + w);// define id that must be unique
        label_Key.setText("Key"); // set the text for the header
        label_Key.setTextColor(Color.WHITE); // set the color
        label_Key.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_Key); // add the column to the table row here

        TextView label_Point = new TextView(this);
        label_Point.setGravity(Gravity.CENTER);
        label_Point.setId(21 + w);// define id that must be unique
        label_Point.setText("Point"); // set the text for the header
        label_Point.setTextColor(Color.WHITE); // set the color
        label_Point.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_Point); // add the column to the table row here

        tl.addView(tr_head, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1f));

        for (int i = 0; i < 20; i++) {
            char[] ansChar, keyChar;
            String resultRec = extras.getString("result");


            ansChar = resultRec.toCharArray();
            keyChar = key.toCharArray();

            TableRow tr = new TableRow(this);
            if (i % 2 != 0) tr.setBackgroundColor(Color.GRAY);
            tr.setId(100 + i);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1f));

//Create two columns to add as table data, Create a TextView to add date
            TextView labelAnswer = new TextView(this);

            labelAnswer.setGravity(Gravity.CENTER);
            labelAnswer.setId(200 + i);
            labelAnswer.setText(String.format("%s", ansChar[i]));
            labelAnswer.setPadding(2, 0, 5, 0);
            labelAnswer.setTextColor(Color.WHITE);

            tr.addView(labelAnswer);

            TextView labelKey = new TextView(this);

            labelKey.setGravity(Gravity.CENTER);
            labelKey.setId(200 + i);
            labelKey.setText(String.format("%s", keyChar[i]));
            labelKey.setTextColor(Color.WHITE);

            tr.addView(labelKey);

            TextView labelPoint = new TextView(this);

            labelPoint.setGravity(Gravity.CENTER);
            labelPoint.setId(200 + i);
            if (ansChar[i] == keyChar[i]) {
                labelPoint.setText("+");
            } else {
                labelPoint.setText("");
            }
            labelPoint.setTextColor(Color.WHITE);

            tr.addView(labelPoint);

// finally add this to the table row
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1f));
        }
    }


    public GradeSummary checkAnswers(String answers, String key) {
        GradeSummary gradeSummary = new GradeSummary();

        char[] answersArr = answers.toCharArray();
        char[] keyArr = key.toCharArray();

        gradeSummary.setAnswers(answersArr);
        gradeSummary.setKey(keyArr);

        int count = 0;
        for (int i = 0; i < answersArr.length; i++) {
            if (answersArr[i] == keyArr[i]) count++;
        }

        gradeSummary.setPoints(count);

        return gradeSummary;
    }

}

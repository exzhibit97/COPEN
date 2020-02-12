package com.example.copen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.example.copen.Extensions.AnswerRecognition.recognize;
import static com.example.copen.Extensions.PerspectiveCorrection.correctPerspective;

public class TransitionActivity extends AppCompatActivity {

    private String recognitionResult;
    private Mat result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        OpenCVLoader.initDebug();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        Bundle extras = getIntent().getExtras();

        result = new Mat();
        if (extras != null) {
            final String path = extras.getString("imgPath");
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (directory.exists()) {
                Log.d("image source:", directory.toString());
                result = correctPerspective(path);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap resultBitmap = Bitmap.createBitmap(result.cols(), result.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(result, resultBitmap);
            } else {
                Log.d("path wrong", directory.toString());
            }

            InputStream stream = null;
            try {
                stream = getAssets().open("templates/questions2.jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            final Mat questions = new Mat();
            Utils.bitmapToMat(bitmap, questions);

            final ArrayList<Bitmap> answers = new ArrayList<>();
            for (int i = 1; i < 5; i++) {
                InputStream stream2 = null;
                try {
                    stream = getAssets().open("templates/kropa.jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap2 = BitmapFactory.decodeStream(stream);
                answers.add(bitmap2);
            }

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    recognitionResult = recognize(result, questions, answers);
//                    textView.setText(String.format("%s height: %d width: %d", recognitionResult, result.height(), result.width()));
                    Intent i = new Intent(getApplicationContext(), ViewActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("imgPath", path);
                    i.putExtra("result", recognitionResult);
                    startActivity(i);
                }
            });

        }
    }
}

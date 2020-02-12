package com.example.copen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.copen.Extensions.GlobalClass;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Button cameraActivitybtn;
    private Button keySelection;
    private TextView keyName;
    private TextView keyAnswers;
    private ImageView imageView;
    private GlobalClass globalClass;
    private String recognitionResult;
    private Mat result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(this.getClass().getSimpleName(), "DZIALA CZY NIE ? XD " + OpenCVLoader.initDebug());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraActivitybtn = findViewById(R.id.button);
//        imageView = findViewById(R.id.imageView);
        keySelection = findViewById(R.id.button2);
        keyName = findViewById(R.id.textView5);
        keyAnswers = findViewById(R.id.textView7);
        globalClass = (GlobalClass) getApplicationContext();

        if (globalClass.getAnswers() != null ) {
            keyName.setText(globalClass.getTestName());
            keyAnswers.setText(Arrays.toString(globalClass.getAnswers()));
        } else {
            keyName.setText("No key chosen!");
            keyAnswers.setText(Arrays.toString(globalClass.getAnswers()));
        }

//        AssetManager assetManager = getAssets();
//        Mat mat = new Mat();
//        InputStream is = null;
//        try {
//            is = assetManager.open("/templates_bigger/");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        Bitmap bitmap = BitmapFactory.decodeStream(is);
//        Utils.bitmapToMat(bitmap, mat);
//        Canny(mat.clone(), mat, 50, 50);
//        Utils.matToBitmap(mat, bitmap);
//        imageView.setImageBitmap(bitmap);


//        Bundle extras = getIntent().getExtras();
        //File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
//        if(extras!=null) {
//            String s = extras.getString("imgPath");
//            Log.d("passed path:", s);
//            //File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
//            File dir = new File(s);
//            Log.d("abs path:", dir.getAbsolutePath());
//            if (dir.exists()) {
//                Log.d("path: ", dir.toString());
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                Bitmap bitmap2 = BitmapFactory.decodeFile(String.valueOf(dir), options);
//                imageView.setImageBitmap(bitmap2);
//
//            } else {
//                Log.d("dua:", dir.toString());
//            }
//        } else {
//            Log.d("DUA:", "DUA BLADA");
//        }

//        if (extras != null) {
//            String path = extras.getString("imgPath");
//            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
//            if (directory.exists()) {
//                Log.d("image source:", directory.toString());
//                Mat result = new Mat();
//                result = correctPerspective(path);
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                Bitmap resultBitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
//                Utils.matToBitmap(result, resultBitmap);
//                imageView.setImageBitmap(resultBitmap);
//            } else {
//                Log.d("path wrong", directory.toString());
//            }
//        } else {
//            Log.d("Extras empty", extras.toString());
//        }

        keySelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KeyActivity.class);
                startActivity(intent);
            }
        });

        cameraActivitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
            }
        });

//        Bundle extras = getIntent().getExtras();
//
//        result = new Mat();
//        if (extras != null) {
//            final String path = extras.getString("imgPath");
////            Mat result = new Mat();
//            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
//            if (directory.exists()) {
//                Log.d("image source:", directory.toString());
//                result = correctPerspective(path);
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                Bitmap resultBitmap = Bitmap.createBitmap(result.cols(), result.rows(), Bitmap.Config.ARGB_8888);
//                Utils.matToBitmap(result, resultBitmap);
//                //imageView2.setImageBitmap(resultBitmap);
//            } else {
//                Log.d("path wrong", directory.toString());
//            }
//
//
//            InputStream stream = null;
//            try {
//                stream = getAssets().open("templates/questions2.jpg");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Bitmap bitmap = BitmapFactory.decodeStream(stream);
//            final Mat questions = new Mat();
//            Utils.bitmapToMat(bitmap, questions);
//
//            final ArrayList<Bitmap> answers = new ArrayList<>();
//            for (int i = 1; i < 5; i++) {
//                InputStream stream2 = null;
//                try {
//                    stream = getAssets().open("templates/kropa.jpg");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Bitmap bitmap2 = BitmapFactory.decodeStream(stream);
//                answers.add(bitmap2);
//            }
//            Thread thread = new Thread(new Runnable(){
//                @Override
//                public void run(){
//                    recognitionResult = recognize(result, questions, answers);
//                    textView.setText(String.format("%s height: %d width: %d", recognitionResult, result.height(), result.width()));
//                }
//            });
//            recognizeHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    recognitionResult = recognize(result, questions, answers);
//                    textView.setText(String.format("%s height: %d width: %d", recognitionResult, result.height(), result.width()));
//                }
//            });
//            AsyncTask.execute(new Runnable() {
//                @Override
//                public void run() {
//                    recognitionResult = recognize(result, questions, answers);
////                    textView.setText(String.format("%s height: %d width: %d", recognitionResult, result.height(), result.width()));
//                    Intent i = new Intent(getApplicationContext(), ViewActivity.class);
//                    i.putExtra("imgPath", path);
//                    i.putExtra("result", recognitionResult);
//                    startActivity(i);
//                }
//            });


    }
    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        if (globalClass.getAnswers() != null ) {
            keyName.setText(globalClass.getTestName());
            keyAnswers.setText(Arrays.toString(globalClass.getAnswers()));
        } else {
            keyName.setText("No key chosen!");
            keyAnswers.setText(Arrays.toString(globalClass.getAnswers()));
        }
    }
}
package com.example.copen.Extensions;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.TM_CCOEFF_NORMED;

public class AnswerRecognition {

    public static String recognize(Mat sourceMat, Mat templateMat, ArrayList answersArray) {
        Mat source = sourceMat;
        Imgproc.cvtColor(source, source, COLOR_BGR2GRAY);
        Mat template = templateMat;
        Imgproc.cvtColor(template, template, COLOR_BGR2GRAY);
        String filePath = "C:\\Users\\Patryk\\Desktop\\template\\";
        char[] maxValMatches = new char[]{'A', 'B', 'C', 'D'};
        char[] finalAnswers = new char[20];
        char[] tempKey = new char[]{'A', 'B', 'C', 'B', 'B', 'A', 'A', 'D', 'C', 'D', 'B', 'B', 'A', 'D', 'C', 'B', 'A', 'D', 'C', 'B'};
//        double[] eachQuestionMaxVals = new double[4];


        int interval = 135;
        Log.d("INTERVAL", interval + "source width" + source.width() + "source height" + source.height() + " template width" + template.width() + "template height" + template.height());
        Mat outputImage = new Mat();
        //Matching question header to whole image
        Imgproc.matchTemplate(source, template, outputImage, TM_CCOEFF_NORMED);
        Core.MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        //create point with coordinates from where to draw rectangle
        Point matchLoc = mmr.maxLoc;
        Log.d("MATCHLOC: ", String.valueOf(matchLoc.x) + " " + matchLoc.y);
        int row = 0;
        //drawing rectangle around question header matched template
        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + interval,
                matchLoc.y + template.height()), new Scalar(0, 0, 255));
        //rectangle with first question answers area inside
        for (int i = 0; i < 20; i++) {
            Rect question = new Rect((int) matchLoc.x + 50, (int) matchLoc.y, source.width() - 51, interval);
            //Rect question = new Rect((source.width()/5) - 10, (int)matchLoc.y, source.width() - template.width(), interval);
            //Rect question = new Rect((source.width()/5)-10, row, (source.width()/5)*4, (source.height()/20) );
            Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + (template.cols()) * 5,
                    matchLoc.y + interval), new Scalar(0, 0, 255));
            //create new mat from rectangle area
            //Log.d("question rectangle :", question.height  + " by " + question.width + ". Starting from:" + matchLoc.x + " " + matchLoc.y+interval);
            Mat questionMat = new Mat(source, question);
            Point matchQ = null;
            for (int j = 0; j < 4; j++) {
                //Mat questionTemplate = Imgcodecs.imread(filePath + "\\templates_bigger\\" + (j + 1) + ".jpg", IMREAD_GRAYSCALE);
                Mat questionTemplate = new Mat();
                Utils.bitmapToMat((Bitmap) answersArray.get(j), questionTemplate);
                Imgproc.cvtColor(questionTemplate, questionTemplate, COLOR_BGR2GRAY);
                //match first question area with possible answer jpegs (empty A, B, C and D images)
                Mat outputFirstQ = new Mat();
                Imgproc.matchTemplate(questionMat, questionTemplate, outputFirstQ, TM_CCOEFF_NORMED);
                Core.MinMaxLocResult minMaxQuestion = Core.minMaxLoc(outputFirstQ);
                //Log.d("Question: ",(i + 1) + " checked answer: [" + maxValMatches[j] + "] MAXVAL: " + minMaxQuestion.maxVal);
                //eachQuestionMaxVals[j] = minMaxQuestion.maxVal;
                //Log.d("mlk: ", "Matchlock.x " + matchLoc.x + " Matchlock.y " + matchLoc.y);
                matchQ = minMaxQuestion.maxLoc;
                //Log.d("MatchQ: ","X: " + matchQ.x + " Y:" + matchQ.y);
                //draw and rectangle in place where template matches with source image
                Imgproc.rectangle(questionMat, matchQ, new Point(matchQ.x + (questionTemplate.cols()),
                        matchQ.y + questionTemplate.rows()), new Scalar(255, 0, 0));

            }
            if ((matchQ.x > 100) && (matchQ.x < 200)) {
                finalAnswers[i] = 'A';
            } else if ((matchQ.x >= 230) && (matchQ.x < 350)) {
                finalAnswers[i] = 'B';
            } else if ((matchQ.x >= 350) && (matchQ.x < 500)) {
                finalAnswers[i] = 'C';
            } else if (matchQ.x >= 500) {
                finalAnswers[i] = 'D';
            }


            matchLoc.y += interval;
        }

        StringBuilder stringBuilder = new StringBuilder();
        String answers;
        for (int i = 0; i < 20; i++) {

            stringBuilder.append(finalAnswers[i]);
        }

        return String.valueOf(stringBuilder);
    }
}

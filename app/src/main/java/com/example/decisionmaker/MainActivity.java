package com.example.decisionmaker;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button makeDecisionBtn;
    private TextView message;
    private TextView decisionResult;
    private ProgressBar loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeDecisionBtn = findViewById(R.id.makeDecision);
        message = findViewById(R.id.message);
        decisionResult = findViewById(R.id.decisionResult);
        loader = findViewById(R.id.loader);

        decisionResult.setText("");
        loader.setVisibility(View.INVISIBLE);

        makeDecisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDecision();
            }
        });
    }

    public void makeDecision() {
        message.setText(R.string.randingMessage);
        decisionResult.setText("");
        loader.setVisibility(View.VISIBLE);
        makeDecisionBtn.setEnabled(false);

        Thread makeDecisionThread = new Thread(new MakeDecision());
        makeDecisionThread.start();
    }

    private Runnable showDecision = new Runnable() {
        @Override
        public void run() {
            loader.setVisibility(View.INVISIBLE);
            makeDecisionBtn.setEnabled(true);

            int decision = new Random().nextInt(1000) + 1;
            if(decision % 2 == 0) {
                decisionResult.setText(R.string.resultYes);
            }
            else {
                decisionResult.setText(R.string.resultNo);
            }
        }
    };

    class MakeDecision implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(showDecision);
        }
    }
}

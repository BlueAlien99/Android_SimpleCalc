package com.bluealien99.simplecalc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;

        String[] numpad = {"7", "8", "9", "4", "5", "6", "1", "2", "3", ".", "0", "="};
        String[] keypad = {"√", "^", "×", "÷", "+", "−"};
        ArrayList<String> history = new ArrayList<>();

        ArrayAdapter<String> numpadAdapter =
                new ArrayAdapter<>(this, R.layout.numpadkey, numpad);
        keypadAdapter keypadAdapter =
                new keypadAdapter(this, R.layout.keypadkey, keypad, screenWidth);
        historyAdapter hisListAdapter =
                new historyAdapter(this, R.layout.hisitem, history);

        GridView numpadView = (GridView) findViewById(R.id.numpad);
        GridView keypadView = (GridView) findViewById(R.id.keypad);
        ListView hisList = (ListView) findViewById(R.id.hisList);

        numpadView.setAdapter(numpadAdapter);
        keypadView.setAdapter(keypadAdapter);
        hisList.setAdapter(hisListAdapter);

        TextView outputView = (TextView) findViewById(R.id.output);
        FrameLayout delKey = (FrameLayout) findViewById(R.id.delkey);
        final View eqCover = findViewById(R.id.eqcover);
        final int animdur = getResources().getInteger(android.R.integer.config_shortAnimTime);

        final Output output = new Output(outputView, keypad, history, hisListAdapter, hisList, this);

        numpadView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                output.inputNum(id);
            }
        });
        keypadView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                output.inputKey(id);
            }
        });
        delKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                output.clear();
            }
        });
        delKey.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                eqCover.setVisibility(View.VISIBLE);
                eqCover.animate().alpha(1f).setDuration(animdur).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        output.clearHardBtn();
                        eqCover.animate().alpha(0f).setDuration(animdur).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                eqCover.setVisibility(View.GONE);
                            }
                        });
                    }
                });
                return false;
            }
        });
    }
}

package com.example.lbe.commitbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final AnimationButton animationButton = (AnimationButton) findViewById(R.id.btn);
        animationButton.setAnimationButtonListener(new AnimationButton.AnimationButtonListener() {
            @Override
            public void OnClick() {
                animationButton.startAnimation();
            }
        });
    }
}

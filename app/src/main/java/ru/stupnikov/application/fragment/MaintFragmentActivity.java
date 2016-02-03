package ru.stupnikov.application.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 04.02.16.
 */
public class MaintFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);
    }
}

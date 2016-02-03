package ru.stupnikov.application.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 03.02.16.
 */
public class MainFragment1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
       View view = inflater.inflate(R.layout.main_fragment1, container, false);
        return view;
    }

}

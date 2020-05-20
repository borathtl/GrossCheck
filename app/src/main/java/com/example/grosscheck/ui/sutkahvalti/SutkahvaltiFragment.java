package com.example.grosscheck.ui.sutkahvalti;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.grosscheck.R;

public class SutkahvaltiFragment extends Fragment {

    private SutkahvaltiViewModel sutkahvaltiViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sutkahvaltiViewModel =
                ViewModelProviders.of(this).get(SutkahvaltiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sutkahvalti, container, false);

        return root;
    }
}

package com.example.grosscheck.ui.meyvesebze;

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

public class MeyvesebzeFragment extends Fragment {

    private MeyvesebzeViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(MeyvesebzeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_meyvesebze, container, false);

        return root;
    }
}

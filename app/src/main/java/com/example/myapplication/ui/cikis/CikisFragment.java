package com.example.myapplication.ui.cikis;

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

import com.example.myapplication.R;


public class CikisFragment extends Fragment {

    private CikisViewModel cikisViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cikisViewModel =
                ViewModelProviders.of(this).get(CikisViewModel.class);
        View root = inflater.inflate(R.layout.fragment_out, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        cikisViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}

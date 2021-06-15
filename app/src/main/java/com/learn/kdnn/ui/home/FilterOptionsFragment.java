package com.learn.kdnn.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.FragmentFilterOptionsBinding;

public class FilterOptionsFragment extends BottomSheetDialogFragment {

    private FragmentFilterOptionsBinding binding;

    public FilterOptionsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFilterOptionsBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }
}
package com.learn.kdnn.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.learn.kdnn.databinding.FragmentFilterOptionsBinding;

public class FilterOptionsFragment extends BottomSheetDialogFragment {

    private FragmentFilterOptionsBinding binding;

    public FilterOptionsFragment() {
    }

    public static FilterOptionsFragment newInstance() {
        FilterOptionsFragment fragment = new FilterOptionsFragment();
        return fragment;
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
package com.learn.kdnn;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.kdnn.model.Product;

import java.util.HashMap;
import java.util.List;

import lombok.Getter;

@Getter
public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Product>> products;

    private MutableLiveData<HashMap<Integer, Object>> bag;

    public MainViewModel() {
        products = new MutableLiveData<>();
        bag = new MutableLiveData<>();



    }
}

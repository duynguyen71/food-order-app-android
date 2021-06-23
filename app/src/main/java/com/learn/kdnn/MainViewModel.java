package com.learn.kdnn;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.kdnn.model.Product;
import com.learn.kdnn.model.ShippingAddress;
import com.learn.kdnn.model.User;

import java.util.HashMap;
import java.util.List;

import lombok.Getter;

@Getter
public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Product>> products;

    private MutableLiveData<HashMap<Integer, Object>> bag;

    private MutableLiveData<Boolean> isUsingGridView;

    private MutableLiveData<User> user;

    private MutableLiveData<Integer> shippingMethod;

    private MutableLiveData<ShippingAddress> shippingAdress;

    public MainViewModel() {
        Log.d("CREATE", "MainViewModel: ");
        products = new MutableLiveData<>();
        bag = new MutableLiveData<>();
        bag.setValue(new HashMap<>());
        user = new MutableLiveData<>();
        shippingMethod = new MutableLiveData<>();
        shippingMethod.setValue(-1);
        shippingAdress = new MutableLiveData<>();

        isUsingGridView = new MutableLiveData<>();
        isUsingGridView.setValue(true);


    }
}

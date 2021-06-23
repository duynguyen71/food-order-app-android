package com.learn.kdnn.ui.account;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.learn.kdnn.model.User;

import java.util.Map;

import lombok.Getter;

@Getter
public class AccountViewModel extends ViewModel {

    private MutableLiveData<User> userInfo = new MutableLiveData<>();

    public AccountViewModel() {
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .addSnapshotListener((value, error) -> {
                        Map<String, Object> data = value.getData();
                        User user = null;
                        if (value.exists() && data != null) {
                            user = new User();
                            user.setAddress((String) data.get("address"));
                            user.setPhoneNumber((String) data.get("phone"));
                            userInfo.setValue(user);
                        }
                    });


    }
}

package com.learn.kdnn.ui.product;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.learn.kdnn.AppCacheManage;
import com.learn.kdnn.MainActivity;
import com.learn.kdnn.MainViewModel;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.FragmentProductDetailsBinding;
import com.learn.kdnn.model.CartItem;
import com.learn.kdnn.model.Comment;
import com.learn.kdnn.model.Product;
import com.learn.kdnn.model.User;
import com.learn.kdnn.ui.review.CommentItemAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductDetailsFragment extends Fragment implements View.OnClickListener {

    public static final String PRODUCT_INDEX = "index";
    public static final String STANDARD_PRICE = "standardPrice";
    public static final String SALES_PRICE = "salesPrice";

    private FragmentProductDetailsBinding binding;
    private MainActivity mainActivity;
    private int index;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(PRODUCT_INDEX);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);

        mainActivity = (MainActivity) getContext();
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        Product product = AppCacheManage.products.get(index);
        double salesPrice = product.getPrice();
        double temp;
        if (product.getDiscountPer() > 0) {
            salesPrice = product.getPrice();
            temp = salesPrice;

            TextView tvStandardPrice = binding.standardPrice;
            tvStandardPrice.setText("$" + String.format("%.2f",temp));
            tvStandardPrice.setPaintFlags(tvStandardPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            salesPrice = salesPrice - salesPrice * (product.getDiscountPer() / 100);
        }
        binding.name.setText(product.getName());

        binding.salesPrice.setText("$" + String.format("%.2f",salesPrice));

        binding.category.setText(product.getCategory());

        Glide.with(getContext()).load(product.getPrimaryImgUrl())
                .centerCrop()
                .into(binding.primaryImg);

        ViewGroup viewGroup = (ViewGroup) binding.thumbImgViewGroup;
        for (int i = 0; i < 7; i++) {
            ImageView iv = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.img_thumb_entry, viewGroup, false);
            Glide.with(getContext())
                    .load(product.getPrimaryImgUrl())
                    .centerCrop()
                    .into(iv);

            viewGroup.addView(iv);


        }
        binding.close.setOnClickListener(v -> mainActivity.getMainNavController().navigate(R.id.action_nav_product_details_to_nav_home));


        binding.btnMinus.setOnClickListener(this);
        binding.btnPlus.setOnClickListener(this);
        //add to bag
        binding.btnAddToBag.setOnClickListener(v -> {
            Map<Integer, Object> items = addProductToBag(mainViewModel);
            ((MainActivity) getContext()).updateBagCounter(items.size());
        });
        binding.ratingsReviewsContainer.saveMyComment.setOnClickListener(v -> {
            TextInputEditText etCmt = binding.ratingsReviewsContainer.etComment;
            String comment = etCmt.getText().toString();
            if (TextUtils.isEmpty(comment)) {
                etCmt.setError("Your comment is blank!");
                return;
            }

        });
        loadReviews();
        return binding.getRoot();

    }

    private void loadReviews() {

        User user = new User();
        user.setFullName("Nguyen Khanh Duy");

        User user1 = new User();
        user1.setFullName("Nguyen Phuon Duy");

        User user2 = new User();
        user2.setFullName("Nguyen  Duy");

        List<Comment> commentList = Arrays
                .asList(new Comment(1, user, "HIHI"),
                        new Comment(2, user1, "LOL"),
                        new Comment(3, user2, "LMAO"));

        RecyclerView commentsView = binding.ratingsReviewsContainer.commentsView;
        commentsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        CommentItemAdapter adapter = new CommentItemAdapter(getContext(), commentList, getLayoutInflater());
        commentsView.setAdapter(adapter);
    }

    private Map<Integer, Object> addProductToBag(MainViewModel mainViewModel) {
        HashMap<Integer, Object> bag = mainViewModel.getBag().getValue();
        if (bag == null) {
            bag = new HashMap<>();
            mainViewModel.getBag().setValue(bag);
        }
        int quality = Integer.parseInt(binding.qualityCounter.getText().toString());

        Product p = AppCacheManage.products.get(this.index);
        bag.put(p.getId(), new CartItem(p, quality));

        FragmentManager manager = mainActivity.getSupportFragmentManager();

        ViewAddedItemBottomSheet.newInstance(this.index, quality)
                .show(manager, "view add to bag item");
        return bag;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
    }

    @Override
    public void onClick(View v) {
        int count = Integer.parseInt(binding.qualityCounter.getText().toString());

        switch (v.getId()) {
            case R.id.btn_minus: {
                if (count > 1)
                    count--;
                break;
            }
            case R.id.btn_plus: {
                count++;
                break;
            }
        }

        binding.qualityCounter.setText(String.valueOf(count));
        return;
    }
}
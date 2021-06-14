package com.learn.kdnn.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.learn.kdnn.AppCacheManage;
import com.learn.kdnn.MainActivity;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.FragmentProductDetailsBinding;
import com.learn.kdnn.model.Product;

import org.jetbrains.annotations.NotNull;


public class ProductDetailsFragment extends Fragment {

    public static final String PRODUCT_INDEX = "index";
    private FragmentProductDetailsBinding binding;
    MainActivity mainActivity;

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

        Product product = AppCacheManage.products.get(index);

        binding.name.setText(product.getName());
        binding.salesPrice.setText("$" + product.getPrice());
        binding.standardPrice.setText("$" + product.getDiscountPer());


        Glide.with(getContext()).load(product.getImgUri())
                .centerCrop()
                .into(binding.primaryImg);

        ViewGroup viewGroup = (ViewGroup) binding.thumbImgViewGroup;
        for (int i = 0; i < 7; i++) {
            ImageView iv = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.img_thumb_entry, viewGroup, false);
            Glide.with(getContext())
                    .load(product.getImgUri())
                    .centerCrop()
                    .into(iv);

            viewGroup.addView(iv);


        }
        binding.close.setOnClickListener(v -> mainActivity.getMainNavController().navigate(R.id.action_nav_product_details_to_nav_home));

        binding.btnAddToBag.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(ViewAddedItemBottomSheet.ADDED_ITEM_INDEX, this.index);
            FragmentManager manager = mainActivity.getSupportFragmentManager();

            ViewAddedItemBottomSheet.newInstance(this.index)
                    .show(manager, "view add to bag item");
        });

        return binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
    }
}
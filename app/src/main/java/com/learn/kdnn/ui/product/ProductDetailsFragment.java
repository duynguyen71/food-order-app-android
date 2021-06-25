package com.learn.kdnn.ui.product;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.learn.kdnn.LoginActivity;
import com.learn.kdnn.MainActivity;
import com.learn.kdnn.MainViewModel;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.FragmentProductDetailsBinding;
import com.learn.kdnn.model.CartItem;
import com.learn.kdnn.model.Comment;
import com.learn.kdnn.model.Product;
import com.learn.kdnn.ui.review.CommentItemAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductDetailsFragment extends Fragment implements View.OnClickListener, CommentItemAdapter.OnRemoveComment {

    public static final String PRODUCT_ID = "id";
    private static final String TAG = ProductDetailsFragment.class.getSimpleName();

    private FragmentProductDetailsBinding binding;
    private long id;
    private MainViewModel mainViewModel;
    private Product product;
    private CommentItemAdapter commentItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong(PRODUCT_ID);
        }

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        List<Product> products = mainViewModel.getProducts();
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getId() == id) {
                product = p;
                break;
            }

        }
        Log.d(TAG, "onCreate: " + product);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);


        TextInputEditText etComment = binding.ratingsReviewsContainer.etComment;
        etComment.setOnClickListener(v -> etComment.setFocusable(true));

        loadComments();


        double salesPrice = product.getPrice();
        double temp;
        if (product.getDiscountPer() > 0) {

            salesPrice = product.getPrice();
            temp = salesPrice;

            TextView tvStandardPrice = binding.standardPrice;
            binding.standardPrice.setVisibility(View.VISIBLE);
            tvStandardPrice.setText("$" + String.format("%.2f", temp));
            tvStandardPrice.setPaintFlags(tvStandardPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            salesPrice = salesPrice - salesPrice * (product.getDiscountPer() / 100);

            binding.discountTag.setVisibility(View.VISIBLE);
            binding.discountTag.setText("-" + product.getDiscountPer() + "%");

        }
        binding.name.setText(product.getName());

        binding.salesPrice.setText("$" + String.format("%.2f", salesPrice));
        binding.category.setText(product.getCategory());

        Glide.with(getContext()).load(product.getPrimaryImgUrl())
                .centerCrop()
                .into(binding.primaryImg);


        if(product.getThumbnailImgUrl()!=null&&!product.getThumbnailImgUrl().isEmpty()){
            ViewGroup viewGroup = (ViewGroup) binding.thumbImgViewGroup;
            for (int i = 0; i < product.getThumbnailImgUrl().size(); i++) {
                String url = product.getThumbnailImgUrl().get(i);
                ImageView iv = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.img_thumb_entry, viewGroup, false);
                Glide.with(getContext())
                        .load(Uri.parse(url))
                        .centerCrop()
                        .into(iv);
                viewGroup.addView(iv);
            }
        }



        binding.close.setOnClickListener(v -> ((MainActivity) getContext()).getMainNavController().navigate(R.id.action_nav_product_details_to_nav_home));
        binding.btnMinus.setOnClickListener(this);
        binding.btnPlus.setOnClickListener(this);
        //add to bag
        binding.btnAddToBag.setOnClickListener(v -> {
            addProductToBag(mainViewModel);
            ((MainActivity) getContext()).updateBagCounter();
        });


        setUpWriteComment();


        return binding.getRoot();

    }

    private void setUpWriteComment() {
        TextInputEditText etComment = binding.ratingsReviewsContainer.etComment;
        Button btnSaveComment = binding.ratingsReviewsContainer.btnSaveComment;
        btnSaveComment.setOnClickListener(v -> {
            Editable text = etComment.getText();
            if (text == null && TextUtils.isEmpty(text.toString())) {
                etComment.setError("Your comment is blank!");
                return;
            }
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Please login before comment")
                        .setPositiveButton("Login", ((dialog, which) -> {
                            Intent i = new Intent(getContext(), LoginActivity.class);
                            startActivity(i);
                        }))
                        .setNegativeButton("Cancel", ((dialog, which) -> {
                            dialog.dismiss();
                        }))
                        .show();
                return;
            }
            String displayName = currentUser.getDisplayName();
            if (displayName == null || displayName.length() == 0) {
                displayName = currentUser.getEmail();
            }
            Comment comment = new Comment(new Date().getTime(), displayName, currentUser.getUid(), text.toString(), new Date());


            FirebaseDatabase
                    .getInstance()
                    .getReference("comments")
                    .child(String.valueOf(product.getId()))
                    .child(currentUser.getUid())
                    .setValue(comment)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            commentItemAdapter.addComment(comment);
                            etComment.setText(null);
                            Toast.makeText(getContext(), "Your comment has been published", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "err", Toast.LENGTH_SHORT).show();
                        }
                    });


        });
    }

    private void loadComments() {


        FirebaseDatabase.getInstance()
                .getReference("comments")
                .child(String.valueOf(product.getId()))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Comment> comments = new ArrayList<>();
                        task.getResult().getChildren().forEach(snapshot -> {
                            Comment comment = (Comment) snapshot.getValue(Comment.class);
                            comments.add(comment);
                        });
                        setUpCommentViews(comments);
                        commentItemAdapter.setOnRemoveComment(this);
                    } else {
                        Log.d(TAG, "loadComments: " + task.getException());

                    }
                });

    }

    private void setUpCommentViews(List<Comment> commentList) {
        RecyclerView commentsView = binding.ratingsReviewsContainer.commentsView;
        commentsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        commentItemAdapter = new CommentItemAdapter(getContext(), commentList, getLayoutInflater());
        commentsView.setAdapter(commentItemAdapter);
    }

    private Map<Long, Object> addProductToBag(MainViewModel mainViewModel) {
        HashMap<Long, Object> bag = mainViewModel.getBag().getValue();
        if (bag == null) {
            bag = new HashMap<>();
            mainViewModel.getBag().setValue(bag);
        }
        int quality = Integer.parseInt(binding.qualityCounter.getText().toString());


        bag.put(product.getId(), new CartItem(product, quality));

        FragmentManager manager = ((MainActivity) getContext()).getSupportFragmentManager();

        ViewAddedItemBottomSheet.newInstance(product.getId(), quality)
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
    }

    @Override
    public void removeComment(int position) {
        String uid = FirebaseAuth.getInstance().getUid();

        FirebaseDatabase
                .getInstance()
                .getReference("comments")
                .child(String.valueOf(product.getId()))
                .child(uid)
                .setValue(null)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        commentItemAdapter.removeComment(position);
                        Toast.makeText(getContext(), "Your comment was removed!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "removeComment: err " + task.getException());
                    }
                });


    }
}
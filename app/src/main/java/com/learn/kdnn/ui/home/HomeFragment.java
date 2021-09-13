package com.learn.kdnn.ui.home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learn.kdnn.MainActivity;
import com.learn.kdnn.MainViewModel;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.FragmentHomeBinding;
import com.learn.kdnn.model.Product;
import com.learn.kdnn.ui.product.ProductDetailsFragment;
import com.learn.kdnn.utils.ApplicationUiUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private MainViewModel mainViewModel;
    private FragmentHomeBinding binding;
    private HomeItemViewAdapter homeItemViewAdapter;
    private MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainActivity = ((MainActivity) getContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);


        getProducts();


        binding.btnFiilter.setOnClickListener(v -> showFilterOptionsFragment());

        setUpSpinner();


        return binding.getRoot();
    }

    private void getProducts() {
        binding.productLoader.setVisibility(View.VISIBLE);
        FirebaseDatabase
                .getInstance()
                .getReference("products")
                .addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            List<Product> productList = new ArrayList<>();
                            for (DataSnapshot data :
                                    snapshot.getChildren()) {
                                productList.add(data.getValue(Product.class));
                            }


                            binding.tv19.setText(productList.size() + " Products");
                            mainViewModel.setProducts(productList);
                            setUpRecyclerView(productList);
                        }
                        binding.productLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        binding.productLoader.setVisibility(View.GONE);
                    }
                });


    }

    @SuppressLint("SetTextI18n")
    public void filter(String text) {
        List<Product> allProduct = mainViewModel.getProducts();
        homeItemViewAdapter.setProducts(allProduct);
        List<Product> filteredList = new ArrayList<>();
        String pattern = text.toLowerCase();
        if (pattern.equals("all")) {
            filteredList = allProduct;
            homeItemViewAdapter.filteredList(filteredList);
            return;
        }
        for (Product product :
                allProduct) {
            if (pattern.equals(product.getCategory().toLowerCase()) || pattern.contains(product.getCategory().toLowerCase()))
                filteredList.add(product);

        }
        ApplicationUiUtils.showCustomToast(getContext(), Toast.LENGTH_SHORT, "Found " + filteredList.size() + " results for " + text.toUpperCase(), getLayoutInflater());
        binding.tv19.setText(filteredList.size() + " RESULTS");
        homeItemViewAdapter.filteredList(filteredList);
    }

    private void setUpSpinner() {
        Spinner spinner = binding.spinner;
        List<String> categories = new ArrayList<>();
        categories.add("All");
        categories.add("Fast food");
        categories.add("Coffee");
        categories.add("Yaourt");
        categories.add("Tea");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, categories);
        spinner.setAdapter(adapter);
    }

    private void setUpRecyclerView(List<Product> productList) {
        RecyclerView productViews = binding.products;
        productViews.setHasFixedSize(true);
        productViews.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        homeItemViewAdapter = new HomeItemViewAdapter(productList, getContext());
        productViews.setAdapter(homeItemViewAdapter);


        setUpSpinnerItemSelected();

        binding.btnFiilter.setOnClickListener(v -> showFilterOptionsFragment());


        //view product details
        homeItemViewAdapter.setOnItemClick((position, item, index) -> {
            Bundle bundle = new Bundle();
            bundle.putLong(ProductDetailsFragment.PRODUCT_ID, item.getId());
            NavController navController = Navigation.findNavController((MainActivity) requireContext(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_nav_home_to_nav_product_details, bundle);
        });

        binding.gridView.setOnClickListener(v -> {
            MutableLiveData<Boolean> useGrid = mainViewModel.getIsUsingGridView();
            if (useGrid == null) {
                return;
            }
            Boolean isUsingGridView = useGrid.getValue();
            if (isUsingGridView) {
                //using list view
                homeItemViewAdapter.setUsingListView(true);
                productViews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                binding.gridView.setImageResource(R.drawable.ic_baseline_view_module_24);
            } else {
                //change to grid view
                productViews.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
                binding.gridView.setImageResource(R.drawable.ic_baseline_view_list_24);
                homeItemViewAdapter.setUsingListView(false);

            }
            homeItemViewAdapter.notifyDataSetChanged();
            useGrid.setValue(!isUsingGridView);
        });
    }

    private void setUpSpinnerItemSelected() {
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                filter(item.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void showFilterOptionsFragment() {
        FragmentManager manager = ((MainActivity) requireContext()).getSupportFragmentManager();
        FilterOptionsFragment.newInstance().show(manager, "filter options fragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
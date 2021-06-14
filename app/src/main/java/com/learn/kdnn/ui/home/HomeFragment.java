package com.learn.kdnn.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.kdnn.AppCacheManage;
import com.learn.kdnn.MainActivity;
import com.learn.kdnn.MainViewModel;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.FragmentHomeBinding;
import com.learn.kdnn.model.Product;
import com.learn.kdnn.ui.product.ProductDetailsFragment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private MainViewModel mainViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final String uri = "https://images.pexels.com/photos/842571/pexels-photo-842571.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940";
        Product product = new Product(1, "Hambugur", 12.00, 5.0, "Fast Food", "https://images.pexels.com/photos/1516415/pexels-photo-1516415.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500");
        Product product1 = new Product(1, "Hambugur", 1.25, 12.0, "Fast Food", uri);
        Product product2 = new Product(1, "Hambugur", 3.5, 10.0, "Fast Food", "https://images.pexels.com/photos/1370939/pexels-photo-1370939.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500");
        Product product3 = new Product(1, "Hambugur", 2.2, 0.0, "Fast Food", "https://images.pexels.com/photos/1516415/pexels-photo-1516415.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500");
        Product product4 = new Product(1, "Hambugur", 1.26, 0.0, "Fast Food", "https://images.pexels.com/photos/1370939/pexels-photo-1370939.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500");
        Product product5 = new Product(1, "Hambugur", 3.25, 0.0, "Fast Food", "https://images.pexels.com/photos/1370939/pexels-photo-1370939.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500");
        Product product6 = new Product(1, "Hambugur", 1.00, 0.0, "Fast Food", "https://images.pexels.com/photos/1370939/pexels-photo-1370939.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500");

        List<Product> products = Arrays.asList(product, product1, product2, product3, product4,product5,product6);

        AppCacheManage.products = products;
        HashMap<Integer, Object> map = new HashMap<>();
        map.put(1, AppCacheManage.products.get(0));
        map.put(2, AppCacheManage.products.get(1));
        map.put(3, AppCacheManage.products.get(2));
        map.put(4, AppCacheManage.products.get(3));
        map.put(5, AppCacheManage.products.get(3));
        map.put(6, AppCacheManage.products.get(4));
        mainViewModel.getBag().setValue(map);



        RecyclerView productViews = binding.products;
        productViews.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        ProductsViewAdapter productsViewAdapter = new ProductsViewAdapter(products, getContext());
        productViews.setAdapter(productsViewAdapter);
        productsViewAdapter.setOnItemClick((position, item, index) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(ProductDetailsFragment.PRODUCT_INDEX, index);
            NavController navController = Navigation.findNavController((MainActivity) getContext(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_nav_home_to_nav_product_details,bundle);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
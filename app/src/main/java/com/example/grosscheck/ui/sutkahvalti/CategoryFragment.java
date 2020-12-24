package com.example.grosscheck.ui.sutkahvalti;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grosscheck.Category;
import com.example.grosscheck.CategoryAdapter;
import com.example.grosscheck.Product;
import com.example.grosscheck.ProductAdapter;
import com.example.grosscheck.R;
import com.example.grosscheck.SubCategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import static java.sql.DriverManager.println;

public class CategoryFragment extends Fragment {
    private int mainCategoryIndex = -1;
    private int subCategoryIndex = -1;
    private Category selectedCategory = null;
    private Category selectedSubCategory = null;
    private CategoryAdapter categoryAdapter;
    private SubCategoryAdapter subCategoryAdapter;
    private ProductAdapter productAdapter;
    private RecyclerView rvCategory;
    private RecyclerView rvSubCategory;
    private RecyclerView rvProduct;
    private ArrayList<Category> mCategoryList = new ArrayList<>();
    private ArrayList<Category> mSubCategoryList = new ArrayList<>();
    private ArrayList<Product> mProductList = new ArrayList<>();
    private int selectedMarket = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sutkahvalti, container, false);
        addCategories();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareCategoryAdapter();
        prepareSubCategoryAdapter();
        prepareProductAdapter();
        mainCategorySelected(0, mCategoryList.get(0), 0, true);
        ImageView ivMigros = requireActivity().findViewById(R.id.iv_migros);
        ImageView ivCarrefour = requireActivity().findViewById(R.id.iv_carrefour);
        ivMigros.setOnClickListener(v -> {
            selectedMarket = 0;
            refreshCategories();
        });
        ivCarrefour.setOnClickListener(v -> {
            selectedMarket = 1;
            refreshCategories();
        });
    }

    private void prepareCategoryAdapter() {
        categoryAdapter = new CategoryAdapter(mCategoryList);
        rvCategory = requireActivity().findViewById(R.id.rv_mainCategory);
        rvCategory.setAdapter(categoryAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        rvCategory.setLayoutManager(linearLayoutManager);
        categoryAdapter.setOnItemClickedListener((view, position) -> mainCategorySelected(position, categoryAdapter.getItems().get(position), 0, false));
    }

    private void prepareSubCategoryAdapter() {
        subCategoryAdapter = new SubCategoryAdapter(mSubCategoryList);
        rvSubCategory = requireActivity().findViewById(R.id.rv_subCategory);
        rvSubCategory.setAdapter(subCategoryAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        rvSubCategory.setLayoutManager(linearLayoutManager);
        subCategoryAdapter.setOnItemClickedListener((view, position) -> subCategorySelected(position, subCategoryAdapter.getItems().get(position), false));
    }

    private void prepareProductAdapter() {
        productAdapter = new ProductAdapter(mProductList);
        rvProduct = requireActivity().findViewById(R.id.rv_products);
        rvProduct.setAdapter(productAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        rvProduct.setLayoutManager(linearLayoutManager);
    }

    private void mainCategorySelected(int index, Category category, int indexOfSelectedChild, boolean forceUpdate) {
        if (forceUpdate) {
            mainCategoryIndex = -1;
        }
        if (index == mainCategoryIndex) {
            return;
        } else {
            println("mainCategorySelected -> " + category.getCategoryName());
            mainCategoryIndex = index;
            categoryAdapter.setSelectedCategoryPosition(mainCategoryIndex);
            rvCategory.scrollToPosition(mainCategoryIndex);
            if (!categoryAdapter.getItems().get(mainCategoryIndex).getSubcategories().isEmpty()) {
                subCategoryAdapter.setItems(categoryAdapter.getItems().get(mainCategoryIndex).getSubcategories()); ///
                Category subCat = categoryAdapter.getItems().get(mainCategoryIndex).getSubcategories().get(0);
                subCategorySelected(indexOfSelectedChild, subCat, true);
            } else {
                clearSubCategoryTab();
//                loadProduct(); bu case olup olmadigi belli degil
            }
        }
    }

    private void subCategorySelected(int index, Category category, boolean forceUpdate) {
        if (forceUpdate) {
            subCategoryIndex = -1;
        }
        if (index == subCategoryIndex) {
            return;
        } else {
            subCategoryIndex = index;
//            subCategoryAdapter.setItems(category.getSubcategories());
            subCategoryAdapter.setSelectedSubCategoryPosition(subCategoryIndex);
//            lmSubCategory.scrollToPositionWithOffset(selectedSubcategoryIndex, 35)
            loadProduct(subCategoryAdapter.getItems().get(subCategoryIndex));
            rvProduct.scrollToPosition(0);
        }
    }

    private void loadProduct(Category category) {
        productAdapter.setItems(category.getProductList());
    }

    private void addCategories() {
        mCategoryList = parseJSON(selectedMarket); //0 migros 1 carrefour
    }

    private void refreshCategories() {
        addCategories();
        prepareCategoryAdapter();
        prepareSubCategoryAdapter();
        prepareProductAdapter();
        categoryAdapter.notifyDataSetChanged();
        subCategoryAdapter.notifyDataSetChanged();
        productAdapter.notifyDataSetChanged();
        mainCategorySelected(0, mCategoryList.get(0), 0, true);
    }

    private void clearSubCategoryTab() {
        subCategoryAdapter.clear();
    }

    public ArrayList<Category> parseJSON(int market) {
        String marketToLoad;
        if (market == 0)
            marketToLoad = "migrosData.json";
        else marketToLoad = "carrefoursaData.json";
        JSONObject reader;
        ArrayList<Category> categoryList = new ArrayList<>();
        try {
            String jsonValue = loadJSONFromAsset(marketToLoad);
            if (jsonValue != null) {
                reader = new JSONObject(jsonValue);
                Iterator<String> catIterator = reader.keys();
                while (catIterator.hasNext()) {
                    String key = catIterator.next();
                    Category tempCat = new Category(key);
                    ArrayList<Category> tempSubCatList = new ArrayList<>();
                    if (reader.get(key) instanceof JSONObject) {
                        JSONObject subCategory = new JSONObject(reader.get(key).toString());
                        Iterator<String> subCatIterator = subCategory.keys();
                        while (subCatIterator.hasNext()) {
                            String subKey = subCatIterator.next();
                            Category tempSubCat = new Category(subKey);
                            tempSubCat.setProductList(new ArrayList<>());
                            ArrayList<Product> tempProdList = new ArrayList<>();
                            JSONArray jsonProdArray = subCategory.getJSONArray(subKey);
                            for (int i = 0; i < jsonProdArray.length(); i++) {
                                JSONObject productJson = (JSONObject) jsonProdArray.get(i);
                                String productName = productJson.getString("productName");
                                String productPrice = productJson.getString("productPrice");
                                String productImage = productJson.getString("productImage");
                                Product product = new Product(productName, productPrice, productImage,"");
                                tempProdList.add(product);
                            }
                            tempSubCat.setProductList(tempProdList);
                            tempSubCatList.add(tempSubCat);
                        }
                    }
                    tempCat.setSubcategories(tempSubCatList);
                    categoryList.add(tempCat);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    private String loadJSONFromAsset(String fileName) {
        String json;
        try {
            InputStream is = getResources().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}

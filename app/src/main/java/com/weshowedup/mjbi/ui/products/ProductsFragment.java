package com.weshowedup.mjbi.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.weshowedup.mjbi.Lists.Category;
import com.weshowedup.mjbi.Module.RetrofitClass;
import com.weshowedup.mjbi.R;
import com.weshowedup.mjbi.Response.MaterialResponse.Datum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsFragment extends Fragment {

    private ProductsViewModel productsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_products, container, false);
        return root;
    }

    private Spinner spinner;
    private ProgressBar progressBar;
    private String category_id;
    private RecyclerView recyclerView;
    private ProductsViewModel adapter;
    private List<Datum> materialData = new ArrayList<>();
    private TextView textView;
    private int currentItem, scrollOutItem, totalItem, page = 1;
    private Boolean isScrolling = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.materials_recycler);
        progressBar = view.findViewById(R.id.material_progress);
        textView = view.findViewById(R.id.material_txt);
        spinner = view.findViewById(R.id.material_spinner);
        List<Category> categoryList = new ArrayList<>();
        spinner.setEnabled(false);
        Category category = new Category("Select", "");
        categoryList.add(category);
        getCategory(categoryList);
        ArrayAdapter<Category> adaptercategory = new ArrayAdapter<Category>(requireContext(), R.layout.spinner_text, categoryList);
        adaptercategory.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adaptercategory);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                recyclerView.stopScroll();
                page = 1;
                materialData.clear();
                category_id = categoryList.get(i).getIndex();
                if (!category_id.equals("")) {
                    textView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    textView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                getMaterialData(page);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new ProductsViewModel(getContext(), materialData);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                    spinner.setVisibility(View.INVISIBLE);
                }
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    spinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = manager.getChildCount();
                totalItem = manager.getItemCount();
                scrollOutItem = manager.findFirstVisibleItemPosition();
                if (isScrolling && (currentItem + scrollOutItem) == totalItem) {
                    isScrolling = false;
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getMaterialData(page);
                }
            }
        });
    }

    private void getMaterialData(int page) {
        try {
            Call<JsonObject> call = new RetrofitClass().retrofit().material(
                    getActivity().getIntent().getStringExtra("id"), category_id, page);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null) {
                        progressBar.setVisibility(View.INVISIBLE);
                        JsonObject jsonObject = response.body();
                        JSONObject jsonObject1 = null;
                        try {
                            jsonObject1 = new JSONObject(jsonObject.toString());
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Datum datum = new Datum();
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                datum.setId(jsonObject2.optString("id"));
                                datum.setName(jsonObject2.optString("name"));
                                datum.setPrice(jsonObject2.optString("price"));
                                datum.setImage(jsonObject2.optString("image"));
                                datum.setStock(jsonObject2.optString("stock"));
                                datum.setCategoryId(jsonObject2.optString("category_id"));
                                materialData.add(datum);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    if (getView() != null) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Snackbar.make(getView(), "Try Again", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getCategory(List<Category> categoryList) {
        try {
            Call<JsonObject> call = new RetrofitClass().retrofit().category(
                    getActivity().getIntent().getStringExtra("id"));
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null) {
                        spinner.setEnabled(true);
                        JsonObject jsonObject = response.body();
                        JSONObject jsonObject1 = null;
                        try {
                            jsonObject1 = new JSONObject(jsonObject.toString());
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                Category datum = new Category(jsonObject2.optString("name"), jsonObject2.optString("id"));
                                datum.setIndex(jsonObject2.optString("id"));
                                datum.setCategory(jsonObject2.optString("name"));
                                categoryList.add(datum);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
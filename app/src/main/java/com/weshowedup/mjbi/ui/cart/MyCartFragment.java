package com.weshowedup.mjbi.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.weshowedup.mjbi.Module.RetrofitClass;
import com.weshowedup.mjbi.R;
import com.weshowedup.mjbi.Response.CartListResponse.Datum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCartFragment extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private MyCartViewModel adapter;
    private List<Datum> mycartList = new ArrayList<>();
    private Boolean isScrolling = false;
    private int currentItem, totalItem, scrollOutItem, page = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.mycart_recycler);
        progressBar = view.findViewById(R.id.my_cart_progress);
        getMyCart(page);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new MyCartViewModel(getContext(), mycartList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
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
                    getMyCart(page);
                }
            }
        });

    }
    private void getMyCart(int page) {
        progressBar.setVisibility(View.VISIBLE);
        try {
            Call<JsonObject> call = new RetrofitClass().retrofit().cartlist(getActivity().getIntent().getStringExtra("id"),
                    page);
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
                                datum.setImage(jsonObject2.optString("image"));
                                datum.setName(jsonObject2.optString("name"));
                                datum.setPrice(jsonObject2.optString("price"));
                                datum.setQuantity(jsonObject2.optString("quantity"));
                                datum.setTotalPrice(jsonObject2.optString("total_price"));
                                datum.setMaterialId(jsonObject2.optString("material_id"));
                                datum.setCartId(jsonObject2.optString("cart_id"));
                                datum.setStock(jsonObject2.optString("stock"));
                                mycartList.add(datum);
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
}

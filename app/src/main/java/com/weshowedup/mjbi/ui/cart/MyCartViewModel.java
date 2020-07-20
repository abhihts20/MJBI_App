package com.weshowedup.mjbi.ui.cart;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.weshowedup.mjbi.Module.RetrofitClass;
import com.weshowedup.mjbi.Module.SharedPrefManager;
import com.weshowedup.mjbi.R;
import com.weshowedup.mjbi.Response.AddCartResponse.AddCartResponse;
import com.weshowedup.mjbi.Response.CartListResponse.Datum;
import com.weshowedup.mjbi.Response.MyOrderResponse.MyOrderResponse;
import com.weshowedup.mjbi.Response.RemoveCartResponse.RemoveCartResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCartViewModel extends RecyclerView.Adapter<MyCartViewModel.ViewHolder> {
    List<Datum> myCartList;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyCartViewModel(Context context, List<Datum> myCartList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.myCartList = myCartList;
    }

    @NonNull
    @Override
    public MyCartViewModel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyCartViewModel.ViewHolder(layoutInflater.inflate(R.layout.card_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartViewModel.ViewHolder holder, int position) {
        Picasso.get().load(myCartList.get(position).getImage()).into(holder.image);
        holder.name.setText(myCartList.get(position).getName());
        holder.quantity.setText(myCartList.get(position).getQuantity());
        SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
        if (Integer.parseInt(myCartList.get(position).getQuantity()) > Integer.parseInt(myCartList.get(position).getStock())) {
            holder.confirm.setEnabled(false);
            holder.confirm.setText("Out of Stock");
            holder.confirm.setBackgroundColor(Color.RED);
        }
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Call<AddCartResponse> call = new RetrofitClass().retrofit().cart(
                            myCartList.get(position).getCartId(), sharedPrefManager.getString("id"),
                            myCartList.get(position).getMaterialId(), Integer.parseInt(myCartList.get(position).getQuantity()), "1"
                    );
                    call.enqueue(new Callback<AddCartResponse>() {
                        @Override
                        public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {
                            if (response.body() != null) {
                                if (response.body().getStatus()) {
                                    holder.confirm.setEnabled(false);
                                    holder.remove.setEnabled(false);
                                    Snackbar.make(v, "Owner will Confirm the order", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    holder.confirm.setEnabled(true);
                                    holder.confirm.setText("Out Of Stock");
                                    holder.confirm.setBackgroundColor(Color.RED);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AddCartResponse> call, Throwable t) {
                            Snackbar.make(v, "Try Again", Snackbar.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Call<RemoveCartResponse> call = new RetrofitClass().retrofit().remove(
                            sharedPrefManager.getString("id"), myCartList.get(position).getCartId()
                    );
                    call.enqueue(new Callback<RemoveCartResponse>() {
                        @Override
                        public void onResponse(Call<RemoveCartResponse> call, Response<RemoveCartResponse> response) {
                            if (response.body() != null) {
                                if (response.body().getStatus()) {
                                    Snackbar.make(v, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                                    holder.confirm.setEnabled(false);
                                    holder.remove.setEnabled(false);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<RemoveCartResponse> call, Throwable t) {
                            Snackbar.make(v, "Try Again", Snackbar.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myCartList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, quantity;
        private ImageView image;
        private Button confirm, remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name_cart);
            quantity = itemView.findViewById(R.id.cart_qty);
            image = itemView.findViewById(R.id.product_image_cart);
            confirm = itemView.findViewById(R.id.confirm_btn_cart);
            remove = itemView.findViewById(R.id.remove_btn_cart);
        }
    }
}

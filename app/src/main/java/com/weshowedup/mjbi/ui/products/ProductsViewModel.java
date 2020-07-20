package com.weshowedup.mjbi.ui.products;

import android.content.Context;
import android.media.Image;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.weshowedup.mjbi.Module.RetrofitClass;
import com.weshowedup.mjbi.Module.SharedPrefManager;
import com.weshowedup.mjbi.R;
import com.weshowedup.mjbi.Response.AddCartResponse.AddCartResponse;
import com.weshowedup.mjbi.Response.MaterialResponse.Datum;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;

public class ProductsViewModel extends RecyclerView.Adapter<ProductsViewModel.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Datum> materialList;

    public ProductsViewModel(Context context, List<Datum> materialList) {
        this.context = context;
        this.layoutInflater = layoutInflater.from(context);
        this.materialList = materialList;
    }

    @NonNull
    @Override
    public ProductsViewModel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsViewModel.ViewHolder(layoutInflater.inflate(R.layout.card_material, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(materialList.get(position).getImage()).into(holder.image);
        holder.name.setText(materialList.get(position).getName());
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (String.valueOf(holder.quantity.getText()).isEmpty()) {
                    holder.add.setEnabled(false);
                } else {
                    holder.add.setEnabled(true);
                }
            }
        };
        holder.quantity.addTextChangedListener(textWatcher);
        holder.add.setEnabled(false);
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
                int quantity = Integer.parseInt(String.valueOf(holder.quantity.getText()));
                int stock = Integer.parseInt(materialList.get(position).getStock());
                if (quantity <= stock) {
                    Call<AddCartResponse> call = new RetrofitClass().retrofit().cart(
                            "0", sharedPrefManager.getString("id"), materialList.get(position).getId()
                            , quantity, "0"
                    );
                    call.enqueue(new Callback<AddCartResponse>() {
                        @Override
                        public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {
                            holder.quantity.setText("");
                            Snackbar.make(v, "Added to Cart", Snackbar.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<AddCartResponse> call, Throwable t) {
                            Snackbar.make(v, "Try Again", Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Snackbar.make(v, "Quantity must be less than " + stock, Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView image;
        TextView name;
        TextInputEditText quantity;
        Button add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.product_name);
            quantity = itemView.findViewById(R.id.product_quantity);
            add = itemView.findViewById(R.id.product_add_btn);
        }
    }

}
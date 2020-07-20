package com.weshowedup.mjbi.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.weshowedup.mjbi.Module.RetrofitClass;
import com.weshowedup.mjbi.R;
import com.weshowedup.mjbi.Response.ProfileResponse.ProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    TextView name, email, phone, address, pincode;
    ProgressBar profileProgress;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        name = root.findViewById(R.id.profile_user_name);
        email = root.findViewById(R.id.profile_user_email);
        phone = root.findViewById(R.id.profile_user_mobile);
        address = root.findViewById(R.id.user_address);
        pincode = root.findViewById(R.id.user_pincode);
        profileProgress=root.findViewById(R.id.progress_profile);
        profile();
        return root;
    }

    private void profile() {
        profileProgress.setVisibility(View.VISIBLE);
        try {
            Call<ProfileResponse> call = new RetrofitClass().retrofit().profile(getActivity().getIntent().getStringExtra("id"), "1");
            call.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            profileProgress.setVisibility(View.INVISIBLE);
                            name.setText(response.body().getData().getName());
                            address.setText(response.body().getData().getAddress());
                            phone.setText(response.body().getData().getMobile());
                            email.setText(response.body().getData().getEmail());
                            pincode.setText(response.body().getData().getPincode());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    profileProgress.setVisibility(View.INVISIBLE);
                    Snackbar.make(requireView(), "Try Again", Snackbar.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
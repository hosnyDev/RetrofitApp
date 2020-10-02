package com.hosnydevtest.retrofitapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.hosnydevtest.retrofitapp.R;
import com.hosnydevtest.retrofitapp.api.RetrofitClient;
import com.hosnydevtest.retrofitapp.model.DefaultResponse;
import com.hosnydevtest.retrofitapp.storage.SharedPrefManager;
import com.hosnydevtest.retrofitapp.ui.MainActivity;
import com.hosnydevtest.retrofitapp.ui.UpdatePasswordActivity;
import com.hosnydevtest.retrofitapp.ui.UpdateProfileActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.setting_update_profile).setOnClickListener(v -> startActivity(new Intent(getActivity(), UpdateProfileActivity.class)));

        view.findViewById(R.id.setting_update_password).setOnClickListener(v -> startActivity(new Intent(getActivity(), UpdatePasswordActivity.class)));

        view.findViewById(R.id.setting_delete_account).setOnClickListener(v -> deleteAccount());

        view.findViewById(R.id.setting_logout).setOnClickListener(v -> logOut()
        );

    }

    private void logOut() {
        if (getActivity() != null) {
            SharedPrefManager.getInstance(getActivity()).logOut();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        }

    }

    private void deleteAccount() {

        if (getActivity() != null)
            new AlertDialog.Builder(getActivity())
                    .setMessage("Are you sure you want to delete this account ?..")
                    .setPositiveButton("YES", (dialog, which) -> {

                        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().deleteUser(
                                SharedPrefManager.getInstance(getActivity()).getUser().getId()
                        );
                        call.enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                if (!response.body().isError()) {
                                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    logOut();
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    })
                    .setPositiveButton("CANCEL", null)
                    .create()
                    .show();

    }
}
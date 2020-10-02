package com.hosnydevtest.retrofitapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hosnydevtest.retrofitapp.R;
import com.hosnydevtest.retrofitapp.adapter.UserAdapter;
import com.hosnydevtest.retrofitapp.api.RetrofitClient;
import com.hosnydevtest.retrofitapp.model.UserModel;
import com.hosnydevtest.retrofitapp.model.UsersResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {

    public static String TAG = "UsersFragment";

    //view
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    //recyclerClass
    private UserAdapter adapter;
    private List<UserModel> model;

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //findView
        recyclerView = view.findViewById(R.id.users_recyclerview);
        progressBar = view.findViewById(R.id.users_progressbar);

        model = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getUsers();
    }

    private void getUsers() {
        Log.d(TAG, "getUsers: start get data");
        progressBar.setVisibility(View.VISIBLE);

        Call<UsersResponse> call = RetrofitClient.getInstance().getApi().getUsers();
        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                assert response.body() != null;
                model = response.body().getUsers();
                adapter = new UserAdapter(model);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onResponse: done");
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }
}
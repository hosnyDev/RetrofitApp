package com.hosnydevtest.retrofitapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hosnydevtest.retrofitapp.R;
import com.hosnydevtest.retrofitapp.model.UserModel;
import com.hosnydevtest.retrofitapp.storage.SharedPrefManager;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView email, name, school;
        email = view.findViewById(R.id.home_email);
        name = view.findViewById(R.id.home_name);
        school = view.findViewById(R.id.home_school);

        UserModel userModel = SharedPrefManager.getInstance(getActivity()).getUser();

        email.setText("Email: " + userModel.getEmail());
        name.setText("Name: " + userModel.getName());
        school.setText("School: " + userModel.getSchool());

    }
}
package com.hosnydevtest.retrofitapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hosnydevtest.retrofitapp.R;
import com.hosnydevtest.retrofitapp.model.UserModel;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {

    private List<UserModel> list;

    public UserAdapter(List<UserModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.format_item_all_users, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder viewHolder, int i) {

        viewHolder.set_name(list.get(i).getName());
        viewHolder.set_email(list.get(i).getEmail());
        viewHolder.set_school(list.get(i).getSchool());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class viewHolder extends RecyclerView.ViewHolder {

        private TextView _name, _email, _school;

        viewHolder(@NonNull View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.item_user_name);
            _email = itemView.findViewById(R.id.item_user_email);
            _school = itemView.findViewById(R.id.item_user_school);
        }

        void set_name(String name) {
            _name.setText(name);
        }

        void set_email(String email) {
            _email.setText(email);
        }

        void set_school(String school) {
            _school.setText(school);
        }
    }


}

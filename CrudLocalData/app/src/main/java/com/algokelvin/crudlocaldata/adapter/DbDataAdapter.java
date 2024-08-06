package com.algokelvin.crudlocaldata.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.algokelvin.crudlocaldata.base.action.RoomDbAction;
import com.algokelvin.crudlocaldata.databinding.ItemDbDataLayoutBinding;
import com.algokelvin.crudlocaldata.db.entity.User;

import java.util.List;

public class DbDataAdapter extends RecyclerView.Adapter<DbDataAdapter.DbDataViewHolder> {
    private final List<User> listUser;
    private final RoomDbAction action;

    public DbDataAdapter(List<User> listUser, RoomDbAction action) {
        this.listUser = listUser;
        this.action = action;
    }

    @NonNull
    @Override
    public DbDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDbDataLayoutBinding binding = ItemDbDataLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DbDataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DbDataViewHolder holder, int position) {
        User user = listUser.get(position);

        if (position == 0) {
            holder.binding.userName.setText("Name");
            holder.binding.userName.setTypeface(null, Typeface.BOLD);
            holder.binding.userDescription.setText("Description");
            holder.binding.userDescription.setTypeface(null, Typeface.BOLD);
        } else {
            holder.binding.userName.setText(user.getName());
            holder.binding.userDescription.setText(user.getDescription());
        }

        holder.binding.btnDelete.setOnClickListener(v -> {
            action.deleteDataInDb(user);
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public static class DbDataViewHolder extends RecyclerView.ViewHolder {
        ItemDbDataLayoutBinding binding;

        public DbDataViewHolder(@NonNull ItemDbDataLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

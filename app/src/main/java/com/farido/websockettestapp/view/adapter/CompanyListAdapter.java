package com.farido.websockettestapp.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.farido.websockettestapp.R;
import com.farido.websockettestapp.db.entity.CompanyDBEntity;

import java.util.ArrayList;
import java.util.List;

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.CompanyViewHolder> {

    private ArrayList<CompanyDBEntity> companyList = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<CompanyDBEntity> newCompanyList) {
        companyList.clear();
        companyList.addAll(newCompanyList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_holder_company, parent, false);
        return new CompanyViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        ConstraintLayout viewHolderItemConstraintLayout = holder.itemView.findViewById(R.id.viewHolderItemConstraintLayout);
        ImageView itemImageView = holder.itemView.findViewById(R.id.itemImageView);
        TextView itemName = holder.itemView.findViewById(R.id.itemNameTextView);
        TextView itemSubName = holder.itemView.findViewById(R.id.itemSubNameTextView);

        if (companyList.get(position).getDirection().equals("down")) {
            itemImageView.setImageDrawable(itemImageView.getResources().getDrawable(R.drawable.ic_baseline_trending_down_24, itemImageView.getContext().getTheme()));
            itemImageView.setColorFilter(ContextCompat.getColor(itemImageView.getContext(), R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (companyList.get(position).getDirection().equals("up")) {
            itemImageView.setImageDrawable(itemImageView.getResources().getDrawable(R.drawable.ic_baseline_trending_up_24, itemImageView.getContext().getTheme()));
            itemImageView.setColorFilter(ContextCompat.getColor(itemImageView.getContext(), R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            itemImageView.setImageDrawable(itemImageView.getResources().getDrawable(R.drawable.ic_baseline_trending_flat_24, itemImageView.getContext().getTheme()));
            itemImageView.setColorFilter(ContextCompat.getColor(itemImageView.getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        itemName.setText(companyList.get(position).getName());
        itemSubName.setText(companyList.get(position).getValue1());
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public static class CompanyViewHolder extends RecyclerView.ViewHolder {
        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

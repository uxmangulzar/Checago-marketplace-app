package com.softarena.checagocoffee.Acitivity.Orders;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softarena.checagocoffee.Acitivity.Category.AllCategoryResponseModel;
import com.softarena.checagocoffee.R;


import java.util.List;

public class SelectCategoryAdapter extends RecyclerView.Adapter<SelectCategoryAdapter.SelectCategoryViewHolder> {
    private List<AllCategoryResponseModel> getAllCategoryResponseModelList;
    Context context;
    private int lastSelectedPosition = 0;
    private OnItemClick mCallback;
    public SelectCategoryAdapter(List<AllCategoryResponseModel> getAllCategoryResponseModelList, Context context, OnItemClick listener) {
        this.getAllCategoryResponseModelList = getAllCategoryResponseModelList;
        this.context = context;
        this.mCallback = listener;
    }

    @NonNull
    @Override
    public SelectCategoryAdapter.SelectCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_select_category_btn, parent, false);
        return new SelectCategoryAdapter.SelectCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectCategoryViewHolder holder, final int position) {
        holder.btn_category.setText(getAllCategoryResponseModelList.get(position).getCategoryName());
      holder.btn_category.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mCallback.onClick(getAllCategoryResponseModelList.get(position).getCategoryId());
              if (position == 0)
              {
                  holder.btn_category.setBackgroundTintList(context.getResources().getColorStateList(R.color.yellow));
                 // holder.btn_category.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_yellow));
              }
              lastSelectedPosition = position;
              notifyDataSetChanged();

          }
      });
      if (lastSelectedPosition == position)
      {
          holder.btn_category.setBackgroundTintList(context.getResources().getColorStateList(R.color.yellow));

       //   holder.btn_category.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_yellow));
          holder.btn_category.setTextColor(Color.parseColor("#ffffff"));


      }
      else
      {
          holder.btn_category.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));

          //holder.btn_category.setBackground(ContextCompat.getDrawable(context,R.drawable.round_btn_cancelled));
          holder.btn_category.setTextColor(Color.parseColor("#000000"));
      }


    }

    @Override
    public int getItemCount() {
        return getAllCategoryResponseModelList.size();
    }

    public class SelectCategoryViewHolder extends RecyclerView.ViewHolder {
        Button btn_category;
        public SelectCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_category = itemView.findViewById(R.id.btn_category);


        }

    }
    public interface OnItemClick {
        void onClick(String value);
    }
}


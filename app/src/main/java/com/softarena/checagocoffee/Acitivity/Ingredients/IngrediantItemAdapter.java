package com.softarena.checagocoffee.Acitivity.Ingredients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.softarena.checagocoffee.R;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IngrediantItemAdapter extends RecyclerView.Adapter<IngrediantItemAdapter.IngrediantItemViewHolder> {
    private Context context;
    private List<IngrediantItemModel> ingrediantItemModelsList;
    private int lastCheckedPosition = -1;
    private OnIngItemClick mCallback;
    List<String> positionsList;
    public IngrediantItemAdapter(Context context, List<IngrediantItemModel> ingrediantItemModelsList,OnIngItemClick mCallback) {
        this.context = context;
        this.ingrediantItemModelsList = ingrediantItemModelsList;
        this.mCallback = mCallback;
        positionsList=new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public IngrediantItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_checkbox,parent,false);
        return new IngrediantItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngrediantItemViewHolder holder, final int position) {

      holder.tv_checkBoxName.setText(ingrediantItemModelsList.get(position).getIngredientName()+"(ZAR"+ingrediantItemModelsList.get(position).getIngredientPrice()+")");

//        if (positionsList.contains(String.valueOf(position))){
//           holder.tv_checkBoxName.setChecked(true);
//        }
        holder.tv_checkBoxName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    positionsList.add(String.valueOf(position));
                }else {

                    positionsList.remove(String.valueOf(position));
                }
                mCallback.onIngClick(ingrediantItemModelsList.get(position));

            }
        });

    }


    @Override
    public int getItemCount() {
        return ingrediantItemModelsList.size();
    }

    public class IngrediantItemViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_ingrediantPrice,tv_ingrediantName;
        CheckBox tv_checkBoxName;

        public IngrediantItemViewHolder(View itemView) {
            super(itemView);
            tv_checkBoxName = itemView.findViewById(R.id.tv_checkBox);
//            tv_checkBoxName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mCallback.onIngClick(ingrediantItemModelsList.get(getAdapterPosition()));
//                    int copyOfLastCheckedPosition = lastCheckedPosition;
//                    lastCheckedPosition = getAdapterPosition();
//                    notifyItemChanged(copyOfLastCheckedPosition);
//                    notifyItemChanged(lastCheckedPosition);
//
//                }
//            });



        }
    }
    public interface OnIngItemClick {
        void onIngClick( IngrediantItemModel ingrediantItemModel);
    }

}

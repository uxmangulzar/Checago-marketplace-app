package com.softarena.checagocoffee.Acitivity.Ingredients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softarena.checagocoffee.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> implements IngrediantItemAdapter.OnIngItemClick {
    private List<IngrediantsModel> ingrediantsModelList;
    Context context;
    private OnIngredientItemSelect mCallback;
    public IngredientAdapter(List<IngrediantsModel> ingrediantsModelList, Context context,OnIngredientItemSelect mCallback)
    {
        this.ingrediantsModelList = ingrediantsModelList;
        this.context = context;
        this.mCallback = mCallback;
    }
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_customize_checkbox,parent,false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {

        if (ingrediantsModelList.get(position).getIngredientAll()!=null&&ingrediantsModelList.get(position).getIngredientAll().size()!=0){
            holder.flavourName.setText(ingrediantsModelList.get(position).getIngredientTypeName());
            IngrediantItemAdapter ingrediantItemAdapter = new IngrediantItemAdapter(context,ingrediantsModelList.get(position).getIngredientAll(),this);
            holder.recyclerView_checkboxName.setLayoutManager(new GridLayoutManager( context,2));
            holder.recyclerView_checkboxName.setAdapter(ingrediantItemAdapter);
        }


    }

    @Override
    public int getItemCount() {
        return ingrediantsModelList.size();
    }

    @Override
    public void onIngClick(IngrediantItemModel ingrediantItemModel) {

        mCallback.onIngredientItemSelectClick(ingrediantItemModel);
    }



    public class IngredientViewHolder extends RecyclerView.ViewHolder
    {

        TextView flavourName;
        RecyclerView recyclerView_checkboxName;


        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            flavourName = itemView.findViewById(R.id.tv_flavourName);
            recyclerView_checkboxName = itemView.findViewById(R.id.reycler_checkbox);



        }

    }
    public interface OnIngredientItemSelect {
        void onIngredientItemSelectClick (IngrediantItemModel ingrediantItemModel);
    }
}

package com.example.graduates.recycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduates.databinding.FragmentBlank2Binding;
import com.example.graduates.listview.Home;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter {

    private List<Home> list;
    private Context context;
    private int layout_id;
    private int recourse_id;
    private LayoutInflater inflater;

    public int getRecourse_id() {
        return recourse_id;
    }

    public List<Home> getList() {
        return list;
    }


    public Context getContext() {
        return context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public int getLayout_id() {
        return layout_id;
    }

    public RecycleAdapter(List<Home> list, Context context, int layout_id,int recourse_id) {
        this.list = list;
        this.context = context;
        this.layout_id = layout_id;
        this.recourse_id = recourse_id;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater,layout_id,parent,false);
        return new MyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewDataBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setVariable(recourse_id,list.get(position));
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

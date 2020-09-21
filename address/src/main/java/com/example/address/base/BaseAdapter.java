package com.example.address.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter {
    protected Context context;
    protected List<T> data = new ArrayList<>();
    private IOnClick iOnClick;

    public BaseAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<T> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setiOnClick(IOnClick iOnClick) {
        this.iOnClick = iOnClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(getLayout(), parent, false);
        final BaseViewHolder holder = new BaseViewHolder(inflate);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iOnClick != null){
                    iOnClick.onClick(holder.getLayoutPosition(),data.get(holder.getLayoutPosition()),view);
                }
            }
        });
        return holder;
    }

    protected abstract int getLayout();

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
        T t = data.get(position);
        bindData(baseViewHolder,t);
    }

    protected abstract void bindData(BaseViewHolder holder, T dataBean);

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clearData(){

    }

    public interface IOnClick<T>{
        void onClick(int position, T t, View view);
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        SparseArray views = new SparseArray();
        public BaseViewHolder(View inflate) {
            super(inflate);
        }
        public View getView(int id){
            View view = (View) views.get(id);
            if (view == null){
                view = itemView.findViewById(id);
                views.append(id,view);
            }
            return view;
        }
    }
}

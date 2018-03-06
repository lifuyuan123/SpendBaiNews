package com.example.lfy.spendbainews.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共adapter
 */

public  abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonAdapter.ViewHolder>{
    private List<T> lists=new ArrayList<>();//数据源
    private int itemId;//布局文件
    private ViewDataBinding binding;//找控件id工具

    public List<T> getLists() {
        return lists;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
    }

    public CommonAdapter(List<T> lists, int itemId) {
        this.lists = lists;
        this.itemId = itemId;
    }

    @Override
    public CommonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将布局item交付给databinding
        binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),itemId,parent,false);
        ViewHolder holder=new ViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommonAdapter.ViewHolder holder, int position) {
      //暴露一个接口给外面设置数据，这里用抽象方法实现
        bindViewItemData(holder.getBinding(),position,lists.get(position));
    }


    protected abstract void bindViewItemData(ViewDataBinding binding, int position, T t);

    @Override
    public int getItemCount() {
        return lists.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ViewDataBinding binding;

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            binding= (ViewDataBinding) itemView.getTag();
        }



        @Override
        public void onClick(View view) {
            if (onItemClickListener!=null){
                onItemClickListener.onItemClick(view,getLayoutPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View item, int position);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}

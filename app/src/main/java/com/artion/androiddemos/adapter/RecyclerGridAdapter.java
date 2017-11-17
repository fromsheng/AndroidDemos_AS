package com.artion.androiddemos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artion.androiddemos.R;

import java.util.List;

/**
 * Created by caijinsheng on 17/11/16.
 */

public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.ViewHolder> {

    private List<String> mDatas;
    private RecyclerItemClickListener mListener;

    public RecyclerGridAdapter(List<String> datas) {
        this.mDatas = datas;
    }

    public void setOnItemClickListener(RecyclerItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()
        ).inflate(R.layout.act_recycle_grid_ite, parent,
                false);
        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String item = mDatas.get(position);
        holder.tvTitle.setText(item);
        if(item.equals("EMPTY")) {
            holder.tvTitle.setVisibility(View.GONE);
            holder.icon.setVisibility(View.GONE);
            holder.rootView.setBackgroundColor(holder.rootView.getResources().getColor(R.color.tab_menu_down));
        } else {
            holder.tvTitle.setVisibility(View.VISIBLE);
            holder.icon.setVisibility(View.VISIBLE);
            holder.rootView.setBackgroundColor(holder.rootView.getResources().getColor(R.color.tab_menu_normal));
        }

        // 对没行的高度进行初始化
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.icon.getLayoutParams();
        params.height = getItemHeight();
        holder.icon.setLayoutParams(params);

        if (mListener != null) {
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null) {
                        if(!item.equals("EMPTY")) {
                            mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }
    }

    private int getItemHeight() {
        return 1080/getColumn(mDatas.size());
    }

    public int getColumn(int size) {
        if(size < 3) {
            return 2;
        } else if (size < 7) {
            return 3;
        } else {
            return 4;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView icon;
        private View rootView;
        public ViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.recycle_grid_item);
            icon = (ImageView) itemView.findViewById(R.id.recycle_grid_item_icon);
            tvTitle = (TextView) itemView.findViewById(R.id.recycle_grid_item_title);
        }
    }
}

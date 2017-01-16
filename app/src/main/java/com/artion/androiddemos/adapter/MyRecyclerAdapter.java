package com.artion.androiddemos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artion.androiddemos.R;
import com.artion.androiddemos.domain.RecyclerItem;

import java.util.List;

/**
 * Created by caijinsheng on 16/9/19.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<RecyclerItem> mDatas = null;
//    private RecycleViewItemListener mListener = null;
//    private ItemTouchHelper mItemTouchHelper;

    public MyRecyclerAdapter(RecyclerView recyclerView, List<RecyclerItem> datas) {

        this.mDatas = datas;

//        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
//            @Override
//            public boolean isLongPressDragEnabled() {
//                return false;
//            }
//
//            @Override
//            public boolean isItemViewSwipeEnabled() {
//                return true;
//            }
//
//            @Override
//            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT);
//            }
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                if(mListener != null) {
//                    mListener.onItemSwipe(viewHolder.getAdapterPosition());
//                }
//            }
//        };
//        mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

//    public void setOnItemListener(RecycleViewItemListener listener) {
//        this.mListener = listener;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()
        ).inflate(R.layout.act_recycler_item, parent,
                false);
        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final RecyclerItem item = mDatas.get(position);

        holder.tvTitle.setText(item.title);
        holder.tvContent.setText(item.content);
        holder.tvDate.setText(item.date);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvContent, tvDate;
        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_item_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_item_content);
            tvDate = (TextView) itemView.findViewById(R.id.tv_item_date);
        }
    }
}

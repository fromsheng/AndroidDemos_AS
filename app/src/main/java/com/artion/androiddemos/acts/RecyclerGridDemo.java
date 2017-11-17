package com.artion.androiddemos.acts;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.adapter.RecyclerGridAdapter;
import com.artion.androiddemos.adapter.RecyclerItemClickListener;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.common.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caijinsheng on 17/11/16.
 */

public class RecyclerGridDemo extends BaseActivity {

    private List<String> mDatas;

    private Button btnSwitch;
    private RecyclerView recyclerView;
    private RecyclerGridAdapter mAdapter = null;
    private GridLayoutManager mManager;

    public List<String> initVirtualDatas(int size) {
        List<String> mDatas = new ArrayList<>();
        String item = null;
        for (int i = 0; i < size; i++) {
            item = "标题" + i;
            mDatas.add(item);
        }

        return mDatas;
    }

    public List<String> getEmptyDatas(int size) {
        int column = getColumn(size);
        int emptyCount = (column - size%column)%column;
        if(emptyCount > 0) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < emptyCount; i++) {
                list.add("EMPTY");
            }
            return list;
        }
        return null;
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

    private List<String> getResults(int size) {
        List<String> list  = initVirtualDatas(size);
        List<String> emptyList = getEmptyDatas(size);
        if(emptyList != null && !emptyList.isEmpty()) {
            list.addAll(emptyList);
        }
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recycler);

        int size = 19;
        mDatas = getResults(size);

        initLayout();
        initListener();

    }

    private void changeShowItemColumn(final int column) {
        mManager.setSpanCount(column);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initLayout() {
        super.initLayout();

        btnSwitch = (Button) findViewById(R.id.recycler_btn);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mManager = new GridLayoutManager(this, getColumn(mDatas.size()));
        recyclerView.setLayoutManager(mManager);
        recyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerGridAdapter(mDatas);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ToastUtils.showMessage(mAct, mDatas.get(position) + "==" + position);
            }
        });
    }


    @Override
    protected void initListener() {
        super.initListener();

        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtils.onViewClickTimes(v, 500, new ViewUtils.OnViewClickListener() {
                    @Override
                    public void onClicked(View view, int clickTimes) {
                        mDatas.clear();
                        List<String> tmpList = getResults(clickTimes);
                        mDatas.addAll(tmpList);
                        changeShowItemColumn(getColumn(mDatas.size()));
                    }

                    @Override
                    public void onClicking(View view, int clickTimes) {

                    }
                });
            }
        });
    }
}

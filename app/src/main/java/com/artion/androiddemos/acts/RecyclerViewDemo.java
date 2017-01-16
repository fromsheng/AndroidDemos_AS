package com.artion.androiddemos.acts;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.adapter.MyRecyclerAdapter;
import com.artion.androiddemos.adapter.SectionDecoration;
import com.artion.androiddemos.domain.NameBean;
import com.artion.androiddemos.domain.RecyclerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caijinsheng on 17/1/16.
 */

public class RecyclerViewDemo extends BaseActivity {


    private List<RecyclerItem> mDatas = null;

    public List<RecyclerItem> initVirtualDatas(int size) {
        List<RecyclerItem> mDatas = new ArrayList<>();
        RecyclerItem item = null;
        for (int i = 0; i < size; i++) {
            item = new RecyclerItem();
            item.title = "标题：" + i;
            item.content = "内容：" + i;
            if(i < 3) {
                item.date = "20170101";
            } else if(i < 7) {
                item.date = "20170102";
            } else if(i<10) {
                item.date = "20170103";
            } else if(i<15) {
                item.date = "20170104";
            } else if(i<20) {
                item.date = "20170105";
            } else if(i< 33){
                item.date = "20170106";
            } else  {
                item.date = "20170117";
            }
            mDatas.add(item);
        }

        return mDatas;
    }

    private RecyclerView recyclerView;
    private MyRecyclerAdapter mAdapter = null;
    private SectionDecoration mDecoration = null;
    private List<NameBean> mActions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_recycler);

        mDatas = initVirtualDatas(50);
        mActions = setPullAction(mDatas);

        initLayout();
        initListener();
    }

    private List<NameBean> setPullAction(List<RecyclerItem> datas) {
        List<NameBean> names = new ArrayList<>();
        NameBean nameBean = null;
        for (int i = 0; i < datas.size(); i++) {
            nameBean = new NameBean();
            String name0 = datas.get(i).date;
            nameBean.setName(name0);
            names.add(nameBean);
        }

        return names;
    }

    @Override
    protected void initLayout() {
        super.initLayout();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDecoration = new SectionDecoration(mActions, this, new SectionDecoration.DecorationCallback() {
            @Override
            public String getGroupId(int position) {
                String name = mActions.get(position).getName();
                return name != null ? name : "-1";
            }

            @Override
            public String getGroupFirstLine(int position) {
                String name = mActions.get(position).getName();
                return name != null ? name : "-1";
            }
        });

        recyclerView.addItemDecoration(mDecoration);

        mAdapter = new MyRecyclerAdapter(recyclerView, mDatas);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }
}

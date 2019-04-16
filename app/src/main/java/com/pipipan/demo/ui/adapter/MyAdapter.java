package com.pipipan.demo.ui.adapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.pipipan.base.BaseFragmentAdapter;
import com.pipipan.demo.common.MyLazyFragment;
import com.pipipan.demo.ui.fragment.FragmentMain;
import com.pipipan.demo.ui.fragment.FragmentUser;
import com.pipipan.demo.ui.fragment.FragmentFound;
import com.pipipan.demo.ui.fragment.FragmentOrder;

import java.util.List;

public class MyAdapter extends BaseFragmentAdapter<MyLazyFragment> {
    public MyAdapter(FragmentActivity activity) {
        super(activity);
    }

    @Override
    protected void init(FragmentManager manager, List<MyLazyFragment> list) {
        list.add(FragmentMain.newInstance());
        list.add(FragmentFound.newInstance());
        list.add(FragmentOrder.newInstance());
        list.add(FragmentUser.getInstance());
    }

    public void refresh(int pos, MyLazyFragment fragment){
        getAllFragment().remove(pos);
        getAllFragment().add(pos, fragment);
        notifyDataSetChanged();
    }
}

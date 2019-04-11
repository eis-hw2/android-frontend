package com.pipipan.demo.ui.adapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.pipipan.base.BaseFragmentAdapter;
import com.pipipan.demo.common.MyLazyFragment;
import com.pipipan.demo.ui.fragment.FragmentUser;
import com.pipipan.demo.ui.fragment.TestFragmentA;
import com.pipipan.demo.ui.fragment.TestFragmentB;
import com.pipipan.demo.ui.fragment.TestFragmentC;

import java.util.List;

public class MyAdapter extends BaseFragmentAdapter<MyLazyFragment> {
    public MyAdapter(FragmentActivity activity) {
        super(activity);
    }

    @Override
    protected void init(FragmentManager manager, List<MyLazyFragment> list) {
        list.add(TestFragmentA.newInstance());
        list.add(TestFragmentB.newInstance());
        list.add(TestFragmentC.newInstance());
        list.add(FragmentUser.getInstance());
    }

    public void refresh(int pos, MyLazyFragment fragment){
        getAllFragment().remove(pos);
        getAllFragment().add(pos, fragment);
        notifyDataSetChanged();
    }
}

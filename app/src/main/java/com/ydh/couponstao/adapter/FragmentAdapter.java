package com.ydh.couponstao.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ydh.couponstao.entitys.TitleEntity;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/5/26
 * @describe May the Buddha bless bug-free!!!
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> list_fragment;                         //fragment列表
    private List<TitleEntity> list_Title;                              //tab名的列表


    public FragmentAdapter(FragmentManager fm, List<Fragment> list_fragment, List<TitleEntity> list_Title) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_Title.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return list_Title.get(position % list_Title.size()).getMaterial_name();
    }
}

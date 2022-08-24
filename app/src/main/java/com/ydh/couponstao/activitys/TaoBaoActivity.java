package com.ydh.couponstao.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ydh.couponstao.R;
import com.ydh.couponstao.adapter.FragmentAdapter;
import com.ydh.couponstao.common.MaterialIds;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.entitys.TitleEntity;
import com.ydh.couponstao.fragments.TaoBaoMaterialFragment;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.Strings;
import com.ydh.couponstao.views.TablayoutTabView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaoBaoActivity extends BaseActivity {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    private int type;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private List<TitleEntity> mTitleList;
    private FragmentAdapter mVpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_bao);
        unBind = ButterKnife.bind(this);
        String title = getIntent().getStringExtra("title");
        type = getIntent().getIntExtra("type", 0);
        tvTitle.setText(Strings.getString(title));
        mTitleList = MaterialIds.getMaterial(type);
        for (int i = 0; i < mTitleList.size(); i++) {
            tablayout.addTab(tablayout.newTab().setText(mTitleList.get(i).getMaterial_name()));
            mFragmentList.add(TaoBaoMaterialFragment.newInstance(mTitleList.get(i).getMaterial_id()));
        }
        tablayout.post(new Runnable() {
            @Override
            public void run() {
                new TablayoutTabView().setIndicator(tablayout, CommonUtil.dp2px(10), CommonUtil.dp2px(10));
            }
        });
        mVpAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        //填充适配器
        viewPager.setAdapter(mVpAdapter);
        //TabLayout加载viewpager
        tablayout.setupWithViewPager(viewPager);
    }


    @OnClick({R.id.return_btn, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.iv_search:
                startActivity(SearchTbActivity.class);
                break;
        }
    }
}
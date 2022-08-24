package com.ydh.couponstao.activitys;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jaeger.library.StatusBarUtil;
import com.ydh.couponstao.R;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.utils.PicassoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PictureActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private List<ImageView> mLists = new ArrayList<>();
    private List<String> mUrlList;
    private PagerAdapter mPagerAdapter;
    int curPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor(false);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.color_black_50), 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        unBind = ButterKnife.bind(this);
        curPosition = getIntent().getIntExtra("position", 0);
        if (getIntent().getSerializableExtra("mUrlList") == null) return;
        mUrlList = (List<String>) getIntent().getSerializableExtra("mUrlList");
        if (mUrlList == null || mUrlList.size() == 0) return;
        tvNum.setText((curPosition + 1) + "/" + mUrlList.size());
        initAdapter();
        initData();
        initListener();
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                curPosition = i;
                tvNum.setText((curPosition + 1) + "/" + mLists.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initData() {
        mLists.clear();
        for (int i = 0; i < mUrlList.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            PicassoUtils.setNetImg(mUrlList.get(i), mContext, imageView);
            mLists.add(imageView);
        }
        mPagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(curPosition);
    }

    private void initAdapter() {
        mPagerAdapter = new PagerAdapter() {

            @Override
            public int getCount() {
                return mLists == null ? 0 : mLists.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(mLists.get(position));
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(mLists.get(position));
                return mLists.get(position);
            }
        };
        viewPager.setAdapter(mPagerAdapter);
    }

    @OnClick(R.id.iv_return)
    public void onViewClicked() {
        finish();
    }
}
package com.ydh.couponstao.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.xuexiang.xupdate.XUpdate;
import com.ydh.couponstao.R;
import com.ydh.couponstao.common.CommonDialog;
import com.ydh.couponstao.common.Constant;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.common.updateapp.CustomUpdateParser;
import com.ydh.couponstao.common.updateapp.CustomUpdatePrompter;
import com.ydh.couponstao.dialogs.CheckCopyDialog;
import com.ydh.couponstao.fragments.JingDongFragment;
import com.ydh.couponstao.fragments.TaoBaoFragment;
import com.ydh.couponstao.utils.ClipboardUtils;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.content_fragment)
    FrameLayout contentFragment;
    @BindView(R.id.iv_tao_bao)
    ImageView ivTaoBao;
    @BindView(R.id.tv_tao_bao)
    TextView tvTaoBao;
    @BindView(R.id.ll_tao_bao)
    LinearLayout llTaoBao;
    @BindView(R.id.iv_jing_dong)
    ImageView ivJingDong;
    @BindView(R.id.tv_jing_dong)
    TextView tvJingDong;
    @BindView(R.id.ll_jing_dong)
    LinearLayout llJingDong;
    private Fragment[] mFragments = new Fragment[2];
    private int curIndex;
    private int colorTheme;
    private int colorGray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unBind = ButterKnife.bind(this);
        colorTheme = ContextCompat.getColor(mContext, R.color.color_theme);
        colorGray = ContextCompat.getColor(mContext, R.color.color_gray_60);
        setFragment();
        FragmentUtils.add(getSupportFragmentManager(), mFragments, R.id.content_fragment, curIndex);
        //检查版本更新
        checkUpdate();
    }

    private void checkUpdate() {
        XUpdate.newBuild(this)
                .updateUrl(Constant.UPDATE_URL)
                .updateParser(new CustomUpdateParser())
                .updatePrompter(new CustomUpdatePrompter(this))
                .update();
    }

    private void setFragment() {
        mFragments[0] = TaoBaoFragment.newInstance();//淘宝fragment
        mFragments[1] = JingDongFragment.newInstance();//京东fragment
    }


    //对返回键进行监听
    //退出时的时间
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 1500) {
                CommonUtil.showToast("再按一次离开券券淘");
                mExitTime = System.currentTimeMillis();
            } else {
               /* finish();
                System.exit(0);*/
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.ll_tao_bao, R.id.ll_jing_dong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_tao_bao:
                showCurrentFragment(0);
                break;
            case R.id.ll_jing_dong:
                showCurrentFragment(1);
                break;
        }
    }

    private void showCurrentFragment(int index) {
        if (index == 0) {
            tvTaoBao.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tvTaoBao.setTextColor(colorTheme);
            ivTaoBao.setImageResource(R.mipmap.tao_bao_true);

            tvJingDong.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tvJingDong.setTextColor(colorGray);
            ivJingDong.setImageResource(R.mipmap.jing_dong_false);

        } else if (index == 1) {
            tvTaoBao.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tvTaoBao.setTextColor(colorGray);
            ivTaoBao.setImageResource(R.mipmap.tao_bao_false);

            tvJingDong.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tvJingDong.setTextColor(colorTheme);
            ivJingDong.setImageResource(R.mipmap.jing_dong_true);

        }
        FragmentUtils.showHide(curIndex = index, mFragments);
    }


}
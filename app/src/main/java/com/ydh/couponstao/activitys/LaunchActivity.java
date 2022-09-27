package com.ydh.couponstao.activitys;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xupdate.XUpdate;
import com.ydh.couponstao.R;
import com.ydh.couponstao.common.Constant;
import com.ydh.couponstao.common.SpaceItemDecoration;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.common.updateapp.CustomUpdateParser;
import com.ydh.couponstao.common.updateapp.CustomUpdatePrompter;
import com.ydh.couponstao.dialogs.AgreementDialog;
import com.ydh.couponstao.entitys.HomeEntity;
import com.ydh.couponstao.interfaces.ViewInterface;
import com.ydh.couponstao.smallwidget.SmallWidgetActivity;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.SPUtils;
import com.ydh.couponstao.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        unBind = ButterKnife.bind(this);
        int displayWidth = CommonUtil.getDisplayWidth(this);
        int dp40 = CommonUtil.dp2px(40);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((displayWidth - dp40) / 3, (displayWidth - dp40) / 3);
        ArrayList<HomeEntity> homeEntities = new ArrayList<>();
        homeEntities.add(new HomeEntity(R.drawable.shape_theme, "自动化模式", 1));
        homeEntities.add(new HomeEntity(R.drawable.shape_blue_10, "领优惠券", 2));
        homeEntities.add(new HomeEntity(R.drawable.shape_red_10, "小组件", 3));
        homeEntities.add(new HomeEntity(R.drawable.shape_orange_10, "文字转语音", 4));
        CommonAdapter<HomeEntity> mAdapter = new CommonAdapter<HomeEntity>(mContext, R.layout.item_main, homeEntities) {

            @Override
            protected void convert(ViewHolder holder, HomeEntity homeEntity, int position) {
                TextView mTvMain = holder.getView(R.id.tv_main);
                mTvMain.setLayoutParams(layoutParams);
                mTvMain.setText(Strings.getString(homeEntity.getTitle()));
                mTvMain.setBackgroundResource(homeEntity.getImgRes());
                mTvMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (homeEntity.getType() == 1) {//自动化模式
                            startActivity(AutoClickActivity.class);
                        } else if (homeEntity.getType() == 2) {//领优惠券
                            startActivity(MainActivity.class);
                        } else if (homeEntity.getType() == 3) {//小组件
                            startActivity(SmallWidgetActivity.class);
                        } else if (homeEntity.getType() == 4) {//文字转语音
                            startActivity(VoiceActivity.class);
                        }
                    }
                });
            }
        };
        GridLayoutManager layout = new GridLayoutManager(mContext, 3);
        recyclerView.addItemDecoration(new SpaceItemDecoration(CommonUtil.dp2px(10), SpaceItemDecoration.GRIDLAYOUT));
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(mAdapter);
        //检查版本更新
        checkUpdate();
        if (!"1".equals(SPUtils.getCache(SPUtils.FILE_DATA, SPUtils.IS_FIRST))) {
            new AgreementDialog(mContext, new ViewInterface() {
                @Override
                public void onClick(View view) {
                    finish();
                    System.exit(0);
                }
            });
        }
    }

    private void checkUpdate() {
        XUpdate.newBuild(this)
                .updateUrl(Constant.UPDATE_URL)
                .updateParser(new CustomUpdateParser())
                .updatePrompter(new CustomUpdatePrompter(this))
                .update();
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
}
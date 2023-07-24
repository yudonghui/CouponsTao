package com.ydh.couponstao.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.ydh.couponstao.R;
import com.ydh.couponstao.common.DividerItemDecoration;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.db.DbInterface;
import com.ydh.couponstao.db.DbManager;
import com.ydh.couponstao.db.LotteryEntity;
import com.ydh.couponstao.entitys.DltEntity;
import com.ydh.couponstao.entitys.SsqEntity;
import com.ydh.couponstao.http.HttpClient;
import com.ydh.couponstao.interfaces.YdhInterface;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.JsonParserUtils;
import com.ydh.couponstao.utils.LogUtils;
import com.ydh.couponstao.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LotteryActivity extends BaseActivity {

    @BindView(R.id.tv_dlt)
    TextView tvDlt;
    @BindView(R.id.tv_ssq)
    TextView tvSsq;
    @BindView(R.id.rv_lottery)
    RecyclerView rvLottery;
    @BindView(R.id.tv_ball)
    TextView tvBall;
    private CommonAdapter<LotteryEntity> mLotteryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        unBind = ButterKnife.bind(this);
        initAdapter();
        initData(0);
    }


    @OnClick({R.id.tv_dlt, R.id.tv_ssq, R.id.tv_ball})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dlt:
                pageSize = 10;
                pageNo = 1;
                dltData();
                break;
            case R.id.tv_ssq:
                pageSizessq = 10;
                pageNossq = 1;
                ssqData();
                break;
            case R.id.tv_ball:
                String ball = tvBall.getText().toString();
                if ("双色".equals(ball)) {
                    tvBall.setText("乐透");
                    initData(1);
                } else {
                    tvBall.setText("双色");
                    initData(0);
                }
                break;
        }
    }

    private void localSsq() {
        SsqEntity ssqEntity = GsonUtils.getGson().fromJson(JsonParserUtils.getJson(mContext, "ssq.json"), SsqEntity.class);
        if (ssqEntity != null && ssqEntity.getResult() != null) {
            List<SsqEntity.ResultBean> result = ssqEntity.getResult();
            ArrayList<LotteryEntity> lotteryEntities = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                SsqEntity.ResultBean resultBean = result.get(i);
                String red = resultBean.getRed().replace(",", " ");
                String blue = resultBean.getBlue();
                lotteryEntities.add(new LotteryEntity(1, Strings.getLong(resultBean.getCode()), red + " " + blue, resultBean.getCode()));
            }
            DbManager.getInstance().insertDlt(lotteryEntities, new DbInterface() {
                @Override
                public void success(Object result) {

                }

                @Override
                public void fail() {

                }
            });
        }
    }

    private int pageSizessq = 20;
    private int pageNossq = 1;

    private void ssqData() {
        String url = "https://ydh.zeabur.app/ssqList?issueCount=300&pageSize=" + pageSizessq + "&pageNo=" + pageNossq;
        HttpClient.getInstantce().getLottery(url, new YdhInterface() {
            @Override
            public void onSuccess(String backResult) {
                LogUtils.e(pageNossq + "    " + backResult);
                SsqEntity ssqEntity = GsonUtils.getGson().fromJson(backResult, SsqEntity.class);
                if (ssqEntity != null && ssqEntity.getResult() != null) {
                    List<SsqEntity.ResultBean> result = ssqEntity.getResult();
                    ArrayList<LotteryEntity> lotteryEntities = new ArrayList<>();
                    for (int i = 0; i < result.size(); i++) {
                        SsqEntity.ResultBean resultBean = result.get(i);
                        String red = resultBean.getRed().replace(",", " ");
                        String blue = resultBean.getBlue();
                        lotteryEntities.add(new LotteryEntity(1, Strings.getLong(resultBean.getCode()), red + " " + blue, resultBean.getCode()));
                    }
                    DbManager.getInstance().insertDlt(lotteryEntities, new DbInterface() {
                        @Override
                        public void success(Object result) {
                            if (pageNossq < 15) {
                                cancelLoadingDialog();
                                pageNossq++;
                                ssqData();
                            }
                        }

                        @Override
                        public void fail() {

                        }
                    });
                }
            }

            @Override
            public void onFailure(String result) {

            }
        });

    }

    private int pageSize = 20;
    private int pageNo = 1;

    private void dltData() {
        String url = "https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry?gameNo=85&provinceId=0&isVerify=1&pageSize=" + pageSize + "&pageNo=" + pageNo;
        HttpClient.getInstantce().getLottery(url, new YdhInterface() {
            @Override
            public void onSuccess(String result) {
                DltEntity dltEntity = GsonUtils.getGson().fromJson(result, DltEntity.class);
                if (dltEntity != null && dltEntity.getValue() != null) {
                    DltEntity.ValueBean value = dltEntity.getValue();
                    List<DltEntity.ListBean> list = value.getList();
                    if (list != null) {
                        List<LotteryEntity> lotteryEntities = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            String lotteryDrawNum = list.get(i).getLotteryDrawNum();
                            lotteryEntities.add(new LotteryEntity(0, Strings.getLong(lotteryDrawNum), list.get(i).getLotteryDrawResult(), lotteryDrawNum));
                        }
                        DbManager.getInstance().insertDlt(lotteryEntities, new DbInterface() {
                            @Override
                            public void success(Object result) {
                                if (pageNo < 100) {
                                    pageNo++;
                                    dltData();
                                }
                            }

                            @Override
                            public void fail() {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(String result) {

            }
        });
    }

    private void initData(int type) {
        DbManager.getInstance().queryDlt(type, new DbInterface<List<LotteryEntity>>() {

            @Override
            public void success(List<LotteryEntity> result) {
                mLotteryList.clear();
                mLotteryList.addAll(result);
                if (type == 0)
                    tvDlt.setText("大乐透(" + mLotteryList.size() + ")");
                else
                    tvSsq.setText("双色球(" + mLotteryList.size() + ")");
                mLotteryAdapter.notifyDataSetChanged();
            }

            @Override
            public void fail() {

            }
        });

    }

    private List<LotteryEntity> mLotteryList = new ArrayList<>();
    private int colorBlue;
    private int colorRed;

    private void initAdapter() {
        colorBlue = ContextCompat.getColor(mContext, R.color.blue_ff);
        colorRed = ContextCompat.getColor(mContext, R.color.color_red);
        mLotteryAdapter = new CommonAdapter<LotteryEntity>(mContext, R.layout.item_lottery, mLotteryList) {

            @Override
            protected void convert(ViewHolder holder, LotteryEntity lotteryEntity, int position) {
                holder.setText(R.id.tv_num, lotteryEntity.getLotteryNum());
                String lotteryResult = lotteryEntity.getLotteryResult();
                int type = lotteryEntity.getType();
                TextView tvSix = holder.getView(R.id.tv_six);
                if (type == 0) {//大乐透
                    tvSix.setTextColor(colorBlue);
                } else {//双色球
                    tvSix.setTextColor(colorRed);
                }
                String[] s = lotteryResult.split(" ");
                holder.setText(R.id.tv_one, s[0]);
                holder.setText(R.id.tv_two, s[1]);
                holder.setText(R.id.tv_three, s[2]);
                holder.setText(R.id.tv_four, s[3]);
                holder.setText(R.id.tv_five, s[4]);
                holder.setText(R.id.tv_six, s[5]);
                holder.setText(R.id.tv_seven, s[6]);
            }
        };
        rvLottery.setLayoutManager(new LinearLayoutManager(mContext));
        rvLottery.addItemDecoration(new DividerItemDecoration(LinearLayoutManager.VERTICAL, CommonUtil.dp2px(0.5), ContextCompat.getColor(mContext, R.color.gray_E5)));
        rvLottery.setAdapter(mLotteryAdapter);
    }
}
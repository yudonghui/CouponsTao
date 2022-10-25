package com.ydh.couponstao.activitys;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ydh.couponstao.MyApplication;
import com.ydh.couponstao.R;
import com.ydh.couponstao.common.DividerItemDecoration;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.entitys.DicEntity;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.DeviceIdUtil;
import com.ydh.couponstao.utils.DeviceUtil;
import com.ydh.couponstao.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ydh on 2022/10/19
 * <p>
 * 市场常见CPU架构
 * armeabiv-v7a: 第7代及以上的 32位ARM 处理器
 * arm64-v8a: 第8代、64位ARM处理器
 * armeabi: 第5代、第6代的32位ARM处理器，早期的手机在使用，现在基本很少了。
 * x86: Intel 32位处理器，在平板、模拟器用得比较多。
 * x86_64: Intel 64位处理器，在平板、模拟器用得比较多。
 * <p>
 * String board = Build.BOARD;//主板
 * String brand = Build.BRAND;//系统定制商
 * String[] supportedAbis = Build.SUPPORTED_ABIS;//CPU指令集
 * String device = Build.DEVICE;//设备参数
 * String display = Build.DISPLAY;//显示屏参数
 * String fingerprint = Build.FINGERPRINT;//唯一编号
 * String serial = Build.SERIAL;//硬件序列号
 * String id = Build.ID;//修订版本列表
 * String manufacturer = Build.MANUFACTURER;//硬件制造商
 * String model = Build.MODEL;//版本
 * String hardware = Build.HARDWARE;//硬件名
 * String product = Build.PRODUCT;//手机产品名
 * String tags = Build.TAGS;//描述Build的标签
 * String type = Build.TYPE;//Builder类型
 * String codename = Build.VERSION.CODENAME;//当前开发代码
 * String incremental = Build.VERSION.INCREMENTAL;//源码控制版本号
 * String release = Build.VERSION.RELEASE;//版本字符串
 * int sdkInt = Build.VERSION.SDK_INT;//版本号
 * String host = Build.HOST;//Host值
 * String user = Build.USER;//User名
 * long time = Build.TIME;//编译时间
 */
public class DeviceInfoActivity extends BaseActivity {

    @BindView(R.id.rv_device)
    RecyclerView rvDevice;
    @BindView(R.id.tv_device_id)
    TextView tvDeviceId;
    private List<DicEntity> mDataList = new ArrayList<>();
    private CommonAdapter<DicEntity> mCommonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        unBind = ButterKnife.bind(this);
        tvDeviceId.setText(DeviceIdUtil.getDeviceId());
        mCommonAdapter = new CommonAdapter<DicEntity>(mContext, R.layout.item_device_info, mDataList) {

            @Override
            protected void convert(ViewHolder holder, DicEntity dicEntity, int position) {
                holder.setText(R.id.tv_name, Strings.getString(dicEntity.getName()));
                holder.setText(R.id.tv_value, Strings.getString(dicEntity.getValue()));
            }
        };
        rvDevice.setLayoutManager(new LinearLayoutManager(mContext));
        rvDevice.addItemDecoration(new DividerItemDecoration(LinearLayoutManager.VERTICAL, CommonUtil.dp2px(0.5), ContextCompat.getColor(mContext, R.color.gray_E5)));
        rvDevice.setAdapter(mCommonAdapter);
        initData();
    }

    private void initData() {
        mDataList.add(new DicEntity("型号", DeviceUtil.getDeviceModel()));
        mDataList.add(new DicEntity("厂商名", DeviceUtil.getDeviceManufacturer()));
        //mDataList.add(new DicEntity("发布时间", DateFormtUtils.dateByLong(Build.TIME)));
        mDataList.add(new DicEntity("分辨率（宽*高*密度）", DeviceUtil.getDisplayWidth() + "*" + DeviceUtil.getDisplayHeight() + "*" + DeviceUtil.getDensity()));
        mDataList.add(new DicEntity("Android版本", DeviceUtil.getOsInfo()));
        mDataList.add(new DicEntity("运行内存", DeviceUtil.getMemoryInfo()));
        mDataList.add(new DicEntity("存储空间", "可用：" + DeviceUtil.getAvailSDSize() + "，全部：" + DeviceUtil.getAllSDSize()));
        mDataList.add(new DicEntity("CPU核心数", "" + DeviceUtil.getNumCores()));
        mDataList.add(new DicEntity("CPU架构信息", DeviceUtil.getCPUStruct()));
        mDataList.add(new DicEntity("手机是否Root", DeviceUtil.isRoot() ? "是" : "否"));
        mCommonAdapter.notifyDataSetChanged();
    }

    /**
     * 获取屏幕信息（分辨率、密度）
     */
    public static String getScreenInfo() {
        String screen = "";
        String density = "";      //屏幕密度
        String resolution = ""; // 屏幕分辨率
        WindowManager wm = (WindowManager) MyApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        density = String.valueOf(dm.density); // 屏幕密度（0.75 / 1.0 / 1.5 / 2.0）
        resolution = String.valueOf(dm.heightPixels) + "*" + String.valueOf(dm.widthPixels);
        screen = "屏幕分辨率：  " + resolution + "， 屏幕密度： " + density;
        return screen;
    }


}
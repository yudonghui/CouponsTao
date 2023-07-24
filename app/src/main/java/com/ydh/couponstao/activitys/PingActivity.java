package com.ydh.couponstao.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ydh.couponstao.R;
import com.ydh.couponstao.adapter.PingAdapter;
import com.ydh.couponstao.adapter.SearchAdapter;
import com.ydh.couponstao.common.UrlUtils;
import com.ydh.couponstao.entitys.IpEntiyty;
import com.ydh.couponstao.interfaces.SearchInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PingActivity extends AppCompatActivity {
    private RecyclerView mRecylerView;
    private RecyclerView mRvResult;
    private PingAdapter pingAdapter;
    private SearchAdapter searchAdapter;
    private PingHandler pingHandler;
    private EditText mEtInputNewIp;
    private ExecutorService executorService;
    private List<IpEntiyty> ipList;
    private List<IpEntiyty> list = new ArrayList<>();
    private boolean inputTag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ping);
        initViews();
        initListener();
        initData();
    }

    private void initListener() {
        mEtInputNewIp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (inputTag) {
                    String s = editable.toString();
                    if (TextUtils.isEmpty(s)) {
                        mRvResult.setVisibility(View.GONE);
                        mRecylerView.setVisibility(View.GONE);
                    } else
                        searchIp(s);
                }
            }
        });
    }

    private void searchIp(String s) {
        list.clear();
        for (int i = 0; i < ipList.size(); i++) {
            if (ipList.get(i).getRemark().contains(s)) {
                list.add(ipList.get(i));
            } else if (ipList.get(i).getIp().contains(s)) {
                list.add(ipList.get(i));
            }
        }
        if (list.size() > 0) {
            mRvResult.setVisibility(View.VISIBLE);
            mRecylerView.setVisibility(View.GONE);
        } else {
            mRvResult.setVisibility(View.GONE);
            mRecylerView.setVisibility(View.VISIBLE);
        }
        searchAdapter.notifyDataSetChanged();

    }

    private void initData() {
        ipList = UrlUtils.getUrl();
    }


    private void initViews() {
        mRvResult = findViewById(R.id.rv_result);
        mRecylerView = findViewById(R.id.rv_cycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(layoutManager);
        pingAdapter = new PingAdapter();
        mRecylerView.setAdapter(pingAdapter);

        LinearLayoutManager layoutManagerResult = new LinearLayoutManager(this);
        mRvResult.setLayoutManager(layoutManagerResult);
        searchAdapter = new SearchAdapter(list, new SearchInterface() {
            @Override
            public void onClick(int position) {
                inputTag = false;
                mEtInputNewIp.setText(list.get(position).getIp());
                mEtInputNewIp.setSelection(list.get(position).getIp().length());
                mRecylerView.setVisibility(View.GONE);
                mRvResult.setVisibility(View.GONE);
                inputTag = true;
            }
        });
        mRvResult.setAdapter(searchAdapter);


        pingHandler = new PingHandler(this);
        mEtInputNewIp = findViewById(R.id.et_input_ip);
        findViewById(R.id.btn_ping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pingAdapter.clear();
                String ip = mEtInputNewIp.getText().toString().trim();
                if (!TextUtils.isEmpty(ip)) {
                    mRecylerView.setVisibility(View.VISIBLE);
                    String pingCmd = spellPing(ip);
                    executePingCmd(pingCmd);
                }
            }
        });
    }

    // 1、准备好ping命令
    private String spellPing(String ip) {
        String countCmd = " -c 4 ";
        String sizeCmd = " -s 64 ";
        String timeCmd = " -i 1 ";
        return "ping" + countCmd + timeCmd + sizeCmd + ip;

    }

    // 2、执行ping命令
    private void executePingCmd(String pingCmd) {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Thread(new PingTask(pingCmd, pingHandler, 1)));
    }


    @Override
    public void onDestroy() {
        if (pingHandler != null) {
            pingHandler.removeCallbacksAndMessages(null);
        }
        if (executorService != null) {
            executorService.shutdownNow();
        }
        super.onDestroy();
    }

    private static class PingHandler extends Handler {
        private WeakReference<PingActivity> weakReference;

        public PingHandler(PingActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 10:
                    // 3、将结果显示出来
                    String resultMsg = (String) msg.obj;
                    weakReference.get().pingAdapter.addString(resultMsg);
                    weakReference.get().mRecylerView.scrollToPosition(weakReference.get().pingAdapter.getItemCount() - 1);
                    break;
                default:
                    break;
            }
        }
    }

    // 创建ping任务
    private class PingTask implements Runnable {
        private String ping;
        private PingHandler pingHandler;
        private long delay;

        public PingTask(String ping, PingHandler pingHandler, long delay) {
            this.ping = ping;
            this.pingHandler = pingHandler;
            this.delay = delay;
        }

        @Override
        public void run() {
            Process process = null;
            BufferedReader successReader = null;
            BufferedReader errorReader = null;
            try {
                // 执行ping命令
                process = Runtime.getRuntime().exec(ping);
                // success
                successReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                // error
                errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String lineStr;

                while ((lineStr = successReader.readLine()) != null) {

                    // receive
                    Message msg = pingHandler.obtainMessage();
                    msg.obj = lineStr + "\r\n";
                    msg.what = 10;
                    msg.sendToTarget();
                }
                while ((lineStr = errorReader.readLine()) != null) {

                    // receive
                    Message msg = pingHandler.obtainMessage();
                    msg.obj = lineStr + "\r\n";
                    msg.what = 10;
                    msg.sendToTarget();
                }
                Thread.sleep(delay * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {

                    if (successReader != null) {
                        successReader.close();
                    }
                    if (errorReader != null) {
                        errorReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (process != null) {
                    process.destroy();
                }
            }
        }
    }

}
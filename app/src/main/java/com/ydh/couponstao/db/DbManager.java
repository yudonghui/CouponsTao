package com.ydh.couponstao.db;

import android.annotation.SuppressLint;

import com.ydh.couponstao.MyApplication;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ydh on 2022/9/21
 */
public class DbManager {
    private static DbManager manager;

    public static DbManager getInstance() {
        if (manager == null)
            return new DbManager();
        else return manager;
    }

    @SuppressLint("CheckResult")
    public void queryByTime(final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<List<ClickEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ClickEntity>> emitter) throws Exception {
                try {
                    List<ClickEntity> clickEntities = ConnectDatabase.getInstance(MyApplication.getContext())
                            .getClickDao()
                            .queryAll();
                    emitter.onNext(clickEntities);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

                .subscribe(new Observer<List<ClickEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ClickEntity> connectEntitie) {
                        mListener.success(connectEntitie);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void queryByTime(final Long creatTime, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<List<ClickEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ClickEntity>> emitter) throws Exception {
                try {
                    List<ClickEntity> clickEntities = ConnectDatabase.getInstance(MyApplication.getContext())
                            .getClickDao()
                            .queryByTime(creatTime);
                    emitter.onNext(clickEntities);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

                .subscribe(new Observer<List<ClickEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ClickEntity> connectEntitie) {
                        mListener.success(connectEntitie);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void insert(final ClickEntity clickEntity, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    ConnectDatabase.getInstance(MyApplication.getContext())
                            .getClickDao()
                            .insert(clickEntity);
                    emitter.onNext("添加成功");
                } catch (Exception e) {
                    emitter.onError(e);
                }


            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mListener.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void insert(final List<ClickEntity> clickEntity, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    ConnectDatabase.getInstance(MyApplication.getContext())
                            .getClickDao()
                            .insert(clickEntity);
                    emitter.onNext("添加成功");
                } catch (Exception e) {
                    emitter.onError(e);
                }


            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mListener.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void update(final ClickEntity clickEntity, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    ConnectDatabase.getInstance(MyApplication.getContext())
                            .getClickDao()
                            .update(clickEntity);
                    emitter.onNext("更新成功");
                } catch (Exception e) {
                    emitter.onError(e);
                }


            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mListener.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void delete(final Long creatTime, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    ConnectDatabase.getInstance(MyApplication.getContext())
                            .getClickDao()
                            .delete(creatTime);
                    emitter.onNext("删除成功");
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mListener.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}

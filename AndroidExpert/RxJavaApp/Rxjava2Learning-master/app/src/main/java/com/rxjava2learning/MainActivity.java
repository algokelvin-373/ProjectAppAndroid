package com.rxjava2learning;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * https://www.theappsfirm.com
 */
public class MainActivity extends Activity {

    private StringBuffer mRxOperatorsText = new StringBuffer();
    private static final String TAG = "Rxjava2Learning";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create();

        // Download the lab work and practice other operators

//        map();
//        zip();
//        concat();
//        flatMap();
//        concatMap();
//        distinct();
//        filter();
//        buffer();
//        timer();
//        interval();
//        doOnNext();
//        skip();
//        take();
//        just();
//        single();
//        debounce();
//        defer();
//        last();
//        merge();
//        reduce();
//        scan();
//        window();
    }

    private void create(){

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                mRxOperatorsText.append("Observable emit 1\n");
                Log.e(TAG, "Observable emit 1\n");
                e.onNext(1);

                mRxOperatorsText.append("Observable emit 2\n");
                Log.e(TAG, "Observable emit 2\n");
                e.onNext(2);

                mRxOperatorsText.append("Observable emit 3\n");
                Log.e(TAG, "Observable emit 3\n");
                e.onNext(3);

                e.onComplete();

                mRxOperatorsText.append("Observable emit 4\n");
                Log.e(TAG, "Observable emit 4\n");
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            private int i = 1;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mRxOperatorsText.append("onSubscribe : " + d.isDisposed() + "\n");
                Log.e(TAG, "onSubscribe : " + d.isDisposed() + "\n");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                mRxOperatorsText.append("onNext : value : " + integer + "\n");
                Log.e(TAG, "onNext : value : " + integer + "\n" );
                i++;
                if(i == 2){
//                    mDisposable.dispose();
                    mRxOperatorsText.append("onNext : isDisposed : " + mDisposable.isDisposed() + "\n");
                    Log.e(TAG, "onNext : isDisposed : " + mDisposable.isDisposed() + "\n");
                }
            }

            @Override
            public void onError(Throwable e) {
                mRxOperatorsText.append("onError : value : " + e.getMessage() + "\n");
                Log.e(TAG, "onError : value : " + e.getMessage() + "\n" );
            }

            @Override
            public void onComplete() {
                mRxOperatorsText.append("onComplete" + "\n");
                Log.e(TAG,"onComplete" + "\n");
            }
        });
    }

    private void map(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                mRxOperatorsText.append("Observable emit 1\n");
                Log.e(TAG, "Observable emit 1\n");
                e.onNext(1);
                mRxOperatorsText.append("Observable emit 2\n");
                Log.e(TAG, "Observable emit 2\n");
                e.onNext(2);
                mRxOperatorsText.append("Observable emit 3\n");
                Log.e(TAG, "Observable emit 3\n");
                e.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                mRxOperatorsText.append("apply : " + integer + "\n");
                Log.e(TAG, "apply : " + integer + "\n");
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mRxOperatorsText.append("accept : " + s + "\n");
                Log.e(TAG, "accept : " + s + "\n");
            }
        });
    }

    private void zip(){
        Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                Log.e(TAG, "apply s = "+s+" integer = "+integer);
                return s + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "zip : accept : "+ s + "\n");
            }
        });
    }

    private Observable<String> getStringObservable(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if(!e.isDisposed()){
                    mRxOperatorsText.append("String emit : A\n");
                    Log.e(TAG, "String emit : A\n");
                    e.onNext("A");

                    mRxOperatorsText.append("String emit : B\n");
                    Log.e(TAG, "String emit : B\n");
                    e.onNext("B");

                    mRxOperatorsText.append("String emit : C\n");
                    Log.e(TAG, "String emit : C\n");
                    e.onNext("C");
                }
            }
        });
    }

    private Observable<Integer> getIntegerObservable(){
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                mRxOperatorsText.append("String emit : 1\n");
                Log.e(TAG, "String emit : 1\n");
                e.onNext(1);
                mRxOperatorsText.append("String emit : 2\n");
                Log.e(TAG, "String emit : 2\n");
                e.onNext(2);
                mRxOperatorsText.append("String emit : 3\n");
                Log.e(TAG, "String emit : 3\n");
                e.onNext(3);
                mRxOperatorsText.append("String emit : 4\n");
                Log.e(TAG, "String emit : 4\n");
                e.onNext(4);
                mRxOperatorsText.append("String emit : 5\n");
                Log.e(TAG, "String emit : 5\n");
                e.onNext(5);
            }
        });
    }

    private void concat(){
        Observable.concat(Observable.just(1,2,3),Observable.just(4,5,6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "concat : "+integer);
                    }
                });
    }

    private void flatMap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                mRxOperatorsText.append("Observable emit 1\n");
                Log.i(TAG, "Observable emit 1\n");
                e.onNext(1);

                mRxOperatorsText.append("Observable emit 2\n");
                Log.i(TAG, "Observable emit 2\n");
                e.onNext(2);

                mRxOperatorsText.append("Observable emit 3\n");
                Log.i(TAG, "Observable emit 3\n");
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    list.add("I am value " + integer + " this is the " + i + " times");
                }
                int delayTime = (int)(1 + Math.random() * 10);
                Log.i(TAG, "apply: integer = " + integer);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "flatMap : accept : " + s + "\n");
                    }
                });
    }

    private void concatMap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                mRxOperatorsText.append("Observable emit 1\n");
                Log.i(TAG, "Observable emit 1\n");
                e.onNext(1);

                mRxOperatorsText.append("Observable emit 2\n");
                Log.i(TAG, "Observable emit 2\n");
                e.onNext(2);

                mRxOperatorsText.append("Observable emit 3\n");
                Log.i(TAG, "Observable emit 3\n");
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    list.add("I am value " + integer + " this is the " + i + " times");
                }
                int delayTime = (int)(1 + Math.random() * 10);
                Log.i(TAG, "apply: integer = " + integer);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "concatMap : accept : " + s + "\n");
                    }
                });
    }

    private void distinct(){
        Observable.just(1,1,1,2,2,3,4,5)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "distinct : " + integer);
                    }
                });
    }

    private void filter(){
        Observable.just(1, 20,65,-5,7,19)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer >= 10;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "filter : " + integer);
            }
        });
    }

    private void buffer(){
        Observable.just(1,2,3,4,5)
                .buffer(3,2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.i(TAG, "accept: buffer size = "+integers.size());
                        Log.i(TAG, "accept: buffer value : ");
                        for(Integer i:integers){
                            Log.i(TAG, "accept: i = "+i);
                        }

                    }
                });
    }

    private void timer(){
        Log.i(TAG, "timer start : " +  new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "timer : "+ aLong + " now : " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
                    }
                });
    }

    private void interval(){
        Log.i(TAG, "interval start : " +  new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        Observable.interval(3,2,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "interval : "+ aLong + " now : " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
                    }
                });
    }

    private void doOnNext(){
        Observable.just(1,2,3,4)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "doOnNext 保存 " + integer + "成功\n");
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "doOnNext :" + integer + "\n");
            }
        });
    }

    private void skip(){
        Observable.just(1,2,3,4,5)
                .skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "skip : " + integer);
                    }
                });
    }

    private void take(){
        Flowable.fromArray(1,2,3,4,5)
                .take(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "take : " + integer);
                    }
                });
    }

    private void just(){
        Observable.just("1213543156123", "sfhhxbssfg", "发货速度换个号")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "take : " + s);
                    }
                });
    }

    private void single(){
        Single.just(new Random().nextInt())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.i(TAG, "single onSuccess: "+integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "single onError: "+e.getMessage());
                    }
                });
    }

    private void debounce(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Thread.sleep(400);
                e.onNext(2);
                Thread.sleep(505);
                e.onNext(3);
                Thread.sleep(100);
                e.onNext(4);
                Thread.sleep(605);
                e.onNext(5);
                Thread.sleep(510);
                e.onComplete();
            }
        }).debounce(500,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "debounce : " + integer);
                    }
                });
    }

    private void defer(){
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(1,2,3);
            }
        });

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "defer : onNext : " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "defer : onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "defer : onComplete");
            }
        });
    }

    private void last(){
        Observable.just(1,2,3)
                .last(4)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "last : onNext : " + integer);
                    }
                });
    }

    private void merge(){
        Observable.merge(Observable.just(1,2),Observable.just(3,4,5))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "merge : onNext : " + integer);
                    }
                });
    }

    private void reduce(){
        Observable.just(1,2,3)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "reduce : onNext : " + integer);
            }
        });
    }

    private void scan(){
        Observable.just(1,2,3)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "scan : onNext : " + integer);
            }
        });
    }

    private void window(){
        Log.i(TAG, "window");
        Observable.interval(1, TimeUnit.SECONDS)
                .take(15)
                .window(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Observable<Long>>() {
                    @Override
                    public void accept(Observable<Long> longObservable) throws Exception {
                        Log.i(TAG, "window Sub Divide Begin...");
                        longObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        Log.i(TAG, "window accept: "+aLong);
                                    }
                                });
                    }
                });
    }
}

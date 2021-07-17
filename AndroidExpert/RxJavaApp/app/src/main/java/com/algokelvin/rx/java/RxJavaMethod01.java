package com.algokelvin.rx.java;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class RxJavaMethod01 extends RxOperatorStringBuffer {

    public static void create() {
        Observable.create((ObservableOnSubscribe<Integer>) e -> {

            RxOperatorsTitle.append("OnNext(1)\n");
            RxOperatorsText.append("Observable emit 1\n");
            e.onNext(1);

            RxOperatorsTitle.append("OnNext(2)\n");
            RxOperatorsText.append("Observable emit 2\n");
            e.onNext(2);

            RxOperatorsTitle.append("OnNext(3)\n");
            RxOperatorsText.append("Observable emit 3\n");
            e.onNext(3);

            RxOperatorsTitle.append("OnComplete()\n");
            e.onComplete();

            RxOperatorsTitle.append("OnNext(4)");
            RxOperatorsText.append("Observable emit 4\n");
            e.onNext(4);

        }).subscribe(new Observer<Integer>() {
            private int i = 1;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                RxOperatorsText.append("onSubscribe : " + d.isDisposed() + "\n");
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                RxOperatorsText.append("onNext : value : " + integer + "\n");
                i++;
                if(i == 2){
//                    mDisposable.dispose();
                    RxOperatorsText.append("onNext : isDisposed : " + mDisposable.isDisposed() + "\n");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                RxOperatorsText.append("onError : value : " + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                RxOperatorsText.append("onComplete" + "\n");
            }
        });
    }
}

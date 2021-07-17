package com.algokelvin.rx.java;

public class RxOperatorStringBuffer {
    static StringBuffer RxOperatorsText = new StringBuffer();
    static StringBuffer RxOperatorsTitle = new StringBuffer();

    public static StringBuffer getRxOperatorsText() {
        return RxOperatorsText;
    }

    public static StringBuffer getRxOperatorsTitle() {
        return RxOperatorsTitle;
    }

    public static void deleteStringBufferRxJava() {
        RxOperatorsText.delete(0, RxOperatorsText.length());
        RxOperatorsTitle.delete(0, RxOperatorsTitle.length());
    }
}

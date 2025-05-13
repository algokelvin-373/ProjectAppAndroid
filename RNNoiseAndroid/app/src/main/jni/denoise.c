//
// Created by fryant on 2018/12/23.
//

#include <jni.h>
#include <string.h>
#include <stdio.h>
#include "main.h"

#define FRAME_SIZE 480

#ifndef _Included_com_example_jni_jnitest_MainActivity
#define _Included_com_example_jni_jnitest_MainActivity
#ifdef __cplusplus
extern "C"{
#endif

JNIEXPORT jint JNICALL
Java_com_fryant_denoise_MainActivity_rnnoise(JNIEnv *env, jobject instance,jstring infile,jstring outfile) {

    const char *infi=(*env)->GetStringUTFChars(env,infile, JNI_FALSE);
    const char *outf=(*env)->GetStringUTFChars(env,outfile, JNI_FALSE);
    dn(infi,outf);
    return 0;
}

JNIEXPORT jint JNICALL
Java_com_algokelvin_rnnoise_MainRNNoiseActivity_rnnoise(JNIEnv *env, jobject thiz, jstring infile,
                                                        jstring outfile) {
    const char *infi=(*env)->GetStringUTFChars(env,infile, JNI_FALSE);
    const char *outf=(*env)->GetStringUTFChars(env,outfile, JNI_FALSE);
    dn(infi,outf);
    return 0;
}

#ifdef __cplusplus
}
#endif
#endif
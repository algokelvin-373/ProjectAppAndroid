package com.algokelvin.dagger2.di;

import com.algokelvin.dagger2.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

}

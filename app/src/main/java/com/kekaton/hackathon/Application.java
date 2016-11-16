package com.kekaton.hackathon;

import android.content.Intent;
import android.util.Log;

import com.kekaton.hackathon.Activity.LoginActivity;
import com.vk.sdk.VKSdk;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }
}

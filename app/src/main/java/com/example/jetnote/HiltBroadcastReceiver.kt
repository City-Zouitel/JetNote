package com.example.jetnote

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper
import dagger.Module

abstract class HiltBroadcastReceiver : BroadcastReceiver() {

    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {}

}
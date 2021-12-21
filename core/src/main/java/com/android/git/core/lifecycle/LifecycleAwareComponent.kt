package com.android.git.core.lifecycle

interface LifecycleAwareComponent : LifecycleTearDownAwareComponent {
    fun onCreate()
    fun onStart()
    fun onResume()
}

interface LifecycleTearDownAwareComponent {
    fun onPause()
    fun onStop()
    fun onDestroy()
}
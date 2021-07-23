package com.ccpp.onetwothreedemo.util

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    override fun getValue(): T {
        throw IllegalStateException("not allowed in LiveEvent, use observe instead")
    }

    @MainThread
    override fun setValue(t: T) {
        pending.set(true)

        super.setValue(t)
    }

    @Suppress("RedundantOverride")
    override fun postValue(value: T) {
        super.postValue(value)
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasObservers()) {
            throw IllegalStateException("Already has registered observers")
        }
        super.observe(owner, Observer { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    inline fun observe(owner: LifecycleOwner, crossinline block: (T) -> Unit) {
        observe(owner, Observer { block(it!!) })
    }

    @MainThread
    override fun observeForever(observer: Observer<in T>) {
        if (hasObservers()) {
            throw IllegalStateException("Already has registered observers")
        }
        super.observeForever { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    inline fun observeForever(crossinline block: (T) -> Unit) {
        observeForever(Observer { block(it!!) })
    }

    fun reset() {
        pending.set(false)
    }
}

fun <T> liveEvent() = SingleLiveEvent<T>()
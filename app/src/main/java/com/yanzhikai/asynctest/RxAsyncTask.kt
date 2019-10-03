package com.yanzhikai.asynctest

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

abstract class RxAsyncTask<Params, Progress, Result> {
    companion object {
        const val TAG = "RxAsyncTask"
    }

    open fun onProgressUpdate(vararg values: Progress?) {

    }

    open fun onPostExecute(result: Result?) {

    }

    open fun onPreExecute() {

    }

    abstract fun doInBackground(params: Array<out Params>): Result?

    fun execute(vararg params: Params) {
        val dis = Observable.just(params)
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                onPreExecute()
            }
            .observeOn(Schedulers.newThread())
            .flatMap {
                Observable.just(doInBackground(it))
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                onPostExecute(it)
            }
    }

}
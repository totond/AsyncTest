package com.yanzhikai.asynctest

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.yanzhikai.asynctest", appContext.packageName)
    }

    companion object {
        const val UNKONW = 0
        const val IS_MAIN_THREAD = 99
        const val IS_BACKGROUND_THREAD = 66

        fun getIsMainThread(): Int {
            return if (Thread.currentThread() == Looper.getMainLooper().thread) {
                IS_MAIN_THREAD
            } else {
                IS_BACKGROUND_THREAD
            }
        }

        fun assertIsMainThread() = assertEquals(getIsMainThread(), IS_MAIN_THREAD)

        fun assertNotMainThread() = assertEquals(getIsMainThread(), IS_BACKGROUND_THREAD)
    }

    @Test
    fun startAsyncTaskTest() {
        val asyncTask: AsyncTask<Int, Int, Int> = object : AsyncTask<Int, Int, Int>() {
            override fun doInBackground(vararg p0: Int?): Int {
                assertNotMainThread()
                return getIsMainThread()
            }

            override fun onProgressUpdate(vararg values: Int?) {
                assertNotMainThread()
            }

            override fun onPostExecute(result: Int?) {
                assertIsMainThread()
            }

            override fun onPreExecute() {
                assertIsMainThread()
            }
        }

        val handler = Handler(Looper.getMainLooper())
        handler.post{
            asyncTask.execute(0)
        }

    }

    @Test
    fun startRxAsyncTaskTest() {
        val asyncTask = object :RxAsyncTask<Int, Int, Int?>() {
            override fun doInBackground(params: Array<out Int>): Int? {
                assertNotMainThread()
                return getIsMainThread()
            }

            override fun onProgressUpdate(vararg values: Int?) {
                assertIsMainThread()
            }

            override fun onPostExecute(result: Int?) {
                assertIsMainThread()
            }

            override fun onPreExecute() {
                assertIsMainThread()
            }
        }

        val handler = Handler(Looper.getMainLooper())
        handler.post{
            asyncTask.execute(0)
        }
    }
}

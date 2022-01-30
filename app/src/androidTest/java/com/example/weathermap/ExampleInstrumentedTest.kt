package com.example.weathermap

import android.app.Application
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weathermap.viewmodel.MainViewModel
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.robolectric.*
import org.robolectric.annotation.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    lateinit var instrumentationContext: Context

    @Before
    fun setup() {

    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.weathermap", appContext.packageName)
    }
    @Test
    fun test_viewModel(){
        val viewModel = MainViewModel()
    }

    class FileManagerTest : AndroidTest() {

        @Test
        fun test_application(){

        }

    }

}

@RunWith(RobolectricTestRunner::class)
@Config(application = AndroidTest.ApplicationStub::class,
    sdk = intArrayOf(21))
abstract class AndroidTest {

    fun context(): Context {
        return RuntimeEnvironment.application
    }

    fun application(): Application{
        return RuntimeEnvironment.getApplication()
    }

    internal class ApplicationStub : Application()
}
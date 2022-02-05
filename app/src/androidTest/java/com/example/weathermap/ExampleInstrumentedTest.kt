package com.example.weathermap

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.weathermap.fragments.StartInfoFragment
import com.example.weathermap.fragments.TodayTempFragment
import com.example.weathermap.fragments.WeekTempFragment
import com.example.weathermap.fragments.adapters.AdapterTodayHour
import junit.framework.TestCase
import kotlinx.coroutines.DelicateCoroutinesApi
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

@DelicateCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest: TestCase() {

    companion object{
        const val TEST_CITY_NAME: String = "Омск"
    }

    private lateinit var scenarioStartFragment: FragmentScenario<StartInfoFragment>
    private lateinit var scenarioDayFragment: FragmentScenario<TodayTempFragment>
    private lateinit var scenarioWeekFragment: FragmentScenario<WeekTempFragment>

    @Nested
    @DisplayName("Fragments")
    inner class TestFragments{

        @Test
        @Disabled
        @DisplayName("Fragment in isolation")
        fun fragmentInIsolation(){
            scenarioStartFragment = launchFragmentInContainer(themeResId = R.style.Theme_WeatherMap)
            scenarioStartFragment.moveToState(Lifecycle.State.STARTED)
        }

        @Test
        @DisplayName("Weather by name of city (day)")
        fun weatherByNameOfCityDay() {
            scenarioDayFragment = launchFragmentInContainer(themeResId = R.style.Theme_WeatherMap)
            scenarioDayFragment.moveToState(Lifecycle.State.STARTED)

            onView(withId(R.id.inputCity)).perform(replaceText(TEST_CITY_NAME))
            onView(withId(R.id.btFindCity)).perform(click())

            Thread.sleep(6_000)

            onView(withId(R.id.rc_viewToday))
                .perform(scrollTo<AdapterTodayHour.ItemViewHolder>(
                    hasDescendant(withSubstring("23:00"))))
            onView(withSubstring("23:00")).check(matches(isDisplayed()))
        }

        @Test
        @DisplayName("Weather by name of city (week)")
        fun weatherByNameOfCityWeek() {
            scenarioWeekFragment = launchFragmentInContainer(themeResId = R.style.Theme_WeatherMap)
            scenarioWeekFragment.moveToState(Lifecycle.State.STARTED)

            onView(withId(R.id.inputCity)).perform(replaceText(TEST_CITY_NAME))
            onView(withId(R.id.btFindCity)).perform(click())

            Thread.sleep(6_000)

            onView(withId(R.id.rcViewWeek))
                .check(matches(hasDescendant(
                    withSubstring("Morning")
                )))
            onView(withId(R.id.rcViewWeek))
                .check(matches(hasDescendant(
                    withSubstring("Evening")
                )))
            onView(withId(R.id.rcViewWeek))
                .check(matches(hasDescendant(
                    withSubstring("Night")
                )))

            onView(withId(R.id.rcViewWeek))
                .check(matches(hasDescendant(
                    withSubstring("Today")
                )))

        }
    }

    @Nested
    @DisplayName("Global (Activity)")
    inner class TestGlobal {

        @Test
        @DisplayName("Correct startFragmentView")
        fun displayedStartFragmentView(){
            val scenario = ActivityScenario.launch(MainActivity::class.java)
            scenario.moveToState(Lifecycle.State.RESUMED)

            onView(withId(R.id.fragment_start_info))
                .check(matches(isDisplayed()))
        }

        @Test
        @DisplayName("Change fragments in nav. graph on activity")
        fun changeFragmentsInNavigationGraph(){
            val scenario = ActivityScenario.launch(MainActivity::class.java)
            scenario.moveToState(Lifecycle.State.RESUMED)

            onView(withId(R.id.btStartApp))
                .perform(click())
            onView(withId(R.id.fragment_today_temp))
                .check(matches(isDisplayed()))

            onView(withId(R.id.btChangeOnWeek))
                .perform(click())
            onView(withId(R.id.fragment_week_temp))
                .check(matches(isDisplayed()))

            onView(withId(R.id.btChangeOnDay))
                .perform(click())
            onView(withId(R.id.fragment_today_temp))
                .check(matches(isDisplayed()))
        }
    }

}
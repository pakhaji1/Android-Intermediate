package com.example.appstorydicoding.ui.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.appstorydicoding.*
import com.example.appstorydicoding.response.StoryResponse
import com.example.appstorydicoding.story.LocationViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocationViewModelTest{

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when Get Location Should Not Null and Return Result Success`() = runTest  {
        val dummyStory = DataDummy.generateDummyLocationStory()
        val locationViewModel = LocationViewModel(storyRepository)

        val expectedLocation = MutableLiveData<Result<StoryResponse>>()
        expectedLocation.value = Result.Success(dummyStory)
        Mockito.`when`(locationViewModel.getStoryWithLocation(LOCATION, TOKEN)).thenReturn(expectedLocation)

        val actualLocation = locationViewModel.getStoryWithLocation(LOCATION, TOKEN).getOrAwaitValue()

        Mockito.verify(storyRepository).getStoryWithLocation(LOCATION, TOKEN)
        Assert.assertNotNull(actualLocation)
        Assert.assertTrue(actualLocation is Result.Success)
    }

    companion object {
        private const val TOKEN = "Bearer TOKEN"
        private const val LOCATION = 1
    }
}
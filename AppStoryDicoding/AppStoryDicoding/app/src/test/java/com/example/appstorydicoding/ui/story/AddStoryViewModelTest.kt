package com.example.appstorydicoding.ui.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.appstorydicoding.*
import com.example.appstorydicoding.response.AddStory
import com.example.appstorydicoding.story.AddStoryViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest{

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when AddStory Should Not Null and Return Result Success`() = runTest  {
        val dummyStory = DataDummy.generateDummyAddStory()
        val file = mock(File::class.java)
        val description = DESC.toRequestBody("text/plain".toMediaType())
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            "nameFile",
            requestImageFile
        )

        val addStoryViewModel = AddStoryViewModel(storyRepository)

        val expectedStory = MutableLiveData<Result<AddStory>>()
        expectedStory.value = Result.Success(dummyStory)
        Mockito.`when`(addStoryViewModel.uploadImage(TOKEN,imageMultipart,description,LAT,LON)).thenReturn(expectedStory)

        val actualAddStory = addStoryViewModel.uploadImage(TOKEN,imageMultipart,description,LAT,LON).getOrAwaitValue()

        Mockito.verify(storyRepository).uploadImage(TOKEN,imageMultipart,description,LAT,LON)
        Assert.assertNotNull(actualAddStory)
        Assert.assertTrue(actualAddStory is Result.Success)
    }

    companion object {
        private const val TOKEN = "Bearer TOKEN"
        private const val DESC = "test deskripsi"
        private const val LAT = 1.0
        private const val LON = 1.0
    }
}
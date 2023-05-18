package com.example.appstorydicoding.ui.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.appstorydicoding.*
import com.example.appstorydicoding.response.SignUp
import com.example.appstorydicoding.signup.SignUpViewModel
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
class SignUpViewModelTest{
    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when SignUp Should Not Null and Return Result Success`() = runTest  {
        val dummyStory = DataDummy.generateDummySignUp()
        val signupViewModel = SignUpViewModel(storyRepository)

        val expectedSignUp = MutableLiveData<Result<SignUp>>()
        expectedSignUp.value = Result.Success(dummyStory)
        Mockito.`when`(signupViewModel.postSignUp(NAME, EMAIL, PASSWORD)).thenReturn(expectedSignUp)

        val actualUserSignUp = signupViewModel.postSignUp(NAME, EMAIL, PASSWORD).getOrAwaitValue()

        Mockito.verify(storyRepository).postSignUp(NAME, EMAIL, PASSWORD)
        Assert.assertNotNull(actualUserSignUp)
        Assert.assertTrue(actualUserSignUp is Result.Success)
    }

    companion object {
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }
}
package com.example.appstorydicoding.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.appstorydicoding.*
import com.example.appstorydicoding.login.LoginViewModel
import com.example.appstorydicoding.response.Login
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
class LoginViewModelTest{
    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when Login Should Not Null and Return Result Success`() = runTest{
        val dummyStory = DataDummy.generateDummyLogin()
        val loginViewModel = LoginViewModel(storyRepository)

        val expectedLogin = MutableLiveData<Result<Login>>()
        expectedLogin.value = Result.Success(dummyStory)
        Mockito.`when`(loginViewModel.postLogin(EMAIL, PASSWORD)).thenReturn(expectedLogin)

        val actualUserLogin = loginViewModel.postLogin(EMAIL, PASSWORD).getOrAwaitValue()

        Mockito.verify(storyRepository).postLogin(EMAIL, PASSWORD)
        Assert.assertNotNull(actualUserLogin)
        Assert.assertTrue(actualUserLogin is Result.Success)
    }

    companion object {
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }
}


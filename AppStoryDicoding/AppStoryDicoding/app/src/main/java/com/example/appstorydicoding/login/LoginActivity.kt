package com.example.appstorydicoding.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appstorydicoding.*
import com.example.appstorydicoding.databinding.ActivityLoginBinding
import com.example.appstorydicoding.edittext.EditTextEmail
import com.example.appstorydicoding.edittext.EditTextPassword
import com.example.appstorydicoding.edittext.MyButton
import com.example.appstorydicoding.local.UserPreferences
import com.example.appstorydicoding.local.UserToken
import com.example.appstorydicoding.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var userModel: UserToken = UserToken()
    private lateinit var userPreferences: UserPreferences
    private lateinit var myButton: MyButton
    private lateinit var editTextEmail : EditTextEmail
    private lateinit var editTextPassword: EditTextPassword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val loginViewModel: LoginViewModel by viewModels {
            factory
        }

        userPreferences = UserPreferences(this)

        binding.btnSignup.setOnClickListener {
            intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        myButton = binding.btnAction
        editTextEmail = binding.edLoginEmail
        editTextPassword = binding.edLoginPassword

        fun EditText.onTextChanged(action: (CharSequence) -> Unit) {
            this.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){
                    action(s)
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }

        editTextPassword.onTextChanged{
            setMyButtonEnable()
        }

        myButton.setOnClickListener {
            val email = editTextEmail.text.toString()
            val pass = editTextPassword.text.toString()

            loginViewModel.postLogin(
                email, pass
            ).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "Login ${result.data.message}", Toast.LENGTH_SHORT).show()
                            val response = result.data
                            saveToken(response.loginResult.token)
                            val intent =
                                Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra(EXTRA_KEY, response.loginResult.token)
                            startActivity(intent)
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Log.e(TAG,"onFailure Call: ${result.error}")
                            Toast.makeText(this, "Login ${getString(R.string.invalid_emailandpass)}" , Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        playAnimation()

        supportActionBar?.hide()
    }

    private fun setMyButtonEnable() {
        val result = editTextPassword.error
        myButton.isEnabled = result.isNullOrEmpty()
    }

    private fun playAnimation() {
        val image = ObjectAnimator.ofFloat(binding.storyAppLogo, View.ALPHA, 1f).setDuration(Const.duration)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(Const.duration)
        val edEmail = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(Const.duration)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(Const.duration)
        val edPassword = ObjectAnimator.ofFloat(binding.passTIL, View.ALPHA, 1f).setDuration(Const.duration)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnAction, View.ALPHA, 1f).setDuration(Const.duration)
        val linear = ObjectAnimator.ofFloat(binding.containerAccount, View.ALPHA, 1f).setDuration(Const.duration)

        AnimatorSet().apply {
            playSequentially(
                image,
                AnimatorSet().apply { playTogether(tvEmail, edEmail) },
                AnimatorSet().apply { playTogether(tvPassword, edPassword) },
                AnimatorSet().apply { playTogether(btnLogin)},
                AnimatorSet().apply { playTogether(linear) }
            )
            start()
        }
    }

    private fun saveToken(token: String) {
        userModel.token = token
        userPreferences.setUser(userModel)
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    companion object {
        const val EXTRA_KEY = "EXTRA_KEY"
    }
}
package com.example.appstorydicoding.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appstorydicoding.Const
import com.example.appstorydicoding.R
import com.example.appstorydicoding.Result
import com.example.appstorydicoding.ViewModelFactory
import com.example.appstorydicoding.databinding.ActivitySignUpBinding
import com.example.appstorydicoding.edittext.EditTextEmail
import com.example.appstorydicoding.edittext.EditTextPassword
import com.example.appstorydicoding.edittext.MyButton
import com.example.appstorydicoding.login.LoginActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var myButton: MyButton
    private lateinit var editTextEmail : EditTextEmail
    private lateinit var editTextPassword: EditTextPassword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val signUpViewModel: SignUpViewModel by viewModels {
            factory
        }

        binding.btnLogin.setOnClickListener {
            intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        myButton = binding.btnAction
        editTextEmail = binding.edRegisterEmail
        editTextPassword = binding.edRegisterPassword

        setMyButtonEnable()
        editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        myButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = editTextEmail.text.toString()
            val pass = editTextPassword.text.toString()
            signUpViewModel.postSignUp(
                name, email, pass
            ).observe(this) { result ->
                when(result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "SignUp ${result.data.message}", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Log.e(ContentValues.TAG,"onFailure Call: ${result.error}")
                        Toast.makeText(this, "SignUp ${getString(R.string.invalid_emailandpass)}" , Toast.LENGTH_SHORT).show()
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
        val greetings = ObjectAnimator.ofFloat(binding.tvGreeting, View.ALPHA, 1f).setDuration(Const.duration)
        val greetingsAccount = ObjectAnimator.ofFloat(binding.tvGreetingAccount, View.ALPHA, 1f).setDuration(Const.duration)
        val tvName = ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(Const.duration)
        val edName = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(Const.duration)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(Const.duration)
        val edEmail = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(Const.duration)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(Const.duration)
        val edPassword = ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(Const.duration)
        val btnSignUp = ObjectAnimator.ofFloat(binding.btnAction, View.ALPHA, 1f).setDuration(Const.duration)
        val linear = ObjectAnimator.ofFloat(binding.containerAccount, View.ALPHA, 1f).setDuration(Const.duration)

        AnimatorSet().apply {
            playSequentially(
                image,
                AnimatorSet().apply { playTogether(greetings,greetingsAccount) },
                AnimatorSet().apply { playTogether(tvName,edName) },
                AnimatorSet().apply { playTogether(tvEmail, edEmail) },
                AnimatorSet().apply { playTogether(tvPassword, edPassword) },
                AnimatorSet().apply { playTogether(btnSignUp) },
                AnimatorSet().apply { playTogether(linear) }
            )
            start()
        }
    }
}
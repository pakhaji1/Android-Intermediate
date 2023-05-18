package com.example.appstorydicoding.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appstorydicoding.Const
import com.example.appstorydicoding.R
import com.example.appstorydicoding.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        val desc = intent.getStringExtra(EXTRA_DESC)
        val date = intent.getStringExtra(EXTRA_DATE)
        val image = intent.getStringExtra(EXTRA_IMAGE)

        Glide.with(this)
            .load(image)
            .into(binding.ivDetailPhoto)

        binding.apply {
            tvDetailName.text = name
            tvDetailDescription.text = desc
            tvDate.text = date?.let { Const.getUploadStoryTime(it) }
        }

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = getString(R.string.description)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_NAME = "EXTRA_NAME"
        const val EXTRA_DESC = "EXTRA_DESC"
        const val EXTRA_DATE = "EXTRA_DATE"
        const val EXTRA_IMAGE = "EXTRA_IMAGE"
    }
}
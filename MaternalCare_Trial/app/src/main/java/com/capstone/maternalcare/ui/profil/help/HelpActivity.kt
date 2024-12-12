package com.capstone.maternalcare.ui.profil.help

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.maternalcare.R
import com.capstone.maternalcare.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnContact: ImageView = binding.btnContact
        btnContact.setOnClickListener {
            performContact()
        }

    }

    private fun performContact() {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("a180b4kx4070@bangkit.academy"))
            putExtra(Intent.EXTRA_SUBJECT, "Help Request")
            putExtra(Intent.EXTRA_TEXT, "Dear Support Team,")
        }

        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(emailIntent)
        } else {
            Toast.makeText(this, "No email client found", Toast.LENGTH_SHORT).show()
        }
    }


}
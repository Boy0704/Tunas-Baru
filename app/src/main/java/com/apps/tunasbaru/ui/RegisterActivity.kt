package com.apps.tunasbaru.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.apps.tunasbaru.R
import com.apps.tunasbaru.databinding.ActivityRegisterBinding
import com.apps.tunasbaru.db.KaryawanDao
import com.apps.tunasbaru.db.TunasDB
import com.apps.tunasbaru.model.Karyawan

class RegisterActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private lateinit var database: TunasDB
    private lateinit var daoKaryawan: KaryawanDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = TunasDB.getDatabase(applicationContext)
        daoKaryawan = database.getKaryawanDao()

        ActivityCompat.requestPermissions(
                this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ), PackageManager.PERMISSION_GRANTED
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
                return
            }
        }

        setupView()

    }

    private fun setupView() {
        binding.btnSimpan.setOnClickListener {
            val nama = binding.nama.text.toString()
            val no_induk = binding.noInduk.text.toString()
            val estate = binding.estate.text.toString()

            if (nama.isEmpty() || no_induk.isEmpty() || estate.isEmpty()) {
                Toast.makeText(this, "Silahkan lengkapi data ini", Toast.LENGTH_SHORT).show()
            } else {
                daoKaryawan.insert(Karyawan(nama = nama, no_induk = no_induk, estate = estate))
                Toast.makeText(this, "Data Kamu berhasil disimpan !", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, HeaderPendataanActivity::class.java))
                finish()
            }
        }
    }



}
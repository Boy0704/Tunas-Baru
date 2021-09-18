package com.apps.tunasbaru.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.apps.tunasbaru.MainActivity
import com.apps.tunasbaru.R
import com.apps.tunasbaru.db.KaryawanDao
import com.apps.tunasbaru.db.TunasDB
import com.apps.tunasbaru.model.DetailPendataan
import com.apps.tunasbaru.model.HeaderPendataan
import com.apps.tunasbaru.model.Karyawan
import java.text.SimpleDateFormat
import java.util.*

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var database: TunasDB
    private lateinit var daoKaryawan: KaryawanDao
    private val TAG = HeaderPendataan::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        val SplashTime: Long = 3000

        database = TunasDB.getDatabase(applicationContext)
        daoKaryawan = database.getKaryawanDao()

//        val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
//        val currentDate = sdf.format(Date())
//
//        Log.e(TAG, "waktu now: ${currentDate.toString()}")
//
//        if (currentDate.toString().equals("17/9/2021 21:44:30"))

        Handler().postDelayed({

            if(daoKaryawan.getById(1).isNotEmpty()){
                val intent = Intent(this, HeaderPendataanActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, SplashTime)

    }
}
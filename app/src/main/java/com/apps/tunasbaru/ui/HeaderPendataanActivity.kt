package com.apps.tunasbaru.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.apps.tunasbaru.R
import com.apps.tunasbaru.databinding.ActivityHeaderPendataanBinding
import com.apps.tunasbaru.databinding.ActivityRegisterBinding
import com.apps.tunasbaru.db.HPendataanDao
import com.apps.tunasbaru.db.KaryawanDao
import com.apps.tunasbaru.db.TunasDB
import com.apps.tunasbaru.model.HeaderPendataan
import com.apps.tunasbaru.model.Karyawan

class HeaderPendataanActivity : AppCompatActivity() {

    private val binding by lazy { ActivityHeaderPendataanBinding.inflate(layoutInflater) }
    private lateinit var database: TunasDB
    private lateinit var hPendataanDao: HPendataanDao
    private lateinit var karyawanDao: KaryawanDao
    private val TAG = HeaderPendataan::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = TunasDB.getDatabase(applicationContext)
        hPendataanDao = database.getHPendataanDao()
        karyawanDao = database.getKaryawanDao()

        setupView()
    }

    private fun setupView() {

        val listName = arrayListOf<Karyawan>()
        listName.addAll(karyawanDao.getById(1))
        binding.welcome.setText("Selamat Datang, \n${listName[0].nama} ")

        binding.btnSimpan.setOnClickListener {
            val divisi = binding.divisi.text.toString()
            val block = binding.block.text.toString()
            val luas = binding.luas.text.toString()
            val tahun_tanam = binding.tahunTanam.text.toString()
            val pelaksanaan_sensus = binding.pelaksanaanSensus.text.toString()

            if ( divisi.isEmpty() || block.isEmpty() || luas.isEmpty() || tahun_tanam.isEmpty() || pelaksanaan_sensus.isEmpty()) {
                Toast.makeText(this, "Silahkan lengkapi data ini", Toast.LENGTH_SHORT).show()
            } else {
                hPendataanDao.insert(HeaderPendataan(divisi = divisi, block = block, luas = luas, tahun_tanam = tahun_tanam, pelaksanaan_sensus = pelaksanaan_sensus))
                Toast.makeText(this, "Data berhasil disimpan !", Toast.LENGTH_SHORT).show()

                val id_header = hPendataanDao.getId()[0].id

                val bundle = Bundle()
                bundle.putString("id_header", id_header.toString())

                val intent = Intent(this, PendataanActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
                finish()
            }
        }
    }
}
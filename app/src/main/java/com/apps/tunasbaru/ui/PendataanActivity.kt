package com.apps.tunasbaru.ui



import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.apps.tunasbaru.databinding.ActivityPendataanBinding
import com.apps.tunasbaru.db.DPendataanDao
import com.apps.tunasbaru.db.TunasDB
import com.apps.tunasbaru.model.DetailPendataan
import com.apps.tunasbaru.model.HeaderPendataan
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.IndexedColorMap
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class PendataanActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPendataanBinding.inflate(layoutInflater) }
    private lateinit var database: TunasDB
    private lateinit var dPendataanDao: DPendataanDao
    private val filePath = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/Demo.xls")
    private val TAG = HeaderPendataan::class.java.simpleName

    private lateinit var viewModelFactory: PendataanModelViewFactory
    private lateinit var viewModel : PendataanViewModel

    private lateinit var progressDialog: ProgressDialog
    private var disableback: String? = null

    private lateinit var idHeader: String
    private var numberKiri: Int = 1
    private var numberAtas: Int = 1
    private var batasX = "Baris 1";
    private var batasY = "Kolom 1";
    private var nilaiValue: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        disableback = "false";

        val bundle = intent.extras
        if (bundle != null) {
            idHeader = bundle.getString("id_header").toString()
        }else{
            Toast.makeText(this, "Ada Kesalahan", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HeaderPendataan::class.java))
            finish()
        }
//        idHeader = "1"

        Log.e(TAG, "id Header : $idHeader")


        database = TunasDB.getDatabase(applicationContext)
        dPendataanDao = database.getDPendataanDao()

        setupViewModel()

        setupView()

    }

    private fun setupViewModel() {
        viewModelFactory = PendataanModelViewFactory( idHeader, dPendataanDao )
        viewModel = ViewModelProvider(this, viewModelFactory).get(PendataanViewModel::class.java)
    }

    fun progressshow() {
        progressDialog = ProgressDialog(this@PendataanActivity)
        progressDialog.setMessage("Silahkan tunggu..")
        progressDialog.show()
        progressDialog.setCancelable(false);
        disableback = "true"
    }

    fun progresshide() {
        progressDialog.dismiss()
        disableback = "false"
    }

    override fun onBackPressed() {
        if (disableback != "true") {
            finish()
        }
    }


    // membuat file excel dengan .xls
    fun buttonCreateExcel() {
        val hssfWorkbook = HSSFWorkbook()
        val hssfSheet = hssfWorkbook.createSheet("Custom Sheet")
        val hssfRow = hssfSheet.createRow(0)
        val hssfCell = hssfRow.createCell(0)
        hssfCell.setCellValue("Hallo Boy Kurniawan")
        try {
            if (!filePath.exists()) {
                filePath.createNewFile()
            }
            val fileOutputStream = FileOutputStream(filePath)
            hssfWorkbook.write(fileOutputStream)
            if (fileOutputStream != null) {
                fileOutputStream.flush()
                fileOutputStream.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupView() {



        binding.barisKiri.setText(batasX)
        binding.kolomAtas.setText(batasY)

        if (numberAtas.equals(201)){
            binding.nilai.isEnabled = false
            binding.btnSimpan.isEnabled = false
            Toast.makeText(this, "Inputan hanya di batasi sampai kolom 200", Toast.LENGTH_SHORT).show()
        }

        binding.btnSimpan.setOnClickListener {

            val nilai = binding.nilai.text.toString()

            if(nilai.isNotEmpty()){
                nilaiValue = nilai.toInt()
            }

            dPendataanDao.insert(DetailPendataan(urutX = numberKiri, urutY = numberAtas, nilai = nilaiValue, idHeader = idHeader.toInt()))
            Toast.makeText(this, "Berhasil simpan !", Toast.LENGTH_SHORT).show()

            numberKiri = numberKiri + 1
            if (numberKiri.equals(201)) {
                numberKiri = 1
                numberAtas = numberAtas + 1
            }

            batasX = "Baris $numberKiri"
            batasY = "Kolom $numberAtas"
            binding.barisKiri.setText(batasX)
            binding.kolomAtas.setText(batasY)

            binding.nilai.setText("")
        }

        binding.btnSelesai.setOnClickListener {


            Log.e(TAG, "CEK LOKASI : $filePath")
//            buttonCreateExcel()
            viewModel.countToOneBillion()
            progressshow()
        }

        viewModel.counter.observe(this, Observer {

            if (it.equals("1")) {
                progresshide()
                Toast.makeText(this, "File Excel Berhasil di generate, Silahkan Buka di Folder DOWNLOAD", Toast.LENGTH_SHORT).show()
            }
        })



    }






}
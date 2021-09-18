package com.apps.tunasbaru.ui

import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.tunasbaru.db.DPendataanDao
import com.apps.tunasbaru.db.TunasDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.IndexedColorMap
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class PendataanViewModel (
    val idHeader: String,
    val dPendataanDao: DPendataanDao
        ): ViewModel() {

    private val _counter = MutableLiveData<String>("0")

    val counter: LiveData<String>
        get() = _counter


    fun countToOneBillion() = viewModelScope.launch {
        countToOneBillionInternal()
    }

    private suspend fun countToOneBillionInternal() {

        withContext(Dispatchers.Default) { // Dispatchers.Default (main-safety block)
            createExcel(createWorkbook())
        }

        var num = 1
        _counter.value = num.toString()
    }



    fun createWorkbook(): Workbook {
        // Creating excel workbook
        val workbook = XSSFWorkbook()

        //Creating first sheet inside workbook
        //Constants.SHEET_NAME is a string value of sheet name
        val sheet: Sheet = workbook.createSheet("Tanaman Sensus")

        //Create Header Cell Style
        val cellStyle = getHeaderStyle(workbook)

        //Creating sheet header row
        createSheetHeader(cellStyle, sheet)
//        progressshow()
        //Adding data to the sheet
        for (i in 1..200) {
            addData(i, sheet)
        }


        return workbook
    }

     fun createSheetHeader(cellStyle: CellStyle, sheet: Sheet) {
        //setHeaderStyle is a custom function written below to add header style

        //Create sheet first row
        val row = sheet.createRow(0)

        //Header list
        var HEADER_LIST = listOf("No", "1", "2")
        for (i in 3..200){
            HEADER_LIST = HEADER_LIST + "$i"
        }

        //Loop to populate each column of header row
        for ((index, value) in HEADER_LIST.withIndex()) {

            val columnWidth = (15 * 500)

            //index represents the column number
            sheet.setColumnWidth(index, columnWidth)

            //Create cell
            val cell = row.createCell(index)

            //value represents the header value from HEADER_LIST
            cell?.setCellValue(value)

            //Apply style to cell
            cell.cellStyle = cellStyle
        }
    }

     fun getHeaderStyle(workbook: Workbook): CellStyle {

        //Cell style for header row
        val cellStyle: CellStyle = workbook.createCellStyle()

        //Apply cell color
        val colorMap: IndexedColorMap = (workbook as XSSFWorkbook).stylesSource.indexedColors
        var color = XSSFColor(IndexedColors.RED, colorMap).indexed
        cellStyle.fillForegroundColor = color
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)

        //Apply font style on cell text
        val whiteFont = workbook.createFont()
        color = XSSFColor(IndexedColors.WHITE, colorMap).indexed
        whiteFont.color = color
        whiteFont.bold = true
        cellStyle.setFont(whiteFont)

        return cellStyle
    }

     fun addData(rowIndex: Int, sheet: Sheet) {

        //Create row based on row index
        val row = sheet.createRow(rowIndex)


        //Add data to each cell
         createCell(row, 0, "$rowIndex")

        for (i in 1..200) {
            var nilaiIsi = 0
            var kolom = i;
            var nilai = dPendataanDao.getXY(idHeader = idHeader.toInt(), urutX = rowIndex, urutY = kolom)
            if (nilai.isNotEmpty()){
                nilaiIsi = nilai[0].nilai
            }
            createCell(row, kolom, "$nilaiIsi") //Column 1
//            createCell(row, kolom, "$rowIndex | $kolom") //Column 1
        }

//        createCell(row, 1, "value 2") //Column 2
//        createCell(row, 2, "value 3") //Column 3
    }

     fun createCell(row: Row, columnIndex: Int, value: String?) {
        val cell = row.createCell(columnIndex)
        cell?.setCellValue(value)
    }

     fun createExcel(workbook: Workbook) {

        //Get App Director, APP_DIRECTORY_NAME is a string
//        val appDirectory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

        //Check App Directory whether it exists or not, create if not.
//        if (appDirectory != null && !appDirectory.exists()) {
//            appDirectory.mkdirs()
//        }

        //Create excel file with extension .xlsx
//        val excelFile = File(appDirectory, "datatunasbaru.xlsx")

        val excelFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/datatunasbaru.xlsx")

//        Log.e(TAG, "LOKASI KEDUA : $excelFile")


        //Write workbook to file using FileOutputStream
        try {
            val fileOut = FileOutputStream(excelFile)
            workbook.write(fileOut)
            fileOut.close()
//            Toast.makeText(this, "File Excel Berhasil di generate, Silahkan Buka di $excelFile", Toast.LENGTH_SHORT).show()
//            progresshide()
//            val intent = Intent(Intent.ACTION_GET_CONTENT)
//            val uri: Uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()) // a directory
//            intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
//            startActivity(Intent.createChooser(intent, "Open folder"))

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
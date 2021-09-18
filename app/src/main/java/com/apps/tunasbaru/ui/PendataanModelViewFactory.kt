package com.apps.tunasbaru.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apps.tunasbaru.db.DPendataanDao

class PendataanModelViewFactory (
    private val id_header: String,
    private val dPendataanDao: DPendataanDao
        ) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PendataanViewModel(id_header, dPendataanDao) as T
    }
}


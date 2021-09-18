package com.apps.tunasbaru.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apps.tunasbaru.db.DPendataanDao
import com.apps.tunasbaru.db.TunasDB

class PendataanModelViewFactory (
    private val id_header: String,
    private val database: TunasDB
        ) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PendataanViewModel(id_header, database) as T
    }
}


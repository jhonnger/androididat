package com.idat.clinicasalud

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater

object ProgressDialogUtil {
    private var progressDialog: Dialog? = null

    fun showProgressDialog(context: Context) {
        if (progressDialog == null) {
            progressDialog = Dialog(context)
            val view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
            progressDialog?.setContentView(view)
            progressDialog?.setCancelable(false)
            progressDialog?.show()
        }
    }

    fun hideProgressDialog() {
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
            progressDialog = null
        }
    }
}
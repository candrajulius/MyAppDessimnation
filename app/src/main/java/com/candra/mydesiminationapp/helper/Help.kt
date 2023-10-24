package com.candra.mydesiminationapp.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.content.AbstractThreadedSyncAdapter
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.provider.Settings
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.customview.widget.Openable
import androidx.recyclerview.widget.RecyclerView
import com.candra.mydesiminationapp.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import java.io.File
import java.io.FileOutputStream

object Help {

    fun showToast(context: Context, message: String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    fun showDialogPermissionDenied(context: Context,message: String){
        AlertDialog.Builder(context).
        setMessage("Aplikasi ini membutuhkan fitur perizinan $message anda.." +
                    "Jika fitur ini tidak anda aktifkan. Anda tidak bisa menggunakan fitur ini" +
                    "Silahkan pergi ke Setting anda")
            .setTitle(context.getString(R.string.warning))
            .setIcon(R.mipmap.ic_launcher_round)
            .setPositiveButton(context.getString(R.string.go_to_setting)) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    context.startActivity(intent)
                }catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                    Log.d("TAG", "showDialogPermissionDenied: ${e.message.toString()}")
                }
            }
            .setNegativeButton(context.getString(R.string.cancel)){dialog,_ ->
                dialog.dismiss()
            }.show()
    }

    fun setClearText(tilInputEmail: TextInputLayout,tilInputPassword: TextInputLayout){
        tilInputEmail.editText?.text?.clear()
        tilInputPassword.editText?.text?.clear()
    }
    fun showDialog(context: Context,message: String){
        AlertDialog.Builder(context)
            .setMessage(message)
            .setTitle(context.getString(R.string.warning))
            .setIcon(R.drawable.baseline_warning_24)
            .setPositiveButton(context.getString(R.string.ok)){dialog,_ ->
                dialog.dismiss()
            }.show()
    }

    fun setToolbar(position: Int,actionBar: ActionBar?,tittleToolbar: String){
        actionBar?.apply {
            if (position == 0){
                title = "Desiminasi UNPRI"
                subtitle = tittleToolbar
                setDisplayHomeAsUpEnabled(true)
            }else{
                title = "Desiminasi UNPRI"
                subtitle = tittleToolbar
                setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    fun showProgressBar(isShow: Boolean,recyclerView: RecyclerView,progresBar: ProgressBar){
        progresBar.visibility = if (isShow) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isShow) View.GONE else View.VISIBLE
    }

    fun removeHtmlTags(htmlText: String): String {
        val spanned: Spanned =
            Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
        return spanned.toString()
    }

    fun visibleDeterminationDecision(status: String,mtvDeterminationDecision: MaterialTextView,
                                     mtvValueDeterminationDecision: MaterialTextView)
    {
        mtvDeterminationDecision.visibility = if (status == Constant.STATUS_REPORT_APPROVED_BY_THE_DEAN ) View.VISIBLE else View.GONE
        mtvValueDeterminationDecision.visibility = if (status == Constant.STATUS_REPORT_APPROVED_BY_THE_DEAN ) View.VISIBLE else View.GONE
    }

    fun showCardScheduleDissemination(status: String,cardScheduleDissemination: MaterialCardView,titleSchedule: MaterialTextView)
    {
        cardScheduleDissemination.visibility = if (status == Constant.STATUS_REGISTER_APPROVE_PROGRAM_STUDY
            || status == Constant.STATUS_REPORT_APPROVED_BY_THE_DEAN || status == Constant.STATUS_REPORT_APPROVE_PROGRAM_STUDY) View.VISIBLE else View.GONE
        titleSchedule.visibility = if (status == Constant.STATUS_REGISTER_APPROVE_PROGRAM_STUDY || status == Constant.STATUS_REPORT_APPROVED_BY_THE_DEAN
            || status == Constant.STATUS_REPORT_APPROVE_PROGRAM_STUDY) View.VISIBLE else View.GONE
    }

}
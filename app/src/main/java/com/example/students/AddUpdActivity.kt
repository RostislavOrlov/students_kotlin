package com.example.students

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

const val EXTRA_ARR_TO_MAIN = "com.example.students.arr_to_main"
const val EXTRA_ARR_FROM_MAIN = "com.example.students.arr_from_main"
class AddUpdActivity : AppCompatActivity() {
    private lateinit var etFIO: EditText
    private lateinit var etDateB: EditText
    private lateinit var etGroup: EditText
    private lateinit var etFacult: EditText
    private lateinit var btnOk: Button
    private lateinit var btnCancel: Button
    private var arrFromMain = arrayListOf<String>("")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arrFromMain = intent?.getStringArrayListExtra(EXTRA_ARR_FROM_MAIN)!!
        setContentView(R.layout.activity_add_upd)
        btnOk=findViewById(R.id.btn_Ok)
        btnCancel=findViewById(R.id.btn_Cancel)
        etFIO=findViewById(R.id.et_FIO)
        etDateB=findViewById(R.id.et_Date)
        etGroup=findViewById(R.id.et_Group)
        etFacult=findViewById(R.id.et_Fac)
        etFIO.requestFocus()

        if (arrFromMain?.get(0)=="true") {
            etFIO.setText("")
            etDateB.setText("")
            etGroup.setText("")
            etFacult.setText("")
        }
        else {
            etFIO.setText(arrFromMain?.get(1))
            etDateB.setText(arrFromMain?.get(2))
            etGroup.setText(arrFromMain?.get(3))
            etFacult.setText(arrFromMain?.get(4))
        }

        btnOk.setOnClickListener { btnOklistener() }
    }

    private fun btnOklistener() {
        if (isNotEmptyET()) {
            val arr = arrayListOf<String>(etFIO.text.toString(), etDateB.text.toString(), etGroup.text.toString(), etFacult.text.toString())
            result(arr)
        }
    }

    private fun result(arr: ArrayList<String>) {
        val studData = Intent().apply {
            putStringArrayListExtra(EXTRA_ARR_TO_MAIN, arr)
        }
        setResult(Activity.RESULT_OK, studData)
    }

    private fun isNotEmptyET():Boolean {
        var flag = true
        if (etFIO.text.isBlank()) {
            etFIO.requestFocus()
            etFIO.setError("Введите ФИО")
            flag = false
        }
        if (etDateB.text.isBlank()) {
            etDateB.requestFocus()
            etDateB.setError("Введите дату рождения")
            flag = false
        }
        if (etGroup.text.isBlank()) {
            etGroup.requestFocus()
            etGroup.setError("Введите группу")
            flag = false
        }
        if (etFacult.text.isBlank()) {
            etFacult.requestFocus()
            etFacult.setError("Введите факультет")
            flag = false
        }
        return flag
    }

    companion object {
        fun newIntent(packageContext: Context, arrFromMain: ArrayList<String>): Intent {
            return Intent(packageContext, AddUpdActivity::class.java).apply {
                putExtra(EXTRA_ARR_FROM_MAIN, arrFromMain)
            }
        }
    }
}
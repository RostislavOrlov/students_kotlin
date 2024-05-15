package com.example.students

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.students.models.MainActivityModel

class MainActivity : AppCompatActivity() {
    private lateinit var tvStudent: TextView
    private lateinit var btnPrev: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var btnAdd: Button
    private lateinit var btnUpd: Button
    private lateinit var btnDel: Button
    private var flag = true

    private val viewModel: MainActivityModel by lazy {
        val provider = ViewModelProvider(this)
        provider.get(MainActivityModel::class.java)
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val result = data?.getStringArrayListExtra(EXTRA_ARR_TO_MAIN)
            if (result != null)
                if (flag)
                    viewModel.addToList(result)
                else
                    viewModel.saveEdit(result)
            updInfo()
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvStudent=findViewById(R.id.tv_Student)
        btnPrev=findViewById(R.id.btn_Prev)
        btnNext=findViewById(R.id.btn_Next)
        btnAdd=findViewById(R.id.btn_Add)
        btnUpd=findViewById(R.id.btn_Upd)
        btnDel=findViewById(R.id.btn_Del)

        updInfo()

        btnAdd.setOnClickListener { btnAddListener() }
        btnUpd.setOnClickListener { btnUpdListener() }
        btnDel.setOnClickListener { btnDelListener() }
        btnPrev.setOnClickListener { btnPrevListener() }
        btnNext.setOnClickListener { btnNextListener() }

    }
    private fun btnNextListener() {
        if (viewModel.s>0) {
            viewModel.moveToNext()
            updInfo()
        }
    }
    private fun btnPrevListener() {
        if (viewModel.s>0) {
            viewModel.moveToPrev()
            updInfo()
        }
    }
    private fun btnAddListener(){
        flag = true
        val arr = arrayListOf<String>("true")
        val intent = AddUpdActivity.newIntent(this@MainActivity,arr)
        resultLauncher.launch(intent)
        updInfo()
    }
    private fun btnUpdListener(){
        if (viewModel.s>0) {
            flag = false
            val arr = arrayListOf<String>("false", viewModel.f, viewModel.dB, viewModel.gr, viewModel.fac)
            val intent = AddUpdActivity.newIntent(this@MainActivity, arr)
            resultLauncher.launch(intent)
            updInfo()
        }
        else {
            val mesg = "Записи для редактирования отсутствуют"
            Toast.makeText(this, mesg, Toast.LENGTH_SHORT).show()
        }
    }
    private fun btnDelListener(){
        if (viewModel.s>0) {
            viewModel.delStud()
            updInfo()
        }
        else {
            val mesg = "Записи для удаления отсутствуют"
            Toast.makeText(this, mesg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updInfo(){
        val currInfo = viewModel.currObj
        tvStudent.setText(currInfo)
    }
}
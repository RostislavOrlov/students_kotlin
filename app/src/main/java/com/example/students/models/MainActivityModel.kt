package com.example.students.models

import androidx.lifecycle.ViewModel
import com.example.students.data.Student

class MainActivityModel:ViewModel() {
    val studentList = mutableListOf<Student>(
        Student("Иванов Иван Иванович", "03.03.2003", "4ИТ", "фктипм")
    )
    private val names = mutableListOf<String>("ФИО", "Дата рождения", "Группа", "Факультет")

    var s=studentList.size
    private var currInd=0

    val f:String
        get()=studentList[currInd].fio
    val dB:String
        get()=studentList[currInd].dateB
    val gr:String
        get()=studentList[currInd].group
    val fac:String
        get()=studentList[currInd].faculty

    val currObj:String
        get()= if (s>0) "${names.get(0)}: ${f}" +
                        "\n${names.get(1)}: ${dB}" +
                        "\n${names.get(2)}: ${gr}" +
                        "\n${names.get(3)}: ${fac}"
                else "Записи отсутствуют"

    fun moveToNext() {
        currInd=(currInd+1)%s
    }
    fun moveToPrev() {
        currInd=(currInd-1)%s
    }
    fun addToList(arr: ArrayList<String>) {
        val studObj = Student(arr[0], arr[1], arr[2], arr[3])
        if (studObj !in studentList) {
            studentList.add(studObj)
            s+=1
            currInd=s-1
        }
    }
    fun saveEdit(arr: ArrayList<String>){
        studentList[currInd] = Student(arr[0], arr[1], arr[2], arr[3])
    }
    fun delStud(){
        studentList.removeAt(currInd)
        if (currInd==s-1) currInd -= 1
        s -= 1
        if (s==0) currInd = 0
    }
}
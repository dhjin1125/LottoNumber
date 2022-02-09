package com.example.a2_2secondexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private val clearButton: Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }

    private val addButton: Button by lazy {
        findViewById<Button>(R.id.addButton)
    }

    private val runButton: Button by lazy {
        findViewById<Button>(R.id.runButton)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    // java.lang.UnsupportedOperationException
    // 118번째 resultList에서 as list 또는 to list로 생성된 list의 요소를 삭제 할 수 없다.
    // isFull을 통해 exception 방지
    private var isFull = false

    private val numberTextViewList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.textView1),
            findViewById(R.id.textView2),
            findViewById(R.id.textView3),
            findViewById(R.id.textView4),
            findViewById(R.id.textView5),
            findViewById(R.id.textView6)
        )
    }

    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNumberPicker()
        initAddButton()
        initClearButton()
        initRunButton()
    }

    private fun initNumberPicker() {
        numberPicker.minValue = 1
        numberPicker.maxValue = 45
    }

    private fun initAddButton() {
        addButton.setOnClickListener {

            if (pickNumberSet.size == 6) {
                Toast.makeText(this, "초기화 후 진행해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (isFull) {
                Toast.makeText(this, "초기화 후 진행해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "중복된 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            pickNumberSet.add(numberPicker.value)
            numberTextViewList[pickNumberSet.size-1].text = numberPicker.value.toString()
            numberTextViewList[pickNumberSet.size-1].isVisible = true
            setNumberBackground(numberPicker.value, numberTextViewList[pickNumberSet.size-1])
        }
    }

    private fun initClearButton() {
        clearButton.setOnClickListener {
            pickNumberSet.clear()
            isFull = false
            numberTextViewList.forEach {
                it.isVisible = false
            }
        }
    }

    private fun initRunButton() {
        runButton.setOnClickListener {

            if(isFull && pickNumberSet.isNotEmpty()) {
                pickNumberSet.clear()
            }

            val randomList = mutableListOf<Int>()
            randomList.clear()

            for (i in 1..45) {
                if (pickNumberSet.contains(i))
                    continue

                randomList.add(i)
            }
            randomList.shuffle()

            var resultList = (pickNumberSet.toMutableList()
                    + randomList.subList(0, 6 - pickNumberSet.size)) as MutableList<Int>

            resultList = resultList.sorted() as MutableList<Int>
            isFull = true

            resultList.forEachIndexed { index, number ->
                numberTextViewList[index].text = number.toString()
                numberTextViewList[index].isVisible = true
                setNumberBackground(number,numberTextViewList[index])
            }
        }
    }

    private fun setNumberBackground(number:Int, textView: TextView) {
        when(number) {
            in 1..10 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_yello)
            in 11..20 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 21..30 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 31..40 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
            else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)

        }
    }

}
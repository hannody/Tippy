package com.abunayla.tippy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
private const val INIT_PERCENT_VALUE = 15
class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekBar_tipPercent.progress = INIT_PERCENT_VALUE
        textView_percent.text = "$INIT_PERCENT_VALUE%"

        seekBar_tipPercent.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG,"OnProgressChanged $progress")
                textView_percent.text = "$progress%"
                computeTipAndTotal()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })// seekBar_tipPercent.setOnSeekBarChangeListener

        editText_baseAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })//editText_baseAmount.addTextChangedListener

    }//onCreate

    private fun computeTipAndTotal() {
        if (editText_baseAmount.text.isEmpty()){
            textView_tipAmount.text =""
            textView_totalAmount.text = ""
            return
        }

        val baseAmount = editText_baseAmount.text.toString().toDouble()
        val tipPercent = seekBar_tipPercent.progress
        val tipAmount = (baseAmount * tipPercent)/100
        val totalAmount = (baseAmount + tipAmount)

        textView_tipAmount.text = "%.2f".format(tipAmount)
        textView_totalAmount.text = "%.2f".format(totalAmount)

    }
}

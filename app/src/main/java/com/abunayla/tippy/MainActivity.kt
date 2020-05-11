package com.abunayla.tippy

import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


private const val TAG = "MainActivity"

private const val INIT_PERCENT_VALUE = 15



class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekBar_tipPercent.progress = INIT_PERCENT_VALUE
        textView_percent.text = "$INIT_PERCENT_VALUE%"
        updateEmoji(INIT_PERCENT_VALUE)



        seekBar_tipPercent.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //Log.i(TAG,"OnProgressChanged $progress")


                textView_percent.text = "$progress%"
                computeTipAndTotal()

                updateEmoji(progress)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if(seekBar_tipPercent.progress < 2) {
                    seekBar_tipPercent.progress = 1
                }
            }
        })// seekBar_tipPercent.setOnSeekBarChangeListener

        editText_baseAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })//editText_baseAmount.addTextChangedListener

    }//onCreate

    private fun updateEmoji(tipPercent: Int) {
        val emojis = mutableListOf("\uD83D\uDE2D", "☹️", "\uD83D\uDE07","\uD83E\uDD70", "\uD83E\uDD11")
        when(tipPercent){
            in 0..3-> textView_emoji.text = emojis.get(0)
            in 4..10-> textView_emoji.text = emojis.get(1)
            in 11..20-> textView_emoji.text = emojis.get(2)
            in 21..25-> textView_emoji.text = emojis.get(3)
            else-> textView_emoji.text = emojis.get(4)
        }
    }

    private fun computeTipAndTotal() {
        if (editText_baseAmount.text.isEmpty()){
            textView_tipAmount.text =""
            textView_totalAmount.text = ""
            return
        }

        if(seekBar_tipPercent.progress > 0) {

            val baseAmount = editText_baseAmount.text.toString().toDouble()
            val tipPercent = seekBar_tipPercent.progress
            val tipAmount = (baseAmount * tipPercent) / 100
            val totalAmount = (baseAmount + tipAmount)

            textView_tipAmount.text = "%.2f".format(tipAmount)
            textView_totalAmount.text = "%.2f".format(totalAmount)
        }

    }
}

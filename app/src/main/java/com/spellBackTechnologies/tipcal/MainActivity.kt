package com.spellBackTechnologies.tipcal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.widget.addTextChangedListener

private const val defaultTipPercentage = 15

class MainActivity : AppCompatActivity()
{
    private lateinit var baseAmount: EditText
    private lateinit var tipPercentage_seek: SeekBar
    private lateinit var tipPercentage: TextView
    private lateinit var tipAmount: TextView
    private lateinit var totalAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        baseAmount = findViewById(R.id.editTextNumberDecimal)
        tipPercentage_seek = findViewById(R.id.tipPercentageSliderField)
        tipPercentage = findViewById(R.id.tipPercentageSliderLabel)
        tipAmount = findViewById(R.id.tipAmountField)
        totalAmount = findViewById(R.id.totalAmountField)

        tipPercentage_seek.progress = defaultTipPercentage
        tipPercentage.text = defaultTipPercentage.toString()

        tipPercentage_seek.setOnSeekBarChangeListener(
            object: SeekBar.OnSeekBarChangeListener
            {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean)
                {
                    tipPercentage.text = "$p1%"
                    evaluateTipAndTotal()
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}

                override fun onStopTrackingTouch(p0: SeekBar?) {}
            }
        )

        baseAmount.addTextChangedListener(
            object: TextWatcher
            {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?)
                {
                    evaluateTipAndTotal()
                }
            }
        )
    }

    private fun evaluateTipAndTotal()
    {
        if (baseAmount.text.isEmpty())
        {
            tipAmount.text = ""
            totalAmount.text = ""
            return
        }

        val baseAmt = baseAmount.text.toString().toDouble()
        val tipPrcnt = tipPercentage_seek.progress

        val tipAmt = baseAmt * tipPrcnt / 100
        val totalAmt = baseAmt + tipAmt

        tipAmount.text = "%.2f".format(tipAmt)
        totalAmount.text = "%.2f".format(totalAmt)
    }
}
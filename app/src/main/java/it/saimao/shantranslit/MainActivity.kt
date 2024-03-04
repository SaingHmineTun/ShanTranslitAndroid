package it.saimao.shantranslit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import it.saimao.shantranslit.databinding.ActivityMainBinding
import it.saimao.shantranslit.utils.ShanTranslit

class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    private lateinit var input: EditText
    private lateinit var output: EditText
    private lateinit var binding: ActivityMainBinding


    private lateinit var rg1: RadioGroup
    private lateinit var rg2: RadioGroup

    private lateinit var rb1: RadioButton
    private lateinit var rb2: RadioButton
    private lateinit var rb3: RadioButton
    private lateinit var rb4: RadioButton
    private lateinit var rb5: RadioButton
    private lateinit var rb6: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        input = binding.layoutMain.edInput
        output = binding.layoutMain.edOutput
        val btConvert = binding.layoutMain.btConvert
        val btClear = binding.layoutMain.btClear
        val btCopy = binding.layoutMain.btCopy
        btConvert.setOnClickListener(this::convert)
        btClear.setOnClickListener(this::clear)
        btCopy.setOnClickListener(this::copy)

        binding.layoutMain.rbShnToEng.setOnCheckedChangeListener(this)
        binding.layoutMain.rbEngToShn.setOnCheckedChangeListener(this)

        rg1 = binding.layoutMain.rg1
        rg2 = binding.layoutMain.rg2

        rg1.setOnCheckedChangeListener { rg, id ->
            if (id != -1)
                rg2.clearCheck()
        }

        rg2.setOnCheckedChangeListener { rg, id ->
            if (id != -1)
                rg1.clearCheck()
        }

        rb1 = binding.layoutMain.rb1
        rb2 = binding.layoutMain.rb2
        rb3 = binding.layoutMain.rb3
        rb4 = binding.layoutMain.rb4
        rb5 = binding.layoutMain.rb5
        rb6 = binding.layoutMain.rb6
        rb1.isChecked = true

        enableRadioButtons()
    }

    private fun enableRadioButtons() {
        if (binding.layoutMain.rbShnToEng.isChecked) {
            rb1.setText("tai")
            rb2.setText("tái")
            rb3.setText("တႆး(tai)")
            rb4.setText("တႆး(tái)")
            rb5.setText("တႆး\ntai")
            rb6.setText("တႆး\ntái")
        }

    }

    private fun copy(view: View?) {
        TODO("Not yet implemented")
    }

    private fun convert(view: View?) {

        if (input.text.isNotEmpty()) {

            var result = ""

            if (binding.layoutMain.rbShnToEng.isChecked) {
                when {
                    rb1.isChecked -> result =
                        ShanTranslit.taiToEngWithoutTone(input.text.toString())

                    rb2.isChecked -> result = ShanTranslit.taiToEngWithTone(input.text.toString())
                    rb3.isChecked -> result =
                        ShanTranslit.taiToEngWithoutToneSideBySide(input.text.toString())

                    rb4.isChecked -> result =
                        ShanTranslit.taiToEngWithToneSideBySide(input.text.toString())

                    rb5.isChecked -> {
                        val converted =
                            ShanTranslit.taiToEngWithoutToneUpsideDown(input.text.toString())
                        result = converted.shan + "\n" + converted.eng
                    }

                    rb6.isChecked -> {
                        val converted =
                            ShanTranslit.taiToEngWithToneUpsideDown(input.text.toString())
                        result = converted.shan + "\n" + converted.eng
                    }
                }
            } else {
                result = ShanTranslit.engToShn(input.text.toString())
            }

            output.setText(result)
        }

    }

    private fun clear(view: View?) {
        if (input.text.isNotEmpty())
            input.text.clear()
        if (output.text.isNotEmpty()) output.text.clear()
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        binding.layoutMain.rg.visibility =
            if (binding.layoutMain.rbShnToEng.isChecked) VISIBLE else GONE
    }
}
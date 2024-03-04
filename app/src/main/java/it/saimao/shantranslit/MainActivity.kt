package it.saimao.shantranslit

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import it.saimao.shantranslit.databinding.ActivityMainBinding
import it.saimao.shantranslit.utils.ShanTranslit
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

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
    private lateinit var executorService: ExecutorService

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
        enableService()
    }

    private fun enableService() {
        executorService = Executors.newSingleThreadExecutor()
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
        val text: String = output.getText().toString()
        if (text.isEmpty()) {
            Toast.makeText(this, "No Output Text to be Copied!!", Toast.LENGTH_SHORT).show()
            return
        }
        val clipboard = this.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Output Text Copied Successfully!", Toast.LENGTH_SHORT).show()

    }

    private fun convert(view: View?) {

        if (input.text.isNotEmpty()) {


            if (binding.layoutMain.rbShnToEng.isChecked) {
                when {
                    rb1.isChecked -> output.setText(
                        ShanTranslit.taiToEngWithoutTone(input.text.toString())
                    )

                    rb2.isChecked -> output.setText(
                        ShanTranslit.taiToEngWithTone(input.text.toString())
                    )

                    rb3.isChecked -> {
                        binding.layoutMain.loading.visibility = VISIBLE
                        binding.layoutMain.ui.alpha = 0.3f
                        executorService.execute {
                            val result =
                                ShanTranslit.taiToEngWithoutToneSideBySide(input.text.toString())
                            runOnUiThread {
                                output.setText(result)
                                binding.layoutMain.loading.visibility = GONE
                                binding.layoutMain.ui.alpha = 1f
                            }
                        }
                    }

                    rb4.isChecked -> {
                        binding.layoutMain.loading.visibility = VISIBLE
                        binding.layoutMain.ui.alpha = 0.3f
                        executorService.execute {
                            val result =
                                ShanTranslit.taiToEngWithoutToneSideBySide(input.text.toString())
                            runOnUiThread {
                                binding.layoutMain.loading.visibility = GONE
                                binding.layoutMain.ui.alpha = 1f
                                output.setText(result)
                            }
                        }
                    }

                    rb5.isChecked -> {
                        val converted =
                            ShanTranslit.taiToEngWithoutToneUpsideDown(input.text.toString())
                        output.setText(converted.shan + "\n" + converted.eng)
                    }

                    rb6.isChecked -> {
                        val converted =
                            ShanTranslit.taiToEngWithToneUpsideDown(input.text.toString())
                        output.setText(converted.shan + "\n" + converted.eng)
                    }
                }
            } else {
                output.setText(ShanTranslit.engToShn(input.text.toString()))
            }

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
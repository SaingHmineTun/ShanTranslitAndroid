package it.saimao.word_sorting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import it.saimao.word_sorting.utils.ShanWordSorting

class MainActivity : AppCompatActivity() {
    private lateinit var input: EditText
    private lateinit var output: EditText
    private lateinit var rbS2E: RadioButton
    private lateinit var rbE2S: RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input = findViewById(R.id.edInput)
        output = findViewById(R.id.edOutput)
        rbS2E = findViewById(R.id.rbShanToEng)
        rbE2S = findViewById(R.id.rbEngToShan)
        val btConvert: Button = findViewById(R.id.btConvert)
        val btClear: Button = findViewById(R.id.btClear)
        btConvert.setOnClickListener(this::convert)
        btClear.setOnClickListener(this::clear)
    }

    private fun convert(view: View?) {

        if (input.text.isNotEmpty()) {
            val textString = input.text.trim().replace("\n".toRegex(), "\u0020");
            val texts: List<String> = textString.split("\u0020").sortedWith(ShanWordSorting())
            var result: String = ""
            for (text in texts) {
                result += " $text"
            }
            output.setText(result.trim())
        }

    }

    private fun clear(view: View?) {
        input.text.clear()
        output.text.clear()
    }
}
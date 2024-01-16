package it.saimao.taile_wordbreak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import it.saimao.taile_wordbreak.utils.V1_TaiLeSyllableBreaker
import it.saimao.taile_wordbreak.utils.V2_TaiLeSyllableSegmentation
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
//        val test = input.text.toString().replace("[${V2_TaiLeSyllableSegmentation.endWordChar}]".toRegex(), "");

        val test = input.text.toString()
        val start = System.currentTimeMillis();
        var result: String = V1_TaiLeSyllableBreaker.syllable_break(test);
        result = "Time took - ${(System.currentTimeMillis() - start)}\n$result"
        output.setText(result)

    }

    private fun clear(view: View?) {
//        val test = input.text.toString().replace("[${V2_TaiLeSyllableSegmentation.endWordChar}]".toRegex(), "");
        val test = input.text.toString()
        val start = System.currentTimeMillis();
        var result: String = V2_TaiLeSyllableSegmentation.segmentAsStringWithDelimiter(test, " ");
        result = "Time took - ${(System.currentTimeMillis() - start)}\n$result"
        output.setText(result)
    }
}
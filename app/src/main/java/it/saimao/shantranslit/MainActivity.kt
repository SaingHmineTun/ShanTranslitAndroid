package it.saimao.shantranslit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import it.saimao.shantranslit.utils.ShanTranslit

class MainActivity : AppCompatActivity() {
    private lateinit var input: EditText
    private lateinit var output: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input = findViewById(R.id.input)
        output = findViewById(R.id.output)
        val btConvert: Button = findViewById(R.id.btConvert)
        val btClear: Button = findViewById(R.id.btClear)
        btConvert.setOnClickListener(this::convert)
        btClear.setOnClickListener(this::clear)
    }

    private fun convert(view: View?) {

        if (input.text.isNotEmpty()) {
            output.setText(ShanTranslit.taiToEng(input.text.toString()))
        }

    }

    private fun clear(view: View?) {
        if (input.text.isNotEmpty())
            input.text.clear()

        if (output.text.isNotEmpty()) output.text.clear()
    }
}
package algokelvin.app.csenabledbtn

import algokelvin.app.csenabledbtn.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAction.setOnClickListener {
            val txt = binding.edtTxt.text.toString()
            Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
        }
        binding.btnBack.setOnClickListener {
            val txt = binding.edtTxt.text.toString()
            Toast.makeText(this, "Back is $txt", Toast.LENGTH_SHORT).show()
        }

        binding.edtTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.i("edt-text", "before this")
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.i("edt-text", "now this")
            }
            override fun afterTextChanged(p0: Editable?) {
                Log.i("edt-text", "after this")
                val data = p0.toString()
                binding.edtTxt.isFocusable = false
                binding.btnAction.isFocusable = data.isNotEmpty()
                binding.btnBack.isFocusable = data.isNotEmpty()
                binding.edtTxt.isFocusable = true
            }
        })

    }
}
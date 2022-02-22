package algokelvin.app.csenabledbtn

import algokelvin.app.csenabledbtn.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var txtData = ""

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
                if (data.isNotEmpty()) {
                    binding.btnAction.isFocusable = true
                    binding.btnBack.isFocusable = true
                } else {
                    binding.btnAction.isFocusable = false
                    binding.btnBack.isFocusable = true
                }
            }
        })

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.i("edt-text-down", keyCode.toString())
        when(keyCode) {
            7,8,9,10,11,12,13,14,15,16 -> {
                txtData += (keyCode - 7).toString()
                binding.edtTxt.setText(txtData)
            }
            67 -> {
                txtData = txtData.substring(0, txtData.length - 1)
                binding.edtTxt.setText(txtData)
            }
            4 -> finish()
        }
        Log.i("edt-text-now", txtData)
        return super.onKeyDown(keyCode, event)
    }

}
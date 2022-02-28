package algokelvin.app.csenabledbtn.edttxt

import algokelvin.app.csenabledbtn.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class EdtTextActivity : AppCompatActivity() {
    private val edtTxtViewModel by lazy {
        ViewModelProvider(this)[EdtTxtViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding
    private var txtData = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.isFocusable = true
        binding.edtTxt.isFocusable = true
        binding.btnAction.setOnClickListener {
            rspEdtTxt()
        }
        binding.btnBack.setOnClickListener {
            val txt = binding.edtTxt.text.toString()
            Toast.makeText(this, "Back is $txt", Toast.LENGTH_SHORT).show()
        }

        binding.edtTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.edtTxt.isFocusable = false
                binding.btnBack.isFocusable = false
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.i("edt-text", "now this")
            }
            override fun afterTextChanged(p0: Editable?) {
                Log.i("edt-text", "after this")
                val data = p0.toString()
                binding.btnAction.isFocusable = data.isNotEmpty()
                binding.edtTxt.isFocusable = true
                binding.btnBack.isFocusable = true
            }
        })

    }

    private fun rspEdtTxt() {
        val txt = binding.edtTxt.text.toString()
        edtTxtViewModel.rqsStatus(txt).observe(this, {
            if (it) startActivity(Intent(this, EdtTxtTrueActivity::class.java))
            else Toast.makeText(this, "PIN is False", Toast.LENGTH_SHORT).show()
            edtTxtViewModel.rqsStatus(txt).removeObservers(this)
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
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
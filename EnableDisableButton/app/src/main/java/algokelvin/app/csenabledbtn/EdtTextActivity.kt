package algokelvin.app.csenabledbtn

import algokelvin.app.csenabledbtn.databinding.ActivityMainBinding
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

class EdtTextActivity : AppCompatActivity() {
    private val edtTxtViewModel by lazy {
        ViewModelProvider(this)[EdtTxtViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity = this

        binding.btnAction.setOnClickListener {
            if (binding.btnAction.isEnabled) {
                rspEdtTxt()
            }
        }
        binding.btnBack.setOnClickListener {
            val txt = binding.edtTxt.text.toString()
            Toast.makeText(this, "Back is $txt", Toast.LENGTH_SHORT).show()
        }

        binding.edtTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(p0: Editable?) {
                val data = p0.toString()
                if (data.isNotEmpty()) {
                    binding.btnAction.background = ContextCompat.getDrawable(activity, R.drawable.bg_btn_action_active)
                    binding.btnAction.setTextColor(ContextCompat.getColor(activity, R.color.white))
                    binding.btnAction.isEnabled = true
                }
                else {
                    binding.btnAction.background = ContextCompat.getDrawable(activity, R.drawable.bg_btn_action_deactive)
                    binding.btnAction.setTextColor(ContextCompat.getColor(activity, R.color.grey))
                    binding.btnAction.isEnabled = false
                }
            }
        })
    }

    private fun rspEdtTxt() {
        val txt = binding.edtTxt.text.toString()
        edtTxtViewModel.rqsStatus(txt).observe(this) {
            if (it) startActivity(Intent(this, EdtTxtTrueActivity::class.java))
            else Toast.makeText(this, "PIN is False", Toast.LENGTH_SHORT).show()
            edtTxtViewModel.rqsStatus(txt).removeObservers(this)
        }
    }

}
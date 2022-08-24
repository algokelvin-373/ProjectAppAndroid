package algorithm.kelvin.app.project.example.case_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment(supportFragmentManager, LayerCalculatorFragment::class.java.simpleName, LayerCalculatorFragment(), R.id.fr_layer_calculator)
        setFragment(supportFragmentManager, ButtonCalculatorFragment::class.java.simpleName, ButtonCalculatorFragment(), R.id.fr_button_calculator)
    }

    private fun setFragment(fragmentManager: FragmentManager, fragmentString: String, fragment: Fragment, layout_fragment: Int) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentManager.findFragmentByTag(fragmentString)
        fragmentTransaction.add(layout_fragment, fragment, fragmentString)
        fragmentTransaction.commit()
    }
}

package algorithm.kelvin.app.fragment_01

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_one.*

class OneFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("fragment-view", "ViewCreated 1 is start")

        btnTwoFromFragmentOne.setOnClickListener {
            startActivity(Intent(requireContext(), TwoActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i("fragment-resume", "Resume 1 is start")
    }

    override fun onStart() {
        super.onStart()
        Log.i("fragment-start", "Start 1 is start")
    }

    override fun onStop() {
        super.onStop()
        Log.i("fragment-stop", "Stop 1 is start")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("fragment-destroy", "Destroy 1 is start")
    }
}
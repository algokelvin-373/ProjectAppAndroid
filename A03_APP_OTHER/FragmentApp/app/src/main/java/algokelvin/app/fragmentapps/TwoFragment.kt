package algokelvin.app.fragmentapps

import algokelvin.app.fragmentapps.databinding.FragmentTwoBinding
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TwoFragment : Fragment() {
    private lateinit var binding: FragmentTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.main()
    }

    private fun FragmentTwoBinding.main() {
        btnTwoFromFragmentTwo.setOnClickListener {
            startActivity(Intent(requireContext(), TwoActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i("fragment-resume", "Resume 2 is start")
    }

    override fun onStart() {
        super.onStart()
        Log.i("fragment-start", "Start 2 is start")
    }

    override fun onStop() {
        super.onStop()
        Log.i("fragment-stop", "Stop 2 is start")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("fragment-destroy", "Destroy 2 is start")
    }

}

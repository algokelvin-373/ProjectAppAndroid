package algokelvin.app.navcomponent

import algokelvin.app.navcomponent.databinding.FragmentHomeBinding
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmitName.setOnClickListener {
            val dataName = binding.edtName.text.toString()
            if (!TextUtils.isEmpty(dataName)) {
                val bundleName = bundleOf("name_data" to dataName)
                it.findNavController().navigate(R.id.action_homeFragment_to_secondFragment, bundleName)
            } else {
                Toast.makeText(activity, "Please insert your name", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
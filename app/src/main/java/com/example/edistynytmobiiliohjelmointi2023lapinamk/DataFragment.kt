package com.example.edistynytmobiiliohjelmointi2023lapinamk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.edistynytmobiiliohjelmointi2023lapinamk.databinding.FragmentDataBinding


/**
 * A simple [Fragment] subclass.
 * Use the [DataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DataFragment : Fragment() {
    private var _binding: FragmentDataBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //ulkoasussa löytyy button_navigate_detail -id:llä oleva button
        binding.buttonNavigateDetail.setOnClickListener {
            Log.d("Testi", "Nappia painettu")

            // haetaan action, jonka avulla voidaan siirtyä DataFragmentista -> DetailFragment
            // yksi parametri, id (kokonaisluku)
            val action = DataFragmentDirections.actionDataFragmentToDetailFragment(14543)
            it.findNavController().navigate(action)
        }

// navigate to another fragment, pass some parameter too

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

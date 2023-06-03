package com.example.edistynytmobiiliohjelmointi2023lapinamk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.edistynytmobiiliohjelmointi2023lapinamk.databinding.FragmentDataDetailBinding
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass.
 * Use the [DataDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DataDetailFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentDataDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: DataDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val url = "https://jsonplaceholder.typicode.com/todos/" + args.id
        Log.d("TESTI", url)


        val queue = Volley.newRequestQueue(requireContext())
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val gson = Gson()

                val item: ToDo = gson.fromJson(response, ToDo::class.java)

                binding.textViewDetailId.text = "id: " + item.id.toString()
                binding.textViewDetailUserId.text = "userId: " + item.userId.toString()
                binding.textViewDetailTitle.text = "title: " + item.title
                binding.textViewDetailCompleted.text = "completed: " + item.completed.toString()
            },
            { error ->
                Log.d("ERROR", "error, error: $error")
            }
        )

        queue.add(stringRequest)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
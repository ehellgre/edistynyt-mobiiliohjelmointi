package com.example.edistynytmobiiliohjelmointi2023lapinamk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.edistynytmobiiliohjelmointi2023lapinamk.databinding.FragmentDataBinding
import com.example.edistynytmobiiliohjelmointi2023lapinamk.datatypes.todo.ToDo
import com.google.gson.Gson


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
    private lateinit var adapter: TodoAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //ulkoasussa löytyy button_navigate_detail -id:llä oleva button
        //        //        //binding.buttonNavigateDetail.setOnClickListener {
        //        //         //   Log.d("Testi", "Nappia painettu")
        ////
        //       //     // haetaan action, jonka avulla voidaan siirtyä DataFragmentista -> DetailFragment
        //            // yksi parametri, id (kokonaisluku)
        //       //     val action = DataFragmentDirections.actionDataFragmentToDetailFragment(14543)
        //        //    it.findNavController().navigate(action)
       // }

// navigate to another fragment, pass some parameter too

        linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerViewTodos.layoutManager = linearLayoutManager

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonGetData.setOnClickListener {
            val queue = Volley.newRequestQueue(requireContext())
            val url = "https://jsonplaceholder.typicode.com/todos"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    // tekee gson luokan -> parsitaan jsonia
                    val gson = Gson()
                    // käytetään gsonia parsimaan json ressiä ja laitetaan listalle
                    val todoItems: List<ToDo> = gson.fromJson(response, Array<ToDo>::class.java).toList()

                    // adapterin luonti
                    adapter = TodoAdapter(todoItems)
                    binding.recyclerViewTodos.adapter = adapter


                    for (item in todoItems) {
                        Log.d("DataFragment", "Todo Title: ${item.title}")
                    }
                },
                // err
                { error -> Log.e("DataFragment", error.toString()) })

            queue.add(stringRequest)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

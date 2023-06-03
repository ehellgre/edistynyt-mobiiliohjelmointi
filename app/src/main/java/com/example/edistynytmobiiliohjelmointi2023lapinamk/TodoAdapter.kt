package com.example.edistynytmobiiliohjelmointi2023lapinamk

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.edistynytmobiiliohjelmointi2023lapinamk.databinding.RecyclerviewItemBinding

// 35:10


// aloitetaan luomalla uusi luokka TodoHolder
class TodoAdapter(private val todos: List<ToDo>) : RecyclerView.Adapter<TodoAdapter.TodoHolder>() {
    // tähän väliin tulee kaikki RecyclerView-adapterin vaatimat metodit
    // kuten onCreateViewHolder, onBindViewHolder sekä getItemCount

    // binding layerin muuttujien alustaminen
    private var _binding: RecyclerviewItemBinding? = null
    private val binding get() = _binding!!

    // !!! RecyclerView adapterin vaatimat metodit START !!!
    // ViewHolderin onCreate-metodi. käytännössä tässä kytketään binding layer
    // osaksi CommentHolder-luokkaan (adapterin sisäinen luokka)
    // koska CommentAdapter pohjautuu RecyclerViewin perusadapteriin, täytyy tästä
    // luokasta löytyä metodi nimeltä onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        // binding layerina toimii yksitätinen recyclerview_item_row.xml -instanssi
        _binding = RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoHolder(binding)
    }

    // tämä metodi kytkee yksittäisen Comment-objektin yksittäisen CommentHolder-instanssiin
    // koska CommentAdapter pohjautuu RecyclerViewin perusadapteriin, täytyy tästä
    // luokasta löytyä metodi nimeltä onBindViewHolder
    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        val itemTodo = todos[position]
        holder.bindTodo(itemTodo)
    }

    // Adapterin täytyy pysty tietämään sisältämänsä datan koko tämän metodin avulla
    // koska CommentAdapter pohjautuu RecyclerViewin perusadapteriin, täytyy tästä
    // luokasta löytyä metodi nimeltä getItemCount
    override fun getItemCount(): Int {
        return todos.size
    }
    // !!! RecyclerView adapterin vaatimat metodit STOP !!!

    // TodoHolder, joka määritettiin oman CommentAdapterin perusmäärityksessä (ks. luokan yläosa)
    // Holder-luokka sisältää logiikan, jolla data ja ulkoasu kytketään toisiinsa
    class TodoHolder(v: RecyclerviewItemBinding) : RecyclerView.ViewHolder(v.root), View.OnClickListener {

        // tämän kommentin ulkoasu ja varsinainen data
        private var view: RecyclerviewItemBinding = v
        private var itemlist: ToDo? = null

        // mahdollistetaan yksittäisen itemin klikkaaminen tässä luokassa
        init {
            //suoritetaan kun itemiä klikataan
            v.root.setOnClickListener(this)
        }

        // metodi, joka kytkee datan yksityiskohdat ulkoasun yksityiskohtiin
        fun bindTodo(itemlist : ToDo)
        {

            //laitetaan yksittäinen itemlist-data talteen myöhempää käyttöä varten, ks. onClick
            this.itemlist = itemlist

            //mäpätään title sopivaan viewiin
            view.textViewTodoTitle.text = itemlist.title
            view.textViewTodoCompleted.text = itemlist.completed.toString()

            if (itemlist.completed == true) {
                view.textViewTodoCompleted.text = "✔"
                view.textViewTodoCompleted.setTextColor(Color.GREEN)
            } else {
                view.textViewTodoCompleted.text = "X"
                view.textViewTodoCompleted.setTextColor(Color.RED)
            }

        }

        // jos itemiä klikataan käyttöliittymässä, ajetaan tämä koodio
        override fun onClick(v: View) {

        }
    }

}

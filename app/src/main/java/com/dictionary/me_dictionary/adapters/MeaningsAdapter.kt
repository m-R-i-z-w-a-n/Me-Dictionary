package com.dictionary.me_dictionary.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dictionary.me_dictionary.models.Meanings
import com.dictionary.me_dictionary.R

open class MeaningsAdapter(
    private var context: Context,
    private var meaningsList: List<Meanings>
) : RecyclerView.Adapter<MeaningsAdapter.MeaningsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningsViewHolder {
        return MeaningsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.meanings_list_items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MeaningsViewHolder, position: Int) {
        val definitionsAdapters =
            meaningsList[position].definitions?.let { DefinitionsAdapter(context, it) }

        holder.apply {
            partsOfSpeechView.text = "Parts of Speech: ${meaningsList[position].partOfSpeech}"
            definitionsRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(context, 1)
                adapter = definitionsAdapters
            }
        }
    }

    override fun getItemCount(): Int {
        return meaningsList.size
    }

    inner class MeaningsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val partsOfSpeechView: TextView
        val definitionsRecyclerView: RecyclerView

        init {
            partsOfSpeechView = itemView.findViewById(R.id.text_view_parts_of_speech)
            definitionsRecyclerView = itemView.findViewById(R.id.recycler_definitions)
        }
    }
}
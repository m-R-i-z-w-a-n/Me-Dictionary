package com.dictionary.me_dictionary.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dictionary.me_dictionary.models.Definitions
import com.dictionary.me_dictionary.R

class DefinitionsAdapter(
    private var context: Context,
    private var definitionsList: List<Definitions>
) : RecyclerView.Adapter<DefinitionsAdapter.DefinitionsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionsViewHolder {
        return DefinitionsViewHolder(LayoutInflater.from(context).inflate(R.layout.definitions_list_items, parent, false))
    }

    override fun onBindViewHolder(holder: DefinitionsViewHolder, position: Int) {
        fun initDefinitions(view: TextView, string: StringBuilder) {
            view.apply {
                text = string
                isSelected = true
            }
        }

        val synonyms = StringBuilder()
        synonyms.append(definitionsList[position].synonyms)
        val antonyms = StringBuilder()
        antonyms.append(definitionsList[position].antonyms)

        holder.apply {
            definitionView.text = "Definition: ${definitionsList[position].definition}"
            exampleView.text = "Example: ${definitionsList[position].example}"
            initDefinitions(synonymView, synonyms)
            initDefinitions(antonymView, antonyms)
        }
    }

    override fun getItemCount(): Int {
        return definitionsList.size
    }

    inner class DefinitionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val definitionView: TextView
        val exampleView: TextView
        val synonymView: TextView
        val antonymView: TextView

        init {
            definitionView = itemView.findViewById(R.id.text_view_definition)
            exampleView = itemView.findViewById(R.id.text_view_example)
            synonymView = itemView.findViewById(R.id.text_view_synonym)
            antonymView = itemView.findViewById(R.id.text_view_antonym)
        }
    }

}
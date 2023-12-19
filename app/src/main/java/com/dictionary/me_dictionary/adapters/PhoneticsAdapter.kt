package com.dictionary.me_dictionary.adapters

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dictionary.me_dictionary.R
import com.dictionary.me_dictionary.models.Phonetics

class PhoneticsAdapter(
    private var context: Context,
    private var phoneticsList: List<Phonetics>
) : RecyclerView.Adapter<PhoneticsAdapter.PhoneticsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneticsViewHolder {
        return PhoneticsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.phonetic_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhoneticsViewHolder, position: Int) {
        holder.apply {
            phoneticView.text = phoneticsList[position].text
            audioButton.setOnClickListener {
                val audioUrl = phoneticsList[position].audio
                if (audioUrl.isEmpty()){
                    Toast.makeText(context, "Audio not found!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val mediaPlayer = MediaPlayer()
                try {
                    mediaPlayer.apply {
                        setDataSource(audioUrl)
                        prepareAsync()
                        setOnPreparedListener {
                            it.start()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
                }  finally {
                    // Release the media player to free system resources
                    mediaPlayer.release()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return phoneticsList.size
    }

    inner class PhoneticsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val phoneticView: TextView
        val audioButton: ImageButton

        init {
            phoneticView = itemView.findViewById(R.id.textview_phonetic)
            audioButton = itemView.findViewById(R.id.img_btn_audio)
        }
    }
}
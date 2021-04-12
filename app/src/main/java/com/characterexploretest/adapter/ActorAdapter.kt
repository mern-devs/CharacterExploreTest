package com.characterexploretest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.characterexploretest.R
import com.characterexploretest.model.Actor
import com.facebook.drawee.view.SimpleDraweeView

class ActorAdapter(private var data: MutableList<Actor>, private val onClick: ((Actor) -> Unit)): RecyclerView.Adapter<ActorAdapter.ActorVH>() {

    fun setData(data: MutableList<Actor>) {
        this.data = data
        notifyDataSetChanged()
    }
    inner class ActorVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val actorName: TextView = itemView.findViewById(R.id.actorName)
        private val avatar: SimpleDraweeView = itemView.findViewById(R.id.avatar)

        fun bind(actor: Actor) {
            actorName.text = actor.name
            avatar.setImageURI(actor.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_actor, parent, false)
        return ActorVH(itemView)
    }

    override fun onBindViewHolder(holder: ActorVH, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            onClick.invoke(data[position])
        }
    }

    override fun getItemCount(): Int = data.size

}
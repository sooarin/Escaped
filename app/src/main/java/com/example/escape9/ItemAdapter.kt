package com.example.escape9

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.item.view.*

class ItemAdapter(private val escapeList: ArrayList<Item>, private val context: Context) : RecyclerView.Adapter<ItemAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var posterList = escapeList[position].poster!!.split(",".toRegex()).toTypedArray()
        Log.d("test",posterList[0])
        var imageReference = Firebase.storage("gs://escaped-85171.appspot.com").reference.child("img/" + posterList[0])
        imageReference.downloadUrl.addOnSuccessListener { Uri ->
            val imageUrl = Uri.toString()
            Glide.with(holder.itemView)
                .load(imageUrl)
                .into(holder.poster_esc)
        }
        holder.region_esc.text = escapeList[position].region
        holder.cafe_esc.text = escapeList[position].cafe
        holder.theme_esc.text = escapeList[position].theme
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var poster_esc: ImageView = itemView.findViewById(R.id.esc_poster)
        var region_esc: TextView = itemView.findViewById(R.id.esc_region)
        var cafe_esc: TextView = itemView.findViewById(R.id.esc_cafe)
        var theme_esc: TextView = itemView.findViewById(R.id.esc_theme)

       /** init {
            itemView.setOnClickListener {
                var pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    var item = escapeList[pos]
                    var posterList = escapeList[pos].poster!!.split(",".toRegex()).toTypedArray()
                    var intent = Intent(itemView.context, EscapeActivity::class.java)
                    intent.putExtra("poster",posterList)
                    intent.putExtra("diff",item.diff)
                    intent.putExtra("recom",item.recom)
                    ContextCompat.startActivity(itemView.context, intent, null)
                }
            }
        } **/
    }

    override fun getItemCount(): Int {
        return escapeList.size
    }
}
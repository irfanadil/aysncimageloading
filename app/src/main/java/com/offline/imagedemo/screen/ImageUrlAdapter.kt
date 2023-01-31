package com.offline.imagedemo.screen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.offline.imagedemo.R
import com.offline.imagedemo.screen.model.ResponseModel

class ImageUrlAdapter
internal constructor(
    onAdapterClickListener: AdapterClickListener
) : RecyclerView.Adapter<ImageUrlAdapter.ImageItemViewHolder>() {

    private var classScopedItemClickListener: AdapterClickListener = onAdapterClickListener

    init {
        this.classScopedItemClickListener = onAdapterClickListener
    }

    private var passedList: ArrayList<ResponseModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder {
        val holder: ImageItemViewHolder
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        holder = ImageItemViewHolder(inflater.inflate(R.layout.inner_item, parent, false))
        return holder

    }

    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {

        val item = passedList[position]
        item.apiImageUrl[0].url.let {
            val context = holder.imageView.context
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(context)
                .load(it)
                .placeholder(circularProgressDrawable)
                //.timeout(60000)
                .override(140, 140)
                .into(holder.imageView)
        }

        holder.longUrlTextView.text = item.created.toString()
        holder.imageView.setOnClickListener {
            classScopedItemClickListener.onImageClick(position)
        }
    }

    override fun getItemCount(): Int {
        return passedList.size
    }

    inner class ImageItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var longUrlTextView: TextView = itemView.findViewById(R.id.long_url_textview)
        var imageView: ImageView = itemView.findViewById(R.id.simple_image_view)
        override fun onClick(v: View?) {}
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addImage(item: ResponseModel) {
        this.passedList.add(item)
        //notifyDataSetChanged()
        notifyItemInserted(this.passedList.size)
    }

}
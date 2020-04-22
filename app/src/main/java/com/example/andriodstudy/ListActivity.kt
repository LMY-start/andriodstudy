package com.example.andriodstudy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : AppCompatActivity() {

    val datas = mutableListOf(
        Data("千味拉面千味拉面千味拉面千味拉面千味拉面千味拉面千味拉面千味拉面千味拉面千味拉面","bingyang","13312341234","绿地世纪城"),
        Data("必胜客宅急送","xiongmaopisa","13312341234","木星"),
        Data("麦当劳","kaoniurou","13312341234","冰粉那就"),
        Data("肯德基","longfeikaorou","13312341234","地球上"),
        Data("黄焖鸡","mizhikaorou","13312341234","火星")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        title="list"
        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = MyAdapter(datas)
    }

    inner class MyAdapter(val data:MutableList<Data>) : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
          return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout,null))
        }

        override fun getItemCount(): Int {
           return data.size;
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(datas[position])
        }

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(data: Data){
            itemView.findViewById<TextView>(R.id.textView).text = data.name
            val field = R.mipmap::class.java.getField(data.image)
            val id = field.getInt(field)
            itemView.findViewById<ImageView>(R.id.imageView).setImageResource(id)

        }
    }
}

data class Data(
    val name:String,
    val image:String,
    val tel:String,
    val location:String

)

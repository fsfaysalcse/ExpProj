package com.example.motiontest

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import java.util.*


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = listOf<String>(
            "Android Is best",
            "IOS is optimized",
            "Linux is super",
            "Windows is buggy",
            "I dont love windows",
            "I love ios",
        )

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val adapter = MyPagerAdapter()
        adapter.updateList(list)
        viewPager.adapter = adapter

        adapter.setOnColorChangeListener(object : MyPagerAdapter.OnColorChangeListener {
            override fun onColorChange(position: Int, color: Int) {
                //change color here
            }
        })

        findViewById<Button>(R.id.changeColor).setOnClickListener {
            val color = (Math.random() * 16777215).toInt() or (0xFF shl 24)

            val getPosition = viewPager.currentItem
            val myAdapter = viewPager.adapter as MyPagerAdapter
            val holder = myAdapter.getVieHolder(getPosition)

            if (holder != null) {


                val spannableStringBuilder = SpannableStringBuilder(holder.textView.text)
                val span = SpannableStringBuilder(holder.textView.text)


                val wordToFind = "love"
                val startIndex = spannableStringBuilder.indexOf(wordToFind)

                Log.d("dgdsfgdsfgdfsg", "onCreate: $startIndex")

                if (startIndex != -1){
                    val endIndex = startIndex + wordToFind.length


                    span.setSpan(
                        ForegroundColorSpan(Color.RED),
                        startIndex,
                        endIndex,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    holder.textView.text = span

                    /*  val handler = Handler()
                      val runnable = Runnable {
                          spannableStringBuilder.removeSpan(span)
                          holder.textView.text = spannableStringBuilder
                      }
                      handler.postDelayed(runnable, 3000)*/

                    holder.textView.postDelayed({
                        spannableStringBuilder.removeSpan(span)
                        holder.textView.text = spannableStringBuilder
                    }, 3000)
                }

            }

        }


    }

    class MyPagerAdapter : RecyclerView.Adapter<MyPagerAdapter.MyViewHolder>() {
        val itemList = mutableListOf<String>()
        private var mHolders = SparseArray<MyViewHolder>()
        private var color: Int = Color.BLACK

        interface OnColorChangeListener {
            fun onColorChange(position: Int, color: Int)
        }

        var listener: OnColorChangeListener? = null
        fun setOnColorChangeListener(listener: OnColorChangeListener) {
            this.listener = listener
        }

        fun changeColor(position: Int, color: Int) {
            this.color = color
            listener?.onColorChange(position, color)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.custom_layout, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val item = itemList[position]
            val spanBuilder = SpannableStringBuilder()
            spanBuilder.append(item)
            spanBuilder.setSpan(
                UnderlineSpan(),
                0,
                5,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )

            holder.textView.text = spanBuilder
            holder.textView.setTextColor(color)

            val span = SpannableStringBuilder(holder.textView.text)
            holder.textView2.text = span

            mHolders.put(position, holder)
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var textView: TextView = itemView.findViewById(R.id.textView)
            var textView2: TextView = itemView.findViewById(R.id.textView2)

        }

        fun updateList(list: List<String>) {
            if (itemList.isNotEmpty()) {
                itemList.clear()
            }
            itemList.addAll(list)
            notifyDataSetChanged()
        }

        fun getVieHolder(position: Int): MyViewHolder? {
            return mHolders.get(position)
        }
    }
}
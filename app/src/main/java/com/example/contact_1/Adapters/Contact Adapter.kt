package com.example.contact_1.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.contact_1.Models.MyContact
import com.example.contact_1.R
import kotlinx.android.synthetic.main.item_rv.view.*

class ContactAdapter (var rvClick:RvClick,var list: List<MyContact>): RecyclerView.Adapter<ContactAdapter.VH>() {
    inner class VH(var itemRv:View):RecyclerView.ViewHolder(itemRv){
        fun onBind(myContact: MyContact,position: Int){
             itemRv.item_name.text = myContact.name
             itemRv.item_number.text = myContact.number

            itemRv.setOnLongClickListener {
                rvClick.delateContact(myContact,position)
                true

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
          holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int = list.size
    interface RvClick{
        fun delateContact(myContact: MyContact,position: Int){

        }
    }

}
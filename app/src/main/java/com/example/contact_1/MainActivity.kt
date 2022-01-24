package com.example.contact_1

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRouter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.contact_1.Adapters.ContactAdapter
import com.example.contact_1.Models.MyContact
import com.example.contact_1.MyShare.MySharedPrefarance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.dialog.view.*
import java.util.ArrayList
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {
    lateinit var rv1 :RecyclerView
    lateinit var alertDialog: AlertDialog
    lateinit var list: ArrayList<MyContact>
    lateinit var myContactAdaapter: ContactAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv1 = findViewById(R.id.rv)
        buildDialog()

        MySharedPrefarance.init(this)
        list = ArrayList()
        list.addAll(MySharedPrefarance.contactList!!)

        addContact.setOnClickListener {
            alertDialog.show()
        }

        val simpleCallback =  object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                var position1 = viewHolder.adapterPosition

                when(direction){

                    ItemTouchHelper.RIGHT->{
                        val number = list[position1].number

                        val intent = Intent(Intent.ACTION_CALL)
                        intent.data = Uri.parse("tel:$number")
                        if (ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.CALL_PHONE) !=PackageManager.PERMISSION_GRANTED){
                            return
                        }
                        startActivity(intent)

                        
                    }

                    ItemTouchHelper.LEFT->{
                        list.removeAt(position1)
                        MySharedPrefarance.contactList =list
                        myContactAdaapter.notifyItemRemoved(position1)
                    }
                }
            }

        }
        var itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(rv1)
        myContactAdaapter = ContactAdapter(object : ContactAdapter.RvClick {
            override fun delateContact(myContact: MyContact, position: Int) {}
        }, list)
        rv.adapter = myContactAdaapter


    }

    fun buildDialog() {
        var builder = AlertDialog.Builder(this)
        var view = LayoutInflater.from(this).inflate(R.layout.dialog, null)
        val edtName = view.findViewById<EditText>(R.id.add_name1)
        val edtNumber = view.findViewById<EditText>(R.id.add_number2)

        builder.setView(view)
        val positiveButton = builder.setTitle("Add Contact")
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                val name: String = edtName.text.toString()
                val number: String = edtNumber.text.toString()
                val myContact = MyContact(name, number)
                list.add(myContact)
                MySharedPrefarance.contactList = list
                myContactAdaapter.notifyItemInserted(list.size)
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> })
        alertDialog = builder.create()


    }
}
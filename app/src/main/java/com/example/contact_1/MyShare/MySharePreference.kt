package com.example.contact_1.MyShare

import android.content.Context
import android.content.SharedPreferences
import com.example.contact_1.Models.MyContact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object MySharedPrefarance {
    private const val NAME = "KeshXotiraga"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }


    var contactList:ArrayList<MyContact>?
    get() = gsonStringToArray(preferences.getString("keyList" ,"[]")!!)
    set(value) = preferences.edit{
        it.putString("keyList", arrayToGsonString(value!!))
    }

    fun arrayToGsonString(arrayList: ArrayList<MyContact>): String {
        return Gson().toJson(arrayList)
    }

    fun gsonStringToArray(gsonString: String): ArrayList<MyContact> {
        val typeToken = object : TypeToken<List<MyContact>>() {}.type
        return Gson().fromJson(gsonString, typeToken)
    }
}
package com.example.escape9

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity() {
    private var escapeList: ArrayList<Item> = ArrayList()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance()

        databaseReference = database.getReference("EscapeList/escape")

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                escapeList.clear()

                for(snapshot in dataSnapshot.children) {
                    val escape = snapshot.getValue(Item::class.java)
                    escapeList.add(escape!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Error",error.toException().toString())
            }
        })
        btn_main.setOnClickListener {
            var escapeIntent = Intent(this, EscapeActivity::class.java)
            escapeIntent.putExtra("escapeList", escapeList)
            startActivity(escapeIntent)
        }
    }

}
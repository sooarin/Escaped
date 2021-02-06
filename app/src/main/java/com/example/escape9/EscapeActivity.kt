package com.example.escape9

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_escape.*
import java.io.Serializable
import kotlin.collections.ArrayList


class EscapeActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, Serializable {
    private lateinit var itemAdapter: RecyclerView.Adapter<*>
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var escapeList: ArrayList<Item>
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escape)

        swipeRefreshLayout.setOnRefreshListener(this)
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        escapeList = ArrayList()
        var escapeList = intent.getParcelableArrayListExtra<Item>("escapeList")!!
        Log.d("tomato",escapeList.toString())
        itemAdapter = ItemAdapter(escapeList, this)
        recyclerView.adapter = itemAdapter
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = true
        Toast.makeText(this,"새로고침중..",Toast.LENGTH_SHORT).show()

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("EscapeList/escape")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                escapeList.clear()

                for (snapshot in dataSnapshot.children) {
                    val escape = snapshot.getValue(Item::class.java)
                    Log.d("firebasedata","getdata"+escape.toString())
                    escapeList.add(escape!!)
                }
                itemAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Error", error.toException().toString())
            }
        })
        Handler().postDelayed({
            swipeRefreshLayout.isRefreshing = false
        }, 1000)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
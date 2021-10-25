package com.example.getandpost

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {
    lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        recyclerView = findViewById(R.id.rv)

        val names = ArrayList<People>()

        recyclerView.adapter = RVadapter(this, names)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this@MainActivity2)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        if (apiInterface != null) {
            apiInterface.getName()?.enqueue(object : Callback<ArrayList<People>> {
                override fun onResponse(
                    call: Call<ArrayList<People>>,
                    response: Response<ArrayList<People>>
                ) {
                    progressDialog.dismiss()
                    for (name in response.body()!!) {
                        names.add(name)
                        Log.d("name","${name.name}")
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<ArrayList<People>>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "" + t.message, Toast.LENGTH_SHORT)
                        .show();
                }
            })
        }
    }
}

package com.example.getandpost

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant

class MainActivity : AppCompatActivity() {
    lateinit var editText: EditText
    lateinit var saveBtn: Button
    lateinit var showBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        saveBtn = findViewById(R.id.button)
        showBtn = findViewById(R.id.button2)



        saveBtn.setOnClickListener {
            var userEnter = People(editText.text.toString())

            addSinglename(userEnter, onResult = {
                editText.setText("")

                Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
            })
        }

        showBtn.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity2::class.java)
            startActivity(intent)
        }
    }


    fun addSinglename(f: People, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.addUser(f).enqueue(object : Callback<People> {
                override fun onResponse(call: Call<People>, response: Response<People>) {

                    onResult()
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<People>, t: Throwable) {
                    onResult()
                    progressDialog.dismiss()

                }
            })
        }
    }
}
package com.almuqsitalif08.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SearchEvent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.almuqsitalif08.crud.databinding.ActivityTampilDataBinding
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject

class TampilDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTampilDataBinding

    val result = ArrayList<model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTampilDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTampilData.setHasFixedSize(true)
        binding.rvTampilData.layoutManager = LinearLayoutManager(this)

        binding.imgBack.setOnClickListener {
            this@TampilDataActivity.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        tampil_data()
    }

    private fun tampil_data() {
        AndroidNetworking.get("http://192.168.43.122/apiV2/show_all_data.php")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    if (response.getInt("success") == 1) {
                        result.clear()
                        val dataArray = response.optJSONArray("data")
                        for (i in 0 until dataArray.length()) {
                            val dataObject = dataArray.optJSONObject(i)
                            result.add(
                                model(
                                    dataObject.getString("id"),
                                    dataObject.getString("nama"),
                                    dataObject.getString("jenis_kelamin"),
                                    dataObject.getString("alamat")
                                )
                            )
                        }
                        binding.rvTampilData.layoutManager = LinearLayoutManager (this@TampilDataActivity, LinearLayoutManager.VERTICAL, false)
                        binding.rvTampilData.adapter = adapter(this@TampilDataActivity, result)
                    } else {
                        Toast.makeText(this@TampilDataActivity, response.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(this@TampilDataActivity, anError?.toString(), Toast.LENGTH_SHORT).show()
                    binding.tvErrorConnection.visibility = View.VISIBLE
                }
            })
    }
}
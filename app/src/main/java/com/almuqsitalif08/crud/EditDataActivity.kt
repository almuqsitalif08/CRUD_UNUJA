package com.almuqsitalif08.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.almuqsitalif08.crud.databinding.ActivityEditDataBinding
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject

class EditDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDataBinding
    private var jenis_kelamin = "L"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgback.setOnClickListener {
            this@EditDataActivity.finish()
        }

        AndroidNetworking.get("http://192.168.43.122/apiV2/show_data.php")
            .addQueryParameter("id", intent.getStringExtra("id").toString())
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    if (response?.getInt("success") == 1) {
                        val data = response.optJSONObject("data")
                        binding.etnama.setText(data.getString("nama"))
                        binding.etalamat.setText(data.getString("alamat"))
                        if (data.getString("jenis_kelamin") == "L") {
                            jenis_kelamin = "L"
                            binding.rbl.isChecked = true
                        } else {
                            jenis_kelamin = "P"
                            binding.rbp.isChecked = true
                        }
                    } else {
                        Toast.makeText(this@EditDataActivity, response?.getString("message"), Toast.LENGTH_SHORT).show()
                        this@EditDataActivity.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(this@EditDataActivity, anError?.toString(), Toast.LENGTH_SHORT).show()
                    this@EditDataActivity.finish()
                }
            })

        binding.btsimpanData.setOnClickListener {
            val nama = binding.etnama.text.toString()
            val alamat = binding.etalamat.text.toString()

            if (nama.isEmpty()) {
                binding.etnama.error = "harus diisi"
                binding.etnama.requestFocus()
            } else if (alamat.isEmpty()) {
                binding.etalamat.error = "harus diisi"
                binding.etalamat.requestFocus()
            } else {
                AndroidNetworking.post("http://192.168.43.122/apiV2/edit_data.php")
                    .addBodyParameter("id", intent.getStringExtra("id"))
                    .addBodyParameter("nama", nama)
                    .addBodyParameter("alamat", alamat)
                    .addBodyParameter("jenis_kelamin", jenis_kelamin)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object: JSONObjectRequestListener{
                        override fun onResponse(response: JSONObject?) {
                            if (response?.getInt("success") == 1) {
                                Toast.makeText(this@EditDataActivity, response.getString("message"), Toast.LENGTH_SHORT).show()
                                this@EditDataActivity.finish()
                            } else {
                                Toast.makeText(this@EditDataActivity, response?.getString("message"), Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onError(anError: ANError?) {
                            Toast.makeText(this@EditDataActivity, anError?.toString(), Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }

        binding.rgjenisKelamin.setOnCheckedChangeListener { _, _ -> jenis_kelamin = if (binding.rbl.isChecked) "L" else "P" }
    }
}
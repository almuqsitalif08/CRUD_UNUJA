package com.almuqsitalif08.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.almuqsitalif08.crud.databinding.ActivityTambahDataBinding
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject

class TambahDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahDataBinding
    private var jenis_kelamin = "L"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgback.setOnClickListener {
            this@TambahDataActivity.finish()
        }

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
                AndroidNetworking.post("http://192.168.43.122/apiV2/add_data.php")
                    .addBodyParameter("nama", nama)
                    .addBodyParameter("alamat", alamat)
                    .addBodyParameter("jenis_kelamin", jenis_kelamin)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            if (response.getInt("success") == 1) {
                                Toast.makeText(
                                    this@TambahDataActivity,
                                    response.getString("message"),
                                    Toast.LENGTH_SHORT
                                ).show()
                                this@TambahDataActivity.finish()
                            } else {
                                Toast.makeText(
                                    this@TambahDataActivity,
                                    response.getString("message"),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onError(anError: ANError?) {
                            Toast.makeText(
                                this@TambahDataActivity,
                                anError?.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
        }

        binding.rgjenisKelamin.setOnCheckedChangeListener { _, _ ->
            jenis_kelamin = if (binding.rbl.isChecked) "L" else "P"
        }
    }
}
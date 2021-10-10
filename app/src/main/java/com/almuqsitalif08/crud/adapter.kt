package com.almuqsitalif08.crud

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.almuqsitalif08.crud.databinding.CustomTampilanBinding
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject

class adapter(private val context: Context, private val items: ArrayList<model>) :
    RecyclerView.Adapter<adapter.MyHolder>() {
//    private var items = ArrayList<model>()
//
//    init {
//        this.items = result
//    }

    inner class MyHolder(val binding: CustomTampilanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            CustomTampilanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val result = items[position]
        with(holder) {
            binding.tvNama.text = result.nama
            binding.tvAlamat.text = result.alamat
            binding.tvJenisKelamin.text = result.jenis_kelamin
            if (result.jenis_kelamin == "L") {
                binding.tvJenisKelamin.text = "Laki-laki"
            } else {
                binding.tvJenisKelamin.text = "Perempuan"
            }
            binding.root.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Pengaturan")
                val pilihan = arrayOf("Edit", "Delete", "Cancel")
                builder.setItems(pilihan) { dialog, which ->
                    when (which) {
                        0 -> {
                            val edit = Intent(context, EditDataActivity::class.java)
                            edit.putExtra("id", result.id)
                            context.startActivity(edit)
                        }
                        1 -> {
                            hapus_data(result.id)
                        }
                        2 -> {
                            dialog.dismiss()
                        }
                    }
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun hapus_data(id: String){
        AndroidNetworking.post("http://192.168.43.122/apiV2/delete_data.php")
            .addBodyParameter("id", id)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    if (response?.getInt("success") == 1) {
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show()
                        (context as Activity).finish()
                    } else {
                        Toast.makeText(context, response?.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(context, anError?.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }
}
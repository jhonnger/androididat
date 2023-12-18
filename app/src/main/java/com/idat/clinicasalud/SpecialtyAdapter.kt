package com.idat.clinicasalud

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SpecialtyAdapter(private val context: Context, private val dataSource: MutableList<String>) : RecyclerView.Adapter<SpecialtyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.specialtyNameTextView)
        val editButton: ImageButton = view.findViewById(R.id.editButton)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)

        fun bind(specialty: String, position: Int, adapter: SpecialtyAdapter) {
            nameTextView.text = specialty
            editButton.setOnClickListener {
                // Lógica para editar
            }
            deleteButton.setOnClickListener {
                adapter.showDeleteConfirmationDialog(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_especialidad, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSource[position], position, this)
    }

    override fun getItemCount(): Int = dataSource.size

    private fun showDeleteConfirmationDialog(position: Int) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.confirmation))
            .setMessage(context.getString(R.string.delete_confirmation))
            .setPositiveButton(context.getString(R.string.delete)) { dialog, _ ->
                dataSource.removeAt(position)
//                notifyItemRemoved(position)
                dialog.dismiss()
            }
            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}
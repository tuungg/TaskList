package paba.c14220130.tasklists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskRecycler(private val listTask: ArrayList<Task>) : RecyclerView.Adapter<TaskRecycler.ListViewHolder>() {

    private lateinit var onItemclickCallback: OnItemClickCallback
    interface OnItemClickCallback {
        fun delData(pos: Int)
        fun editData(pos: Int)
        fun updateStart(pos: Int)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _namaTask: TextView = itemView.findViewById(R.id.namaTask)
        var _tanggalTask: TextView = itemView.findViewById(R.id.tanggalTask)
        var _deskripsiTask: TextView = itemView.findViewById(R.id.deskripsiTask)
        val _btnHapus: Button = itemView.findViewById(R.id.btnHapus)
        val _btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        val _btnStart: Button = itemView.findViewById(R.id.btnStart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_recycler, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val task = listTask[position]

        holder._namaTask.text = task.nama
        holder._deskripsiTask.text = task.deskripsi
        holder._tanggalTask.text = task.tanggal

        if (task.isStarted) {
            holder._btnStart.text = "End"
            holder._btnEdit.isEnabled = false
        } else {
            holder._btnStart.text = "Start"
            holder._btnStart.isEnabled = true
            holder._btnEdit.isEnabled = true
        }

        if (task.isEnded) {
            holder._btnStart.text = "Ended"
            holder._btnStart.isEnabled = false
            holder._btnEdit.isEnabled = false
        }

        holder._btnHapus.setOnClickListener {
            onItemclickCallback.delData(position)
        }

        holder._btnEdit.setOnClickListener {
            onItemclickCallback.editData(position)
        }

        holder._btnStart.setOnClickListener {
            onItemclickCallback.updateStart(position)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemclickCallback = onItemClickCallback
    }



}
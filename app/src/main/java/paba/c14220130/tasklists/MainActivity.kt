package paba.c14220130.tasklists

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var sp : SharedPreferences
    private lateinit var _rvTask: RecyclerView
    private lateinit var _taskAdapter: TaskRecycler
    private var _nama: MutableList<String> = mutableListOf()
    private var _tanggal: MutableList<String> = mutableListOf()
    private var _deskripsi: MutableList<String> = mutableListOf()
    private var _listTask: ArrayList<Task> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _nama = mutableListOf()
        _tanggal = mutableListOf()
        _deskripsi = mutableListOf()

        _rvTask = findViewById(R.id.rvTask)
        val buttonCreate = findViewById<Button>(R.id.btnAdd)
        buttonCreate.setOnClickListener {
            val intent = Intent(this, ActivityUpdate::class.java)
            intent.putExtra("action", "Tambah Task")
            startActivityForResult(intent, add_task)
        }

        fun tambahData() {
            _listTask.clear()
            for (position in _nama.indices) {
                val task = Task(
                    _nama[position],
                    _tanggal[position],
                    _deskripsi[position]
                )
                _listTask.add(task)
            }
        }

        fun tampilkanData() {
            _taskAdapter = TaskRecycler(_listTask)
            _rvTask.layoutManager = LinearLayoutManager(this)
            _rvTask.adapter = _taskAdapter
            _taskAdapter.setOnItemClickCallback(object : TaskRecycler.OnItemClickCallback{
                override fun delData(pos: Int) {
                    if (pos >= 0 && pos < _listTask.size) {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle("Hapus Data")
                            .setMessage("Apakah benar ingin menghapus ${_listTask[pos].nama}?")
                            .setPositiveButton("Hapus") { _, _ ->
                                _listTask.removeAt(pos)
                                _taskAdapter.notifyItemRemoved(pos)
                                _taskAdapter.notifyItemRangeChanged(pos, _listTask.size)
                            }
                            .setNegativeButton("Batal") { dialog, _ ->
                                dialog.dismiss()
                                Toast.makeText(
                                    this@MainActivity,
                                    "Penghapusan dibatalkan",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .show()
                    } else {
                        Toast.makeText(this@MainActivity, "Invalid position", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun editData(pos: Int) {
                    if (pos >= 0 && pos < _listTask.size) {
                        val intent = Intent(this@MainActivity, ActivityUpdate::class.java)
                        intent.putExtra("action", "Edit Task")
                        intent.putExtra("position", pos)
                        intent.putExtra("nama", _listTask[pos].nama)
                        intent.putExtra("tanggal", _listTask[pos].tanggal)
                        intent.putExtra("deskripsi", _listTask[pos].deskripsi)
                        startActivityForResult(intent, edit_task)
                    } else {
                        Toast.makeText(this@MainActivity, "Invalid position", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun updateStart(pos: Int) {
                    if (pos >= 0 && pos < _listTask.size && !_listTask[pos].isStarted) {
                        _listTask[pos].isStarted = true
                        _taskAdapter.notifyItemChanged(pos)
                    } else if (pos >= 0 && pos < _listTask.size && _listTask[pos].isStarted) {
                        _listTask[pos].isEnded = true
                        _taskAdapter.notifyItemChanged(pos)
                    } else {
                        Toast.makeText(this@MainActivity, "Invalid position", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        }

        tambahData()
        tampilkanData()
    }
    companion object {
        const val add_task = 1
        const val edit_task = 2
    }

    private fun addTask(nama: String, tanggal: String, deskripsi: String) {
        val task = Task(nama, tanggal, deskripsi)
        _listTask.add(task)
        _taskAdapter.notifyItemInserted(_listTask.size - 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == add_task && resultCode == RESULT_OK) {
            val nama = data?.getStringExtra("nama")
            val tanggal = data?.getStringExtra("tanggal")
            val deskripsi = data?.getStringExtra("deskripsi")

            if (nama != null && tanggal != null && deskripsi != null) {
                addTask(nama, tanggal, deskripsi)
            }

        } else if (requestCode == edit_task && resultCode == RESULT_OK) {
            val position = data?.getIntExtra("position", -1)
            val nama = data?.getStringExtra("nama")
            val tanggal = data?.getStringExtra("tanggal")
            val deskripsi = data?.getStringExtra("deskripsi")

            if (position != null && position >= 0 && nama != null && tanggal != null && deskripsi != null) {
                _listTask[position].nama = nama
                _listTask[position].tanggal = tanggal
                _listTask[position].deskripsi = deskripsi
                _taskAdapter.notifyItemChanged(position)
            }
        }
    }
}
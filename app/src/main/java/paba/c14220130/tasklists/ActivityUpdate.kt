package paba.c14220130.tasklists

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import paba.c14220130.tasklists.MainActivity.Companion.add_task

class ActivityUpdate : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val _btnBack = findViewById<Button>(R.id.btnBack)
        _btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val namaTask = findViewById<EditText>(R.id.namaTaskInput)
        val tanggalTask = findViewById<EditText>(R.id.tanggalTaskInput)
        val deskripsiTask = findViewById<EditText>(R.id.deskripsiTaskInput)
        val simpanButton = findViewById<Button>(R.id.btnSimpan)

        val nama = intent.getStringExtra("nama")
        if (nama != null) {
            namaTask.setText(nama)
        }
        val tanggal = intent.getStringExtra("tanggal")
        if (tanggal != null) {
            tanggalTask.setText(tanggal)
        }
        val deskripsi = intent.getStringExtra("deskripsi")
        if (deskripsi != null) {
            deskripsiTask.setText(deskripsi)
        }

        simpanButton.setOnClickListener {
            val nama = namaTask.text.toString().trim()
            val tanggal = tanggalTask.text.toString().trim()
            val deskripsi = deskripsiTask.text.toString().trim()

            if (nama.isEmpty() || tanggal.isEmpty() || deskripsi.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultIntent = Intent().apply {
                putExtra("nama", nama)
                putExtra("tanggal", tanggal)
                putExtra("deskripsi", deskripsi)
                putExtra("position", intent.getIntExtra("position", -1))
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
package paba.c14220130.tasklists

data class Task(
    var nama: String,
    var tanggal: String,
    var deskripsi: String,
    var isStarted: Boolean = false,
    var isEnded: Boolean = false
)
package algokelvin.app.recyclerviewbasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvMenu = findViewById<RecyclerView>(R.id.rv_menu)
        rvMenu.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainAdapter(menu())
        }

    }
}
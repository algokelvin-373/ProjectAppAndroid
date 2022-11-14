package algokelvin.app.recyclerviewbasic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(
    private val data: ArrayList<Menus>,
): RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_data, parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class MainHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(data: Menus) {
            val txtId = itemView.findViewById<TextView>(R.id.txt_id)
            val txtName = itemView.findViewById<TextView>(R.id.txt_menu)
            val txtDescription = itemView.findViewById<TextView>(R.id.txt_dec_menu)

            txtId.text = data.code
            txtName.text = data.name
            txtDescription.text = data.description
        }
    }
}
package algokelvin.app.room

import algokelvin.app.room.databinding.ItemSubscriberBinding
import algokelvin.app.room.db.Subscriber
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(
    private val clickListener: (Subscriber) -> Unit
): RecyclerView.Adapter<DataAdapter.DataHolder>() {
    private lateinit var data: ArrayList<Subscriber>

    fun setList(subscriber: List<Subscriber>) {
        data.clear()
        data.addAll(subscriber)
    }

    inner class DataHolder(val binding: ItemSubscriberBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val binding = ItemSubscriberBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DataHolder(binding)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        with(holder) {
            with(data[position]) {
                binding.txtName.text = this.name
                binding.txtEmail.text = this.email
                binding.layoutItem.setOnClickListener {
                    clickListener(this)
                }
            }
        }
    }

    override fun getItemCount(): Int = data.size

}
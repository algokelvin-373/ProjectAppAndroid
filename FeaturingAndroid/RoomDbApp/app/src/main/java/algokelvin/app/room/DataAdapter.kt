package algokelvin.app.room

import algokelvin.app.room.databinding.ItemSubscriberBinding
import algokelvin.app.room.db.Subscriber
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(
    private val data: ArrayList<Subscriber>,
): RecyclerView.Adapter<DataAdapter.DataHolder>() {

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
            }
        }
    }

    override fun getItemCount(): Int = data.size

}
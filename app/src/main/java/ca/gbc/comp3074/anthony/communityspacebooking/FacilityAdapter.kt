package ca.gbc.comp3074.anthony.communityspacebooking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FacilityAdapter(
    private var facilities: List<Facility>,
    private val onFacilityClick: (Facility) -> Unit
) : RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder>() {

    class FacilityViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val name: TextView =
            view.findViewById(R.id.txtItemFacilityName)

        val description: TextView =
            view.findViewById(R.id.txtItemFacilityDescription)

        val capacity: TextView =
            view.findViewById(R.id.txtItemFacilityCapacity)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FacilityViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_facility, parent, false)

        return FacilityViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FacilityViewHolder,
        position: Int
    ) {
        val facility = facilities[position]

        holder.name.text = facility.name
        holder.description.text = facility.description
        holder.capacity.text =
            "Capacity: ${facility.capacity} people"

        holder.itemView.setOnClickListener {
            onFacilityClick(facility)
        }
    }

    override fun getItemCount(): Int = facilities.size

    fun updateFacilities(updated: List<Facility>) {
        facilities = updated
        notifyDataSetChanged()
    }
}

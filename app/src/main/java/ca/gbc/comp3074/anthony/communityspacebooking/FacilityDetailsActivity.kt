
package ca.gbc.comp3074.anthony.communityspacebooking

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FacilityDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facility_details)

        val facilityName = intent.getStringExtra("facilityName")
            ?: "Community Hall"

        val name = findViewById<TextView>(R.id.txtFacilityName)
        val capacity = findViewById<TextView>(R.id.txtCapacity)
        val location = findViewById<TextView>(R.id.txtLocation)
        val description = findViewById<TextView>(R.id.txtDescription)
        val hours = findViewById<TextView>(R.id.txtHours)

        name.text = facilityName

        when (facilityName) {
            "Meeting Room" -> {
                capacity.text = "Capacity: 20 people"
                location.text = "Location: Community complex"
                description.text =
                    "A smaller room for meetings, workshops, and discussions."
            }

            "Gymnasium" -> {
                capacity.text = "Capacity: 80 people"
                location.text = "Location: Recreation centre"
                description.text =
                    "Indoor space for sports, recreation, and community activities."
            }

            else -> {
                capacity.text = "Capacity: 150 people"
                location.text = "Location: Main community complex"
                description.text =
                    "Large multi-purpose space for meetings and community events."
            }
        }

        hours.text = "Sample hours: 8:00 AM - 10:00 PM"

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnBookSpace).setOnClickListener {
            Toast.makeText(
                this,
                "Booking form coming next!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

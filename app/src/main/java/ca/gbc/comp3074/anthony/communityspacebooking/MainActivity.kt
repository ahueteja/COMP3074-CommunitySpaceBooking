package ca.gbc.comp3074.anthony.communityspacebooking

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val facilities = listOf(
        Facility(
            "Community Hall",
            "Community events and gatherings",
            150
        ),
        Facility(
            "Meeting Room",
            "Meetings and workshops",
            20
        ),
        Facility(
            "Gymnasium",
            "Sports and recreation",
            80
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up facility list
        val recyclerView =
            findViewById<RecyclerView>(R.id.recyclerFacilities)

        val adapter = FacilityAdapter(facilities) { facility ->
            openFacility(facility.name)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Facility search
        findViewById<EditText>(R.id.searchFacilities)
            .addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    val query = s.toString().trim()

                    val filtered = facilities.filter {
                        it.name.contains(query, ignoreCase = true) ||
                                it.description.contains(query, ignoreCase = true)
                    }

                    adapter.updateFacilities(filtered)
                }

                override fun afterTextChanged(s: Editable?) {}
            })

        // Home navigation
        findViewById<TextView>(R.id.btnHome)
            .setOnClickListener {
                recyclerView.smoothScrollToPosition(0)
            }

        // My Bookings navigation
        findViewById<TextView>(R.id.btnBookings)
            .setOnClickListener {
                startActivity(
                    Intent(this, MyBookingsActivity::class.java)
                )
            }

        // About navigation
        findViewById<TextView>(R.id.btnAbout)
            .setOnClickListener {
                startActivity(
                    Intent(this, AboutActivity::class.java)
                )
            }
    }

    private fun openFacility(name: String) {
        val intent = Intent(
            this,
            FacilityDetailsActivity::class.java
        )

        intent.putExtra("facilityName", name)
        startActivity(intent)
    }
}

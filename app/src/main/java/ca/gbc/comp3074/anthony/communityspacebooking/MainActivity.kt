package ca.gbc.comp3074.anthony.communityspacebooking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Community Hall
        findViewById<Button>(R.id.btnCommunityHall)
            .setOnClickListener {
                openFacility("Community Hall")
            }

        // Meeting Room
        findViewById<Button>(R.id.btnMeetingRoom)
            .setOnClickListener {
                openFacility("Meeting Room")
            }

        // Gymnasium
        findViewById<Button>(R.id.btnGymnasium)
            .setOnClickListener {
                openFacility("Gymnasium")
            }

        // Home button
        findViewById<Button>(R.id.btnHome)
            .setOnClickListener {
                // Already on Home screen
            }

        // My Bookings
        findViewById<Button>(R.id.btnBookings)
            .setOnClickListener {
                val intent = Intent(
                    this,
                    MyBookingsActivity::class.java
                )
                startActivity(intent)
            }

        // About screen
        findViewById<Button>(R.id.btnAbout)
            .setOnClickListener {
                val intent = Intent(
                    this,
                    AboutActivity::class.java
                )
                startActivity(intent)
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

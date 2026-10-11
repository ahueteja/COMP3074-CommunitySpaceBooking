
package ca.gbc.comp3074.anthony.communityspacebooking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnCommunityHall)
            .setOnClickListener {
                openFacility("Community Hall")
            }

        findViewById<Button>(R.id.btnMeetingRoom)
            .setOnClickListener {
                openFacility("Meeting Room")
            }

        findViewById<Button>(R.id.btnGymnasium)
            .setOnClickListener {
                openFacility("Gymnasium")
            }

        findViewById<Button>(R.id.btnBookings)
            .setOnClickListener {
                val intent = Intent(
                    this,
                    MyBookingsActivity::class.java
                )
                startActivity(intent)
            }

    }



    private fun openFacility(name: String) {
        val intent = Intent(this, FacilityDetailsActivity::class.java)
        intent.putExtra("facilityName", name)
        startActivity(intent)
    }
}

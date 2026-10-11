
package ca.gbc.comp3074.anthony.communityspacebooking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MyBookingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)

        // Open Community Hall booking details
        findViewById<Button>(R.id.btnBookingOne)
            .setOnClickListener {
                openBooking("Community Hall")
            }

        // Open Meeting Room booking details
        findViewById<Button>(R.id.btnBookingTwo)
            .setOnClickListener {
                openBooking("Meeting Room")
            }

        // Return to Home screen
        findViewById<Button>(R.id.btnBackHome)
            .setOnClickListener {
                val intent = Intent(
                    this,
                    MainActivity::class.java
                )

                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                )

                startActivity(intent)
                finish()
            }
    }

    // Navigate to selected booking details
    private fun openBooking(facilityName: String) {
        val intent = Intent(
            this,
            BookingDetailsActivity::class.java
        )

        intent.putExtra("facilityName", facilityName)
        startActivity(intent)
    }
}

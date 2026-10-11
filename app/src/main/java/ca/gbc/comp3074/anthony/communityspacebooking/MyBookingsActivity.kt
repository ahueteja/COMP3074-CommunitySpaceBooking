package ca.gbc.comp3074.anthony.communityspacebooking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MyBookingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)

        findViewById<TextView>(R.id.btnBookingOne)
            .setOnClickListener {
                openBooking("Community Hall")
            }

        findViewById<TextView>(R.id.btnBookingTwo)
            .setOnClickListener {
                openBooking("Meeting Room")
            }

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

    private fun openBooking(facilityName: String) {
        val intent = Intent(
            this,
            BookingDetailsActivity::class.java
        )

        intent.putExtra("facilityName", facilityName)
        startActivity(intent)
    }
}

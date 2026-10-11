
package ca.gbc.comp3074.anthony.communityspacebooking

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class BookingDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)

        val facility = intent.getStringExtra("facilityName")
            ?: "Community Hall"

        val bookingInfo = findViewById<TextView>(
            R.id.txtBookingInformation
        )

        bookingInfo.text = """
            SAMPLE BOOKING

            Facility: $facility

            Date: Demonstration only

            Time: Demonstration only

            Status: Pending

            This booking is sample data and has not been saved.
        """.trimIndent()

        findViewById<Button>(R.id.btnCancelSelectedBooking)
            .setOnClickListener {
                AlertDialog.Builder(this)
                    .setTitle("Cancel Booking")
                    .setMessage(
                        "This is a prototype. No saved booking will be cancelled."
                    )
                    .setPositiveButton("OK", null)
                    .show()
            }

        findViewById<Button>(R.id.btnBackToBookings)
            .setOnClickListener {
                finish()
            }
    }
}

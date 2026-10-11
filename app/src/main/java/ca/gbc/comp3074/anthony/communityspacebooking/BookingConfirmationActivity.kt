package ca.gbc.comp3074.anthony.communityspacebooking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BookingConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_confirmation)

        val facility = intent.getStringExtra("facilityName") ?: "Unknown"
        val date = intent.getStringExtra("bookingDate") ?: "Not selected"
        val start = intent.getStringExtra("startTime") ?: ""
        val end = intent.getStringExtra("endTime") ?: ""
        val attendees = intent.getIntExtra("attendees", 0)
        val purpose = intent.getStringExtra("purpose") ?: ""

        findViewById<TextView>(R.id.txtConfirmationDetails).text =
            """
            Facility: $facility

            Date: $date

            Start Time: $start
            End Time: $end

            Attendees: $attendees

            Purpose: $purpose

            Status: Prototype only — not saved
            """.trimIndent()

        findViewById<Button>(R.id.btnConfirmationHome)
            .setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                )
                startActivity(intent)
                finish()
            }
    }
}

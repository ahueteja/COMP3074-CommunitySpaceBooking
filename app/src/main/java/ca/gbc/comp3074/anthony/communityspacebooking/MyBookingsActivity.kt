package ca.gbc.comp3074.anthony.communityspacebooking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ca.gbc.comp3074.anthony.communityspacebooking.data.AppDatabase
import ca.gbc.comp3074.anthony.communityspacebooking.data.Booking
import kotlinx.coroutines.launch

class MyBookingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)

        val container = findViewById<LinearLayout>(
            R.id.bookingsContainer
        )

        val database = AppDatabase.getDatabase(applicationContext)

        // Observe saved bookings from Room
        lifecycleScope.launch {
            database.bookingDao().getAllBookings().collect { bookings ->
                displayBookings(container, bookings)
            }
        }

        // Return to home screen
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

    private fun displayBookings(
        container: LinearLayout,
        bookings: List<Booking>
    ) {
        // Clear previously displayed items
        container.removeAllViews()

        if (bookings.isEmpty()) {
            val emptyMessage = TextView(this).apply {
                text = "No bookings found."
                textSize = 16f
                setTextColor(getColor(R.color.app_muted))
                setPadding(16, 24, 16, 24)
            }

            container.addView(emptyMessage)
            return
        }

        // Display every saved booking
        bookings.forEach { booking ->

            val bookingCard = TextView(this).apply {
                text = """
                    ${booking.facilityName}
                    Date: ${booking.bookingDate}
                    Time: ${booking.startTime} - ${booking.endTime}
                    Status: ${booking.status}
                    
                    Tap to view details
                """.trimIndent()

                textSize = 16f
                setTextColor(getColor(R.color.app_heading))
                setBackgroundResource(R.drawable.bg_facility_card)
                setPadding(24, 24, 24, 24)

                isClickable = true
                isFocusable = true

                setOnClickListener {
                    val intent = Intent(
                        this@MyBookingsActivity,
                        BookingDetailsActivity::class.java
                    )

                    intent.putExtra("bookingId", booking.id)
                    startActivity(intent)
                }
            }

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 16
            }

            container.addView(bookingCard, params)
        }
    }
}

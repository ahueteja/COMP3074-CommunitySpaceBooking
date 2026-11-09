package ca.gbc.comp3074.anthony.communityspacebooking

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ca.gbc.comp3074.anthony.communityspacebooking.data.AppDatabase
import ca.gbc.comp3074.anthony.communityspacebooking.data.Booking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookingDetailsActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var bookingInfo: TextView
    private lateinit var cancelButton: Button

    private var currentBooking: Booking? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)

        database = AppDatabase.getDatabase(applicationContext)

        bookingInfo = findViewById(R.id.txtBookingInformation)
        cancelButton = findViewById(R.id.btnCancelSelectedBooking)

        val bookingId = intent.getIntExtra("bookingId", -1)

        // Load selected booking
        if (bookingId == -1) {
            bookingInfo.text = "Booking not found."
            cancelButton.isEnabled = false
        } else {
            loadBooking(bookingId)
        }

        // Cancel selected booking
        cancelButton.setOnClickListener {
            val booking = currentBooking ?: return@setOnClickListener

            if (booking.status == "Cancelled") {
                Toast.makeText(
                    this,
                    "This booking is already cancelled.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Cancel Booking")
                .setMessage(
                    "Are you sure you want to cancel this booking?"
                )
                .setPositiveButton("Yes, Cancel") { _, _ ->
                    cancelBooking(booking)
                }
                .setNegativeButton("No", null)
                .show()
        }

        // Return to My Bookings
        findViewById<Button>(R.id.btnBackToBookings)
            .setOnClickListener {
                finish()
            }
    }

    private fun loadBooking(bookingId: Int) {
        lifecycleScope.launch {
            try {
                val booking = withContext(Dispatchers.IO) {
                    database.bookingDao().getBookingById(bookingId)
                }

                if (booking == null) {
                    bookingInfo.text = "Booking not found."
                    cancelButton.isEnabled = false
                    return@launch
                }

                currentBooking = booking
                displayBooking(booking)

            } catch (e: Exception) {
                bookingInfo.text = "Unable to load booking."
                cancelButton.isEnabled = false
            }
        }
    }

    private fun displayBooking(booking: Booking) {
        bookingInfo.text = """
            Booking ID: ${booking.id}

            Facility: ${booking.facilityName}

            Date: ${booking.bookingDate}

            Start Time: ${booking.startTime}
            End Time: ${booking.endTime}

            Attendees: ${booking.attendees}

            Purpose: ${booking.purpose}

            Status: ${booking.status}
        """.trimIndent()

        cancelButton.isEnabled = booking.status != "Cancelled"
    }

    private fun cancelBooking(booking: Booking) {
        cancelButton.isEnabled = false

        lifecycleScope.launch {
            try {
                val updatedBooking = booking.copy(
                    status = "Cancelled"
                )

                withContext(Dispatchers.IO) {
                    database.bookingDao().updateBooking(updatedBooking)
                }

                currentBooking = updatedBooking
                displayBooking(updatedBooking)

                Toast.makeText(
                    this@BookingDetailsActivity,
                    "Booking cancelled successfully.",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {
                cancelButton.isEnabled = true

                Toast.makeText(
                    this@BookingDetailsActivity,
                    "Unable to cancel booking.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

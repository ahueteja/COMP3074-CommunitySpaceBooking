package ca.gbc.comp3074.anthony.communityspacebooking
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.Locale

class CreateBookingActivity : AppCompatActivity() {

    private var selectedDate = ""
    private var startMinutes: Int? = null
    private var endMinutes: Int? = null
    private var selectedStartTime = ""
    private var selectedEndTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_booking)

        val facility = intent.getStringExtra("facilityName")
            ?: "Community Hall"

        findViewById<TextView>(R.id.txtSelectedFacility).text =
            "Facility: $facility"

        val dateButton = findViewById<Button>(R.id.btnSelectDate)
        val startButton = findViewById<Button>(R.id.btnStartTime)
        val endButton = findViewById<Button>(R.id.btnEndTime)

        val attendees = findViewById<EditText>(R.id.edtAttendees)
        val purpose = findViewById<EditText>(R.id.edtPurpose)

        dateButton.setOnClickListener {
            val calendar = Calendar.getInstance()

            val dialog = DatePickerDialog(
                this,
                { _, year, month, day ->
                    selectedDate = String.format(
                        Locale.US,
                        "%04d-%02d-%02d",
                        year,
                        month + 1,
                        day
                    )
                    dateButton.text = selectedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            dialog.datePicker.minDate = System.currentTimeMillis()
            dialog.show()
        }


        startButton.setOnClickListener {
            showTimePicker { minutes, label ->
                startMinutes = minutes
                selectedStartTime = label
                startButton.text = "Start: $label"
            }
        }

        endButton.setOnClickListener {
            showTimePicker { minutes, label ->
                endMinutes = minutes
                selectedEndTime = label
                endButton.text = "End: $label"
            }
        }

        findViewById<Button>(R.id.btnSubmitBooking)
            .setOnClickListener {

                val attendeeCount = attendees.text.toString()
                    .toIntOrNull()

                val bookingPurpose = purpose.text.toString().trim()

                when {
                    selectedDate.isEmpty() -> {
                        showMessage("Please select a booking date")
                    }

                    startMinutes == null || endMinutes == null -> {
                        showMessage("Please select start and end times")
                    }

                    endMinutes!! <= startMinutes!! -> {
                        showMessage("End time must be after start time")
                    }

                    attendeeCount == null || attendeeCount <= 0 -> {
                        attendees.error = "Enter a valid number"
                    }

                    bookingPurpose.isEmpty() -> {
                        purpose.error = "Please enter a purpose"
                    }

                    else -> {
                        val confirmationIntent = Intent(
                            this,
                            BookingConfirmationActivity::class.java
                        )

                        confirmationIntent.putExtra("facilityName", facility)
                        confirmationIntent.putExtra("bookingDate", selectedDate)
                        confirmationIntent.putExtra("startTime", selectedStartTime)
                        confirmationIntent.putExtra("endTime", selectedEndTime)
                        confirmationIntent.putExtra("attendees", attendeeCount)
                        confirmationIntent.putExtra("purpose", bookingPurpose)

                        startActivity(confirmationIntent)
                        finish()
                    }

                }
            }

        findViewById<Button>(R.id.btnCancelBooking)
            .setOnClickListener {
                finish()
            }
    }

    private fun showTimePicker(
        onSelected: (Int, String) -> Unit
    ) {
        val now = Calendar.getInstance()

        TimePickerDialog(
            this,
            { _, hour, minute ->
                val totalMinutes = hour * 60 + minute

                val formatted = String.format(
                    Locale.US,
                    "%02d:%02d",
                    hour,
                    minute
                )

                onSelected(totalMinutes, formatted)
            },
            now.get(Calendar.HOUR_OF_DAY),
            now.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}

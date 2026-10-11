package ca.gbc.comp3074.anthony.communityspacebooking

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        // Select booking date
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

            dialog.datePicker.minDate =
                System.currentTimeMillis() - 1000

            dialog.show()
        }

        // Select start time
        startButton.setOnClickListener {
            showSpinnerTimePicker("Select Start Time") { minutes, label ->
                startMinutes = minutes
                selectedStartTime = label
                startButton.text = "Start: $label"
            }
        }

        // Select end time
        endButton.setOnClickListener {
            showSpinnerTimePicker("Select End Time") { minutes, label ->
                endMinutes = minutes
                selectedEndTime = label
                endButton.text = "End: $label"
            }
        }

        // Submit booking
        findViewById<Button>(R.id.btnSubmitBooking)
            .setOnClickListener {

                val attendeeCount =
                    attendees.text.toString().toIntOrNull()

                val bookingPurpose =
                    purpose.text.toString().trim()

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

                        confirmationIntent.putExtra(
                            "facilityName", facility
                        )
                        confirmationIntent.putExtra(
                            "bookingDate", selectedDate
                        )
                        confirmationIntent.putExtra(
                            "startTime", selectedStartTime
                        )
                        confirmationIntent.putExtra(
                            "endTime", selectedEndTime
                        )
                        confirmationIntent.putExtra(
                            "attendees", attendeeCount
                        )
                        confirmationIntent.putExtra(
                            "purpose", bookingPurpose
                        )

                        startActivity(confirmationIntent)
                        finish()
                    }
                }
            }

        // Return to facility details
        findViewById<Button>(R.id.btnCancelBooking)
            .setOnClickListener {
                finish()
            }
    }

    // Scrolling time picker: Hour / Minute / AM-PM
    private fun showSpinnerTimePicker(
        title: String,
        onSelected: (Int, String) -> Unit
    ) {
        val now = Calendar.getInstance()
        val currentHour = now.get(Calendar.HOUR_OF_DAY)

        val hourPicker = NumberPicker(this).apply {
            minValue = 1
            maxValue = 12
            value = if (currentHour % 12 == 0) {
                12
            } else {
                currentHour % 12
            }
            wrapSelectorWheel = true
        }

        val minutePicker = NumberPicker(this).apply {
            minValue = 0
            maxValue = 59
            setFormatter { String.format(Locale.US, "%02d", it) }
            value = now.get(Calendar.MINUTE)
            wrapSelectorWheel = true
        }

        val amPmPicker = NumberPicker(this).apply {
            minValue = 0
            maxValue = 1
            displayedValues = arrayOf("AM", "PM")
            value = if (currentHour < 12) 0 else 1
            wrapSelectorWheel = false
        }

        val pickerLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(24, 16, 24, 16)
            gravity = android.view.Gravity.CENTER
        }

        val pickerParams = LinearLayout.LayoutParams(
            0,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1f
        )

        pickerLayout.addView(hourPicker, pickerParams)

        pickerLayout.addView(
            minutePicker,
            LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
        )

        pickerLayout.addView(
            amPmPicker,
            LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
        )

        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(pickerLayout)
            .setPositiveButton("OK") { _, _ ->

                val hour12 = hourPicker.value
                val minute = minutePicker.value
                val isPm = amPmPicker.value == 1

                val hour24 = (hour12 % 12) +
                        if (isPm) 12 else 0

                val totalMinutes = hour24 * 60 + minute

                val formattedTime = String.format(
                    Locale.US,
                    "%d:%02d %s",
                    hour12,
                    minute,
                    if (isPm) "PM" else "AM"
                )

                onSelected(totalMinutes, formattedTime)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}

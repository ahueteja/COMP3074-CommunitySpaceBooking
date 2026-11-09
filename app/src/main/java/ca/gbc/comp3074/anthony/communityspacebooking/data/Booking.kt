package ca.gbc.comp3074.anthony.communityspacebooking.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class Booking(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val facilityName: String,
    val bookingDate: String,
    val startTime: String,
    val endTime: String,
    val attendees: Int,
    val purpose: String,
    val status: String = "Pending"
)

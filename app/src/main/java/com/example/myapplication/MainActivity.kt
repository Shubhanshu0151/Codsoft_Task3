package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import android.widget.ToggleButton
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    var alarmTimePicker : TimePicker? = null
    var pendingIntent : PendingIntent? = null
    var alarmManager : AlarmManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alarmTimePicker = findViewById(R.id.timePicker)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

    }

    fun onToggleClick(view: View) {
        var time : Long

        if((view as ToggleButton).isChecked){
            Toast.makeText(this, "Alarm On",Toast.LENGTH_SHORT).show()

            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = alarmTimePicker!!.currentHour
            calendar[Calendar.MINUTE] = alarmTimePicker!!.currentMinute

            val intent = Intent(this,AlarmReceiver::class.java)

            pendingIntent = PendingIntent.getBroadcast(this,0,intent, PendingIntent.FLAG_IMMUTABLE)
            time = calendar.timeInMillis - calendar.timeInMillis % 60000

            if (System.currentTimeMillis() > time ){
                time = if (Calendar.AM_PM == 0 ) {
                    time +1000*60*60*12
                }
                else{
                    time + 1000 * 60 * 60 * 24
                }
            }
            alarmManager!!.setRepeating(AlarmManager.RTC_WAKEUP,time,1000,pendingIntent)
        }
        else{
            alarmManager!!.cancel(pendingIntent)
            Toast.makeText(this, "Alarm On",Toast.LENGTH_SHORT).show()
        }
    }
}
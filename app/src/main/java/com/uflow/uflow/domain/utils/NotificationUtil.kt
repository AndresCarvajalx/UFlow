package com.uflow.uflow.domain.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.uflow.uflow.R
import com.uflow.uflow.domain.models.WorkTask
import java.time.LocalTime
import java.util.Calendar

class NotificationUtil(val ctx: Context) {

    companion object {
        const val CHANNEL_ID = "Notification"
        const val NOTIFICATION_TO_DELIVERY = "ToDeliveryDate"
        const val NOTIFICATION_TO_DO = "ToDoDate"

    }

    init {
        createChannel(context = ctx)
    }

    private fun createChannel(
        context: Context
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification = NotificationChannel(
                CHANNEL_ID,
                "Alarm",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alarm Description"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notification)
        }
    }

    fun builderBasicNotification(
        context: Context = this.ctx,
        title: String = "Alarm",
        priority: Int = NotificationManager.IMPORTANCE_DEFAULT,
        description: String = "Alarm Description"
    ) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_air_24)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(priority)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(1, builder.build())
        }
    }

    fun setNotification(context: Context = this.ctx, workTask: WorkTask) {
        val toDoCalendar = Calendar.getInstance()
        toDoCalendar.set(Calendar.YEAR, workTask.toDoDate.year)
        toDoCalendar.set(Calendar.MONTH, workTask.toDoDate.monthValue - 1)
        toDoCalendar.set(Calendar.DAY_OF_MONTH, workTask.toDoDate.dayOfMonth)
        toDoCalendar.set(Calendar.HOUR_OF_DAY, workTask.toDoTime.hour)
        toDoCalendar.set(Calendar.MINUTE, workTask.toDoTime.minute)

        val deliveryCalendar = Calendar.getInstance()
        deliveryCalendar.set(Calendar.YEAR, workTask.toDeliveryDate.year)
        deliveryCalendar.set(Calendar.MONTH, workTask.toDeliveryDate.monthValue - 1)
        deliveryCalendar.set(Calendar.DAY_OF_MONTH, workTask.toDeliveryDate.dayOfMonth)
        deliveryCalendar.set(Calendar.HOUR_OF_DAY, workTask.toDeliveryTime.hour)
        deliveryCalendar.set(Calendar.MINUTE, workTask.toDeliveryTime.minute)

        val currentTime = System.currentTimeMillis()

        if (toDoCalendar.timeInMillis > currentTime) {
            scheduleNotification(context, workTask, "TO_DO_${workTask.id}", toDoCalendar)
        }


        if (deliveryCalendar.timeInMillis > currentTime) {
            scheduleNotification(context, workTask, "TO_DELIVERY_${workTask.id}", deliveryCalendar)
        }

    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification(
        context: Context,
        workTask: WorkTask,
        notificationId: String,
        calendar: Calendar
    ) {
        val intent = Intent(context, NotificationBroadcast::class.java)
        intent.putExtra("className", workTask.className)
        intent.putExtra("assignment", workTask.assignment)
        intent.putExtra("description", workTask.description)
        intent.putExtra("id", notificationId)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}

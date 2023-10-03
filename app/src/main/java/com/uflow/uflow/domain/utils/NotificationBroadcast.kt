package com.uflow.uflow.domain.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.uflow.uflow.R

class NotificationBroadcast: BroadcastReceiver() {

    companion object {
        const val  NOTIFICATION_ID = 4
    }

    private lateinit var className: String
    private lateinit var assignment: String
    private lateinit var description: String
    private var id: Int = NOTIFICATION_ID

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1 != null){
            className = p1.extras?.getString("className")!!
            assignment = p1.extras?.getString("assignment")!!
            description = p1.extras?.getString("description")!!
            id = p1.extras?.getInt("id")!!
        }
        createNotification(context = p0!!)
    }

    private fun createNotification(
        context: Context,
    ){
        val notification = NotificationCompat.Builder(context, NotificationUtil.CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_air_24)
            .setContentTitle("UFlow te informa tienes tarea  de $className ")
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("asignado: $assignment, descripcion: $description")
            ).build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(id, notification)
    }
}
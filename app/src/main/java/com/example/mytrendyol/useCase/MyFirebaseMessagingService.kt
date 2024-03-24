package com.example.mytrendyol.useCase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.mytrendyol.R
import com.example.mytrendyol.utils.ConstValues
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage



class MyFirebaseMessagingService:FirebaseMessagingService() {

    private fun generateNotification(title:String, message:String){
          var builder:NotificationCompat.Builder=NotificationCompat.Builder(applicationContext, ConstValues.CHANNEL_ID)
              .setSmallIcon(R.drawable.unnamed)
              .setAutoCancel(true)
              .setVibrate(longArrayOf(1000,1000,1000,1000))
              .setOnlyAlertOnce(true)

        builder=builder.setContent(getRemoteView(title,message))
        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val notificationChannel=NotificationChannel(ConstValues.CHANNEL_ID, ConstValues.CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0,builder.build())
    }


    private fun getRemoteView(title:String, message:String):RemoteViews{
        val remoteView=RemoteViews("com.example.mytrendyol", R.layout.notification_item)
        remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.description,message)
        remoteView.setImageViewResource(R.id.app_logo, R.drawable.unnamed)
        return remoteView
    }

    override fun onMessageReceived(remoteMessage:RemoteMessage) {
       if(remoteMessage.notification !=null){
           remoteMessage.notification!!.title?.let { remoteMessage.notification!!.body?.let { it1 ->
               generateNotification(it,
                   it1
               )
           } }
       }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }
}
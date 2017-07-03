package com.app.elixir.makkhankitchen.fcm;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.activity.ViewCoupons;
import com.app.elixir.makkhankitchen.activity.ViewCustomerOrderDetail;
import com.app.elixir.makkhankitchen.activity.ViewDeliveryBoyOrderDetail;
import com.app.elixir.makkhankitchen.activity.ViewOfferScreen;
import com.app.elixir.makkhankitchen.activity.ViewSplash;
import com.app.elixir.makkhankitchen.utils.CM;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final String TAG1 = "logout";
    Context context;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.

        Log.i(TAG, "onMessageReceived: From " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.i(TAG, "onMessageReceived: Message Data " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "onMessageReceived: Message Body " + remoteMessage.getNotification().getTitle());
        }

        // remoteMessage.getData().get("img_url");
        context = getApplicationContext();
        Map data = remoteMessage.getData();
        Set keys = data.keySet();
        Iterator itr = keys.iterator();
        String key;
        String value = "";
        while (itr.hasNext()) {
            key = (String) itr.next();
            value = (String) data.get(key);
            System.out.println(key + " - " + value);
        }

        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData().get("img_url"), remoteMessage.getData().get("Type"), remoteMessage.getData().get("OrderID"), remoteMessage.getData().get("userType"));
        Log.d(TAG, "From: " + remoteMessage.getFrom());


    }


    private void sendNotification(String title, String messageBody, String url, String type, String id, String userType) {
        int requestID = (int) System.currentTimeMillis();
        Intent intent = null;


        //Check Login
        if (!CM.getSp(this, "logout", "").toString().equals("1")) {

            //Check User is Customer
            if (userType.equals("customer")) {

                //Check Is Valid User Is Log In
                if (CM.getSp(this, "custLog", "").equals("1")) {

                    if (type != null && type.equals("order")) {
                        intent = new Intent(this, ViewCustomerOrderDetail.class);
                        intent.putExtra("OrderID", id);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } else if (type != null && type.equals("coupon")) {
                        intent = new Intent(this, ViewCoupons.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    } else if (type != null && type.equals("offers")) {
                        intent = new Intent(this, ViewOfferScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    } else {
                        intent = new Intent(this, ViewSplash.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }

                } else {

                    // Send to Login Screen
                    CM.setSp(this, TAG1, "1");
                    CM.setSp(this, "custLog", "");
                    CM.setSp(this, "cartId", "");
                    CM.setSp(this, "customerId", "");
                    intent = new Intent(this, ViewSplash.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                }

            } else if (userType.equals("delivery")) {   // User is Delivery Box


                //Check Is Valid User Is Log In (Delivery Boy)
                if (!CM.getSp(this, "custLog", "").equals("1")) {
                    if (type != null && type.equals("order")) {
                        intent = new Intent(this, ViewDeliveryBoyOrderDetail.class);
                        intent.putExtra("OrderID", id);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                } else {

                    // Send to Login Screen
                    CM.setSp(this, TAG1, "1");
                    CM.setSp(this, "custLog", "");
                    CM.setSp(this, "cartId", "");
                    CM.setSp(this, "customerId", "");
                    intent = new Intent(this, ViewSplash.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                }
            } else {

            }


        } else {
            intent = new Intent(this, ViewSplash.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }


        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));

        if (url != null && !url.equals("")) {
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(getBitmapFromURL(url)));
        } else {

        }


        notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));  //
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.applogo1)); //getNotificationIcon(notificationBuilder)
        } else {
            notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.applogo1));
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(requestID, notificationBuilder.build());

    }

    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = 0xFFFFFF;
            notificationBuilder.setColor(color);
            return R.drawable.applogo11;

        } else {
            return R.drawable.applogo1;
        }
    }
}

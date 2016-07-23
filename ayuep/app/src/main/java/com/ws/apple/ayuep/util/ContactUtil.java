package com.ws.apple.ayuep.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

/**
 * Created by apple on 16/7/23.
 */

public class ContactUtil {

    public static void call(Context context, String phone) {
        Intent phoneIntent = new Intent("android.intent.action.CALL",
                Uri.parse("tel:" + phone));
        context.startActivity(phoneIntent);
    }

    public static void sendMessage(Context context, String phone) {
        Uri smsToUri = Uri.parse("smsto:" + phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", "");
        context.startActivity(intent);
    }
}

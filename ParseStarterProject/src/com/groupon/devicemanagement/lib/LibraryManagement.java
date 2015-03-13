package com.groupon.devicemanagement.lib;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by vyoung on 3/11/15.
 */
public class LibraryManagement {

    private static String last_user = "";
    private static String last_device = "";
    private static boolean retVal;

    public static final String CHECKIN_STR = "Checkin";
    public static final String USER_STR = "User";
    public static final String DEVICE_STR = "Device";

    public static boolean checkIn(String user, String device) {
        if (hasCheckedOut(device)) {
            return false;
        }

        ParseObject checkinObject = new ParseObject(CHECKIN_STR);
        checkinObject.put(USER_STR, user);
        checkinObject.put(DEVICE_STR, device.toLowerCase());
        //checkinObject.save();
        checkinObject.saveInBackground();

        last_user = user;
        last_device = device;

        return true;
    }

    public static boolean hasCheckedOut(String device) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(CHECKIN_STR);
        query.whereEqualTo(DEVICE_STR, device.toLowerCase());


        List<ParseObject> parseObjects;
        try {
            query.getFirst();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}

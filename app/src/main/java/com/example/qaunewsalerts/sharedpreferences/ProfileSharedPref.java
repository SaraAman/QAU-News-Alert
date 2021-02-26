package com.example.qaunewsalerts.sharedpreferences;


import android.content.Context;

import com.google.gson.Gson;

public class ProfileSharedPref {

    public static final String CustomerDTO = "CustomerDTO";

    //*** Save ProfileDto...
//    public static void saveCustomerDto(Context context, SPSignInLoginResponse.CustomerData customerData) {
//        try {
//            String json = "";
//            if (customerData != null) {
//                json = new Gson().toJson(customerData);
//            }
//            SharedPref.set(context, CustomerDTO, json);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//   // *** Read ProfileDto...
//    public static SPSignInLoginResponse.CustomerData readProfileDto(Context context) {
//        SPSignInLoginResponse.CustomerData profileDto;
//        try {
//            String json = SharedPref.get(context, CustomerDTO, "");
//            if (!json.isEmpty()) {
//                profileDto = new Gson().fromJson(json, SPSignInLoginResponse.CustomerData.class);
//                return profileDto;
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//    public static void setProfileImage(Context context, String imageUrl){
//        SPSignInLoginResponse.CustomerData customerData = readProfileDto(context);
//        customerData.setImagePath(imageUrl);
//        saveCustomerDto(context, customerData);
//    }
//
//   public static String getProfileImage(Context context){
//        return readProfileDto(context).getImagePath();
//    }
}

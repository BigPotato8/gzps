package com.augurit.agmobile.agwater5.login.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.codehaus.jackson.map.Serializers;

import java.io.ByteArrayOutputStream;

/**
 * 包名：com.augurit.agmobile.agwater5.login.util
 * 文件描述：
 * 创建人：limeijuan
 * 创建时间：2021/8/19 9:19
 * 修改人：limeijuan
 * 修改时间：2021/8/19 9:19
 * 修改备注：
 */
public class BitmapChange {

    /**
     *  bitmap to String
     */
    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] array = baos.toByteArray();
        String result = Base64.encodeToString(array, Base64.DEFAULT);
        return result;
    }

    /**
     * String to Bitmap
     */
    public static Bitmap StringToBitmap(String base64){
        try{
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return bitmap;
        }catch (Exception e){
            Log.d("lmj", "StringToBitmap: "+e.getMessage());
            return null;
        }
    }
}

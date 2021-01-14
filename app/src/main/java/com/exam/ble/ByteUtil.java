package com.exam.ble;

import android.util.Log;

public class ByteUtil {
    private static final String TAG ="BYTE UTIL";
    public static final String byteToString(byte[] bytes){
        return new String(bytes);
    }
    public static final byte[] stringToBytes(String s){
        return s.getBytes();
    }
    public static final void logByteAskii(byte[] bytes){
        String res = "";
        for(int i = 0;i<bytes.length;i++){
            int cur = (int)bytes[i];
            res+=Integer.toBinaryString(cur)+" ";
        }
        Log.i(TAG,res);
    }
}

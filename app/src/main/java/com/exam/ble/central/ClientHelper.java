package com.exam.ble.central;

import android.widget.Button;

public class ClientHelper {
    public byte[] appendByte = new byte[3000];
    int idx= 0;
    int data=0;//2번에 걸쳐서 모으기
    private byte parseMode(String message){
        byte m = 0;//mode를 나타내는 바이트 하나
        switch (message){
            case "RUN":
                m = (byte) 0x01 ;
                break;
            case "Stop":
                m = (byte)0x02;
                break;
            case "VERSION":
                m = (byte)0x03;
                break;
            case "MESURE":
                m = (byte) 0x04;
        }
        return m;
    }
    private int parseDataSliced(byte b){//홀수일 때만 의미있는 값이겠지만 일단은 리턴
        if(idx %2 ==0){//윗부분, refresh
            data= b * 0X100;
        }
        else{
            data+=b;
        }
        appendByte[idx] = b;//일단 저장해놓자
        return data;
    }

}

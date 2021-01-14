package com.exam.ble.peripheral;

import com.exam.ble.Constants;

public class ServerHelper {
    private int data;
    private int idx;
    private int status;//status code는 constantsSERVER에 있다
    String name = "kkalchang_1";
    //숫자일 때는 인코딩 적용 안하려고 했는데 적용하는게 좋지 않을까
    //보낼 때도 잘라서..
    public ServerHelper(int d){
        idx=0;
        data= d;
    }
    public byte getDataSliced(){//0이면 윗부분 1이면 뒷부분
        return (byte) (idx%2==0?data/0x100:data);
    }

    public void setStatus(byte mode){
        status = (int)mode;
    }
    public byte[] getStatusStr(){
        String ret= ConstantsServer.retStr[status];
        if(status==ConstantsServer.MESURE){
            byte[] b = new byte[1];
            b[0] =getDataSliced();
            return b;
        }
        return ret.getBytes();
    }

}

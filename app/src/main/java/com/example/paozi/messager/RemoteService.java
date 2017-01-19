package com.example.paozi.messager;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service {

    private Messenger messenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Message toClient = Message.obtain(msg);
            if (msg.what == 0x01){
                Log.d("RemoteService", "来自客户端的消息" + msg.arg1);
                try {
                    toClient.arg2 = 222;
                    toClient.what = 0x02;
                    msg.replyTo.send(toClient);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("RemoteService", "服务绑定成功");
        return messenger.getBinder();
    }
}

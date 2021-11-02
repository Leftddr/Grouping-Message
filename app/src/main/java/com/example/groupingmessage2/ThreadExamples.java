package com.example.groupingmessage2;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import sdk.chat.core.dao.User;
import sdk.chat.core.interfaces.ThreadType;
import sdk.chat.core.session.ChatSDK;

public class ThreadExamples extends BaseExample{
    public ThreadExamples(Context context, ArrayList<User> users){
        dm.add(ChatSDK.thread().createThread("Name", users, ThreadType.PrivateGroup, "custom-id", "http://image-url").subscribe(thread -> {
            ChatSDK.ui().startChatActivityForID(context, thread.getEntityID());
            dm.add(ChatSDK.thread().sendMessageWithText("hello", thread).subscribe(() -> {

            }));
        }));
    }
}

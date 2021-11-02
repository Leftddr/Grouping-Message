package com.example.groupingmessage2;

import static sdk.chat.app.firebase.ChatSDKFirebase.quickStart;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.functions.Consumer;
import sdk.chat.app.firebase.ChatSDKFirebase;
import sdk.chat.core.dao.Message;
import sdk.chat.core.dao.User;
import sdk.chat.core.hook.Hook;
import sdk.chat.core.hook.HookEvent;
import sdk.chat.core.interfaces.ThreadType;
import sdk.chat.core.session.ChatSDK;
import sdk.chat.firebase.adapter.module.FirebaseModule;
import sdk.chat.firebase.push.FirebasePushModule;
import sdk.chat.firebase.ui.FirebaseUIModule;
import sdk.chat.firebase.upload.FirebaseUploadModule;
import sdk.chat.ui.module.UIModule;
import io.reactivex.disposables.Disposable;
import sdk.guru.common.DisposableMap;

public class MainApp extends Application {
    protected DisposableMap dm = new DisposableMap();
    @Override
    public void onCreate(){
        super.onCreate();
        try {
            quickStart(this, "pre_3", "adadad", false);
            ArrayList<User> users = new ArrayList<>();
            User currentUser = ChatSDK.core().currentUser();
            currentUser.setName("Test");
            dm.add(ChatSDK.thread().createThread(users).subscribe(thread -> {
                dm.add(ChatSDK.thread().sendMessageWithText("Hello", thread).subscribe(() -> {

                }));
            }));
        } catch (Exception e) {
            Log.i("1", "\n\n\n\n\n");
            e.printStackTrace();
            Log.i("1", "\n\n\n\n\n");
        }
        //ChatSDK.ui().startSplashScreenActivity(this);
    }
    /*
    public void sendTextMessage (String message, Thread thread) {
        Disposable d = ChatSDK.thread().sendMessageWithText(message, thread).subscribe(() -> {
            // Handle Success
        }, throwable -> {
            // Handle failure
        });
    }
    */
    public void listenForReceivedMessage () {
        // Synchronous code
        ChatSDK.hook().addHook(Hook.sync(data -> {
            Message message = (Message) data.get(HookEvent.Message);
        }), HookEvent.MessageReceived);

        // Asynchronous code
        ChatSDK.hook().addHook(Hook.async(data -> Completable.create(emitter -> {
            // ... Async code here
            emitter.onComplete();
        })), HookEvent.MessageReceived);
    }
}
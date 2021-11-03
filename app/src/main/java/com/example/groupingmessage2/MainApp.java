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
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.functions.Consumer;
import sdk.chat.app.firebase.ChatSDKFirebase;
import sdk.chat.core.dao.Thread;
import sdk.chat.core.dao.Message;
import sdk.chat.core.dao.User;
import sdk.chat.core.hook.Hook;
import sdk.chat.core.hook.HookEvent;
import sdk.chat.core.interfaces.ThreadType;
import sdk.chat.core.session.ChatSDK;
import sdk.chat.core.types.AccountDetails;
import sdk.chat.firebase.adapter.module.FirebaseModule;
import sdk.chat.firebase.push.FirebasePushModule;
import sdk.chat.firebase.ui.FirebaseUIModule;
import sdk.chat.firebase.upload.FirebaseUploadModule;
import sdk.chat.ui.module.UIModule;
import io.reactivex.disposables.Disposable;
import sdk.guru.common.DisposableMap;
//RXJava를 이용한 코드구나...
public class MainApp extends Application {
    protected DisposableMap dm = new DisposableMap();
    protected int max_num = 20, min_num = 5;
    protected Random random = new Random();
    protected ArrayList<User> users, addusers;
    protected String id[] = {"user1", "user2", "user3"};
    @Override
    public void onCreate(){
        super.onCreate();
        try {
            quickStart(this, "pre_3", "adadad", false);
            dm.add(ChatSDK.thread().createThread("Name", users, ThreadType.PrivateGroup, "custom-id", "http://image-url").subscribe(thread -> {
                //여러 유저 추가.
                for(int i = 0 ; i < id.length ; i++) {
                    User user = ChatSDK.db().fetchUserWithEntityID(id[i]);
                    addusers.add(user);
                }

                for(int i = 0 ; i < id.length ; i++) {
                    if (ChatSDK.thread().canAddUsersToThread(thread)) {
                        dm.add(ChatSDK.thread().addUsersToThread(thread, addusers[i]).subscribe(() -> {
                            Log.i("mytag", "add user success");
                            ArrayList<User> arr = new ArrayList<>();
                            arr.add(addusers[i]);
                            dm.add(ChatSDK.thread().createThread("Na", arr, ThreadType.PrivateGroup, "custom", null).subscribe(thread_ -> {
                                //메세지 보내기
                                for (int i = 0; i < 100; i++) {
                                    int rand = random.nextInt(max_num - min_num + 1) + min_num;
                                    try {
                                        java.lang.Thread.sleep(rand * 1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    sendTextMessage("hihihi", thread_);
                                }
                            }, e -> {
                                Log.e("thread_", e.toString())
                            }));
                        }));
                    }
                }
            }, e -> {
                Log.e("thread", e.toString());
            }));
            listenForReceivedMessage();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("my error", e.toString());
        }
    }

    public void sendTextMessage (String message, Thread thread) {
        Disposable d = ChatSDK.thread().sendMessageWithText(message, thread).subscribe(() -> {
            // Handle Success
        }, throwable -> {
            // Handle failure
        });
    }

    public void listenForReceivedMessage () {
        // Synchronous code
        // 여기에 broadcast Receiver 필요
        ChatSDK.hook().addHook(Hook.sync(data -> {
            Message message = (Message) data.get(HookEvent.Message);
        }), HookEvent.MessageReceived);
    }
}
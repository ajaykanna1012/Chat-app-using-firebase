package com.example.chatapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.MainActivity;
import com.example.chatapp.Models.UserModel;
import com.example.chatapp.R;
import com.example.chatapp.utils.AndroidUtil;
import com.example.chatapp.utils.FirebaseUtils;

public class Splash1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash1);
        if(getIntent().getExtras()!=null) {
            //from notification
            String userId = getIntent().getExtras().getString("userId");
            assert userId != null;
            FirebaseUtils.allUserCollectionReference().document(userId).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            UserModel model = task.getResult().toObject(UserModel.class);

                            Intent mainIntent = new Intent(this, MainActivity.class);
                            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(mainIntent);

                            Intent intent = new Intent(this, ChatActivity.class);
                            AndroidUtil.passUserModelAsIntent(intent, model);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
        }else {
            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {
                    if (FirebaseUtils.isLoggedIn()) {
                        startActivity(new Intent(Splash1Activity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(Splash1Activity.this, LoginActivity.class));
                    }
                    finish();
                }
            }, 1000);
        }
    }
}
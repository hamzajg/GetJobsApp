package com.thephoenixit.getjobsapp.views;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thephoenixit.getjobsapp.app.MyApp;
import com.thephoenixit.getjobsapp.R;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

public class SplashActivity extends AppCompatActivity {

    public void onBackPressed()
    {
        mIsBackButtonPressed = true;
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        myApp = MyApp.getInstance();
        (new Handler()).postDelayed(new Runnable() {

            public void run()
            {
                label0:
                {
                    if(!mIsBackButtonPressed)
                    {
                        if(myApp == null || !myApp.getIsLogin())
                            break label0;
                        android.content.Context context = getApplicationContext();
                        Object obj;
                        Intent intent1;
                        if(myApp.getIsJobProvider())
                            obj = JobProviderMainActivity.class;
                        else
                            obj = MainActivity.class;
                        intent1 = new Intent(context, ((Class) (obj)));
                        intent1.addFlags(FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent1);
                        finish();
                    }
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
            }

            final SplashActivity splashActivity;
            {
                splashActivity = SplashActivity.this;
            }
        }, SPLASH_DURATION);
    }

    private static final int SPLASH_DURATION = 2000;
    MyApp myApp;
    private boolean mIsBackButtonPressed;
}

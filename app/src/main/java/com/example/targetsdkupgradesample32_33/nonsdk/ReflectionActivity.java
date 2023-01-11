package com.example.targetsdkupgradesample32_33.nonsdk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.targetsdkupgradesample32_33.R;

import java.lang.reflect.Method;

public class ReflectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection);
        Activity act;

    }

    public void checkNonSdkMethods(View view) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException {

        PowerManager manager= (PowerManager) getSystemService(Context.POWER_SERVICE);;
        Method[] managerMethods = manager.getClass().getMethods();
      
        for(int i=0;i<managerMethods.length;i++)
        {
            Log.d("TAG","Method name:"+managerMethods[i].getName());
        }

        Activity activity=this;

        Method[] actMethods = activity.getClass().getMethods();

        for(int i=0;i<actMethods.length;i++)
        {
            Log.d("TAG","Method name:"+actMethods[i].getName());
        }


        /*if(method!=null)
        {
            Toast.makeText(getApplicationContext(),"Test passed",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Test Failed",Toast.LENGTH_LONG).show();
        }*/
    }
}
//Kelly Pitts 09098321
package com.example.kelly.assignment1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button[] buttons;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitButtons();
        text = (TextView) findViewById(R.id.textInput);

        //adding click event listener to each button in the array
        for (final Button b: buttons) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if regular button pressed (numbers)
                    if(!b.getText().equals("Del") && !b.getText().equals("Call")) {
                        //add numbers to input box
                        text.append(b.getText());
                    } else if (b.getText().equals("Call")) {
                        //add permissions and make phone call
                        RequestPermissions();
                    } else if (b.getText().equals("Del")) {
                        //delete numbers from input box only if there are numbers to delete
                        String temp = text.getText().toString();
                        if (temp.length() > 0) {
                            text.setText(temp.substring(0, temp.length() - 1));
                        }
                    }
                }
            });
        }
    }

    private void InitButtons() {
        //creating an array of all the clickable buttons
        buttons = new Button[] {
                (Button) findViewById(R.id.button0),
                (Button) findViewById(R.id.button1),
                (Button) findViewById(R.id.button2),
                (Button) findViewById(R.id.button3),
                (Button) findViewById(R.id.button4),
                (Button) findViewById(R.id.button5),
                (Button) findViewById(R.id.button6),
                (Button) findViewById(R.id.button7),
                (Button) findViewById(R.id.button8),
                (Button) findViewById(R.id.button9),
                (Button) findViewById(R.id.buttonStar),
                (Button) findViewById(R.id.buttonHash),
                (Button) findViewById(R.id.buttonCall),
                (Button) findViewById(R.id.buttonDel)
        };
    }

    private void RequestPermissions() {
        //get phone number from input box
        String phone = text.getText().toString();

        //only needed if sdk version is greater than 22
        if(Build.VERSION.SDK_INT > 22) {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                //if permission has been granted to make call, start phone call
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                startActivity(intent);
            } else {
                //else request the permission to make phone call
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        } else {
            //if sdk version is 22 or less we do not need permission to make phone call
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1) {
            String phone = text.getText().toString();
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        }
    }
}

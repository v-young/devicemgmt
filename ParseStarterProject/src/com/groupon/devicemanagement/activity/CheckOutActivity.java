package com.groupon.devicemanagement.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.groupon.devicemanagement.lib.LibraryManagement;
import com.parse.ParseAnalytics;
import com.parse.starter.R;

public class CheckOutActivity extends Activity {
    TextView txtDevice;
    TextView txtUser;
    Button btnCheckIn;

    final Handler mHandler = new Handler();
    private String mToastMessage = "";

    // Create runnable for posting
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            Toast.makeText(getApplicationContext(), mToastMessage,
                    Toast.LENGTH_LONG).show();
        }
    };

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivity_checkout);

		ParseAnalytics.trackAppOpenedInBackground(getIntent());

        txtDevice = (TextView) findViewById(R.id.labelDevice);
        txtUser = (TextView) findViewById(R.id.labelUser);

        Button add = (Button) findViewById(R.id.btnShow);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(CheckOutActivity.this);
                integrator.addExtra("PROMPT_MESSAGE", "Scan device or user QR code!");

                integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
            }
        });
        btnCheckIn = (Button) findViewById(R.id.btnCheckIn);
        btnCheckIn.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View v) {
                  boolean retVal = LibraryManagement.checkIn(txtUser.getText().toString().toLowerCase(), txtDevice.getText().toString().toLowerCase());

                  mToastMessage = (retVal == true)? "Done!": "Already checked out!";
                  mHandler.post(mUpdateResults);

                  if (retVal) {
                      btnCheckIn.setVisibility(View.GONE);
                  }
              }
        });

        // Start scanning
        IntentIntegrator integrator = new IntentIntegrator(CheckOutActivity.this);
        integrator.addExtra("PROMPT_MESSAGE", "Scan device or user QR code!");

        integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
	}


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (result == null || resultCode == 0) {
            return;
        }

        String contents = result.getContents();
        updateContent(contents);


        if (!readyToCheckin()) {
            IntentIntegrator integrator = new IntentIntegrator(CheckOutActivity.this);
            integrator.addExtra("PROMPT_MESSAGE", "Scan device or user QR code!");

            integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
        }
        else {
            boolean retVal = LibraryManagement.checkIn(txtUser.getText().toString().toLowerCase(), txtDevice.getText().toString().toLowerCase());

            mToastMessage = (retVal == true)? "Done!": "Already checked out!";
            mHandler.post(mUpdateResults);
        }
    }

    private boolean readyToCheckin() {
        return (txtUser.getText() != null && !txtUser.getText().equals("") &&
                txtDevice.getText() != null && !txtDevice.getText().equals(""));
    }

    private void updateContent(String contents) {

        if (contents != null) {
            if (contents.toLowerCase().endsWith("@groupon.com")) {
                txtUser.setText(contents);
            } else {
                txtDevice.setText(contents);
            }

            if (readyToCheckin()) {
                boolean retVal = LibraryManagement.checkIn(txtUser.getText().toString().toLowerCase(), txtDevice.getText().toString().toLowerCase());

                mToastMessage = (retVal == true)? "Done!": "Already checked out!";
                mHandler.post(mUpdateResults);

                if (retVal) {
                    btnCheckIn.setVisibility(View.GONE);
                }
            }

        } else {
            mToastMessage = "Failed!";
            mHandler.post(mUpdateResults);
        }
    }
}

/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.cardreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.common.logger.Log;
import com.reimaginebanking.api.java.Constants.TransactionMedium;
import com.reimaginebanking.api.java.Constants.TransactionType;
import com.reimaginebanking.api.java.NessieClient;
import com.reimaginebanking.api.java.NessieException;
import com.reimaginebanking.api.java.NessieResultsListener;
import com.reimaginebanking.api.java.models.Customer;
import com.reimaginebanking.api.java.models.Transfer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.Path;

/**
 * Generic UI for sample discovery.
 */
public class CardReaderFragment extends Fragment implements LoyaltyCardReader.AccountCallback {


    NessieClient nessieClient = NessieClient.getInstance();
    String customerid="55e94a6af8d8770528e60bdb";
    String senderaccountid="55e94a6cf8d8770528e61b85";



    public static final String TAG = "CardReaderFragment";
    // Recommend NfcAdapter flags for reading from other Android devices. Indicates that this
    // activity is interested in NFC-A devices (including other Android devices), and that the
    // system should not check for the presence of NDEF-formatted data (e.g. Android Beam).
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
    public LoyaltyCardReader mLoyaltyCardReader;
    private TextView mAccountField;
    private Button button;
    private EditText meditText;
    /** Called when sample is created. Displays generic UI with welcome text. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nessieClient.setAPIKey("fb4c5a581daa1b85454310e4993d7f56");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        if (v != null) {
            mAccountField = (TextView) v.findViewById(R.id.card_account_field);


            mAccountField.setText("Waiting...");

            meditText=(EditText) v.findViewById(R.id.editText);
            mLoyaltyCardReader = new LoyaltyCardReader(this);

            // Disable Android Beam and register our card reader callback
            enableReaderMode();
        }
        button= (Button) v.findViewById(R.id.button2);


        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        disableReaderMode();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableReaderMode();
    }

    private void enableReaderMode() {
        Log.i(TAG, "Enabling reader mode");
        Activity activity = getActivity();
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {
            nfc.enableReaderMode(activity, mLoyaltyCardReader, READER_FLAGS, null);
        }
    }

    private void disableReaderMode() {
        Log.i(TAG, "Disabling reader mode");
        Activity activity = getActivity();
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {
            nfc.disableReaderMode(activity);
        }
    }

    @Override
    public void onAccountReceived(final String account) {
        // This callback is run on a background thread, but updates to UI elements must be performed
        // on the UI thread.
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //  mAccountField.setText(account);
                nessieClient.getAccountCustomer(account, new NessieResultsListener() {
                    @Override
                    public void onSuccess(Object result, NessieException e) {
                        if (e == null) {
                            //There is no error, do whatever you need here.
                            // Cast the result object to the type that you are requesting and you are good to go
                            Customer customer = (Customer) result;
                            mAccountField.setText(customer.getFirst_name() + " " + customer.getLast_name());
                        } else {
                            //There was an error. Handle it here
                            Log.e("Error", e.toString());
                            mAccountField.setText(account);
                        }
                    }
                });



            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Hey", "Hey");
                //send request
                double amount=Double.parseDouble(meditText.getText().toString());

                Transfer transfer = new Transfer.Builder()
                        .amount(amount)
                        .description("description")
                        .medium(TransactionMedium.BALANCE)
                        .payee_id(account)
                        .status("completed")
                        .build();

              //Transfer transfer=new Transfer("","string","pending",TransactionType.DEPOSIT,TransactionMedium.BALANCE,senderaccountid,account,amount,"string");

                nessieClient.createTransfer(senderaccountid, transfer, new NessieResultsListener() {
                    @Override
                    public void onSuccess(Object result, NessieException e) {
                        if (e == null) {
                            //There is no error, do whatever you need here.
                            // Cast the result object to the type that you are requesting and you are good to go
                        //    Toast.makeText(getContext(),"Transcation sent",Toast.LENGTH_LONG);
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setTitle("Transaction Successful");
                            alert.setMessage("Your tip was sent to your street artist.");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                        } else {
                            //There was an error. Handle it here
                            Log.e("Error", e.toString());
                        }
                    }
                });

            }
        });

    }
}

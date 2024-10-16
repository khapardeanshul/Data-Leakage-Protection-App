package com.example.datalekage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.Executor;

public class HomeFragment extends Fragment {

    private TextView textView;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private Executor executor;

    View v;
    Button home;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        home = v.findViewById(R.id.btn1);
        textView = v.findViewById(R.id.textView);
        executor = ContextCompat.getMainExecutor(getContext());

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BiometricManager biometricManager = BiometricManager.from(getContext());
                if (biometricManager.canAuthenticate() != BiometricManager.BIOMETRIC_SUCCESS){

                    textView.setText("Biometric Not Supported");
                    return;
                }
                biometricPrompt.authenticate(promptInfo);
            }
        });

        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                textView.setText("Error");
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                textView.setText("Success");
               // startActivity(new Intent(getApplicationContext(),DataActivity.class));
                Fragment fragment = new NotesFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, fragment);
                ft.commit();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

                textView.setText("Failure");
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("File Access Authentication")
                .setNegativeButtonText("Cancel/ Use Password")
                .setConfirmationRequired(false)
                .build();


        return v;
    }



}
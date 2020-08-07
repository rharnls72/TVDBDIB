package com.web.curation.service;

import java.io.FileInputStream;

import com.google.auth.oauth2.GoogleCredentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
@Service
public class FirebaseInitialize {
    @PostConstruct
    public void initialize(){
        try {
            FileInputStream serviceAccount = new FileInputStream("/tvility/serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://tvility.firebaseio.com")
            .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
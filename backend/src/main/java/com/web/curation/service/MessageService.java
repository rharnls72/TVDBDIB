package com.web.curation.service;

import com.web.curation.dao.alert.AlertDao;
import com.web.curation.dao.message.MessageDao;
import com.web.curation.model.alert.Alert;

import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.DocumentSnapshot;

import org.springframework.beans.factory.annotation.Autowired;
import com.google.cloud.firestore.DocumentReference;

@Service
public class MessageService {
    @Autowired
    MessageDao messageDao;
    
    //채팅룸 삭제
    public boolean deleteChatroom(String cno){
        try {
            Firestore db = FirestoreClient.getFirestore();
            db.collection("chat_room").document(cno).delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    

}
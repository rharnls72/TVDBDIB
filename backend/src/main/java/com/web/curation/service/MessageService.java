package com.web.curation.service;

import org.springframework.stereotype.Service;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.web.curation.model.message.Message;

@Service
public class MessageService {
    //메시지 전송
    public boolean sendMessage(Message msg){
        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference addedDocRef = db.collection("message").document();
            msg.setMno(addedDocRef.getId());
            addedDocRef.set(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

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

    //메시지 삭제
    public boolean deleteMessage(String mno){
        try {
            Firestore db = FirestoreClient.getFirestore();
            db.collection("message").document(mno).delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    

}
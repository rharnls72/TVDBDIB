package com.web.curation.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.web.curation.model.message.ChatRoom;
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

    //채팅룸 생성
    public String createChatRoom(ChatRoom room){
        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference addedDocRef = db.collection("chat_room").document();
            room.setCno(addedDocRef.getId());
            addedDocRef.set(room);
            return room.getCno();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //채팅룸 조회
    public ChatRoom getChatRoom(String usersToString){
        try {
            Firestore db = FirestoreClient.getFirestore();
            List<QueryDocumentSnapshot> documents = db.collection("chat_room").whereEqualTo("usersToString", usersToString).get().get().getDocuments();
            if(documents.size()>0)
                return documents.get(0).toObject(ChatRoom.class);
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //채팅룸 삭제
    public boolean deleteChatroom(String cno){
        try {
            Firestore db = FirestoreClient.getFirestore();
            db.collection("chat_room").document(cno).delete();
            List<QueryDocumentSnapshot> documents = db.collection("message").whereEqualTo("cno", cno).get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                db.collection("message").document(document.getId()).delete();
            }
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
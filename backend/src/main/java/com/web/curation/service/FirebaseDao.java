package com.web.curation.service;

import com.web.curation.dao.alert.AlertDao;
import com.web.curation.model.alert.Alert;
import com.web.curation.model.following.UserFollowing;

import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.DocumentSnapshot;

import org.springframework.beans.factory.annotation.Autowired;
import com.google.cloud.firestore.DocumentReference;

@Service
public class FirebaseDao {
    @Autowired
    AlertDao dao;
    //추가
    public boolean addAlert(Alert alert){
        try {
            Firestore db = FirestoreClient.getFirestore();
            int result=0;
            switch(alert.getCtype()){
                case 1:
                result = dao.getWriterUno("feed", "fno", alert.getCno());
                break;
                case 2:
                result = dao.getWriterUno("feed_reply", "frno", alert.getCno());
                break;
                case 3:
                result = dao.getWriterUno("program_reply", "prno", alert.getCno());
                break;
                case 4:
                result = dao.getWriterUno("episode_reply", "erno", alert.getCno());
            }
            alert.setUno(result);
            Alert newAlert = dao.getInfoByUser(alert.getSubject_no());
            if(newAlert.getPicture()!=null)
                alert.setPicture(newAlert.getPicture());
            alert.setSubject_name(newAlert.getSubject_name());

            DocumentReference addedDocRef = db.collection("alert").document();
            alert.setAno(addedDocRef.getId());
            addedDocRef.set(alert);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //팔로우 요청 추가
    public String addFollowing(Alert alert){
        try {
            Alert newAlert = dao.getInfoByUser(alert.getSubject_no());
            if(newAlert.getPicture()!=null)
                alert.setPicture(newAlert.getPicture());
            alert.setSubject_name(newAlert.getSubject_name());

            Firestore db = FirestoreClient.getFirestore();
            DocumentReference addedDocRef = db.collection("follow_request").document();
            alert.setAno(addedDocRef.getId());
            addedDocRef.set(alert);
            return alert.getAno();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //팔로우 요청 취소
    public boolean cancelFollowing(UserFollowing uf){
        try {
            Firestore db = FirestoreClient.getFirestore();
            String id = db.collection("follow_request").whereEqualTo("subject_no", uf.getFollower()).whereEqualTo("uno", uf.getFollowing()).get().get().getDocuments().get(0).getId();
            db.collection("follow_request").document(id).delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //팔로우 요청삭제
    public boolean deleteFollowing(String ano){
        try {
            Firestore db = FirestoreClient.getFirestore();
            db.collection("follow_request").document(ano).delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //상세
    public Alert getAlertDetail(String ano){
        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference documentReference = db.collection("alert").document(ano);
            ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
            DocumentSnapshot documentSnapshot = apiFuture.get();
            Alert alert = null;
            if(documentSnapshot.exists()){
                alert = documentSnapshot.toObject(Alert.class);
                return alert;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //수정
    public boolean updateAlert(Alert alert){
        try {
            Firestore db = FirestoreClient.getFirestore();
            db.collection("alert").document(alert.getAno()).set(alert);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //모든 알림 삭제
    public boolean deleteAllAlert(int uno){
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> query = db.collection("alert").whereEqualTo("uno", uno).get();
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                db.collection("alert").document(document.getId()).delete();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //삭제
    public boolean deleteAlert(String ano){
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = db.collection("alert").document(ano).delete();
            System.out.println("Update time : " + writeResult.get().getUpdateTime());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //리스트
    public List<Alert> getAlertList(){
        List<Alert> alist = new ArrayList<>();
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> query = db.collection("alert").get();
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            
            for (QueryDocumentSnapshot document : documents) {
                alist.add(document.toObject(Alert.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alist;
    }
    //리스트 - 조건
    public List<Alert> getUserAlert(int uno){
        List<Alert> alist = new ArrayList<>();
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> query = db.collection("alert").whereEqualTo("uno", uno).get();
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                alist.add(document.toObject(Alert.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alist;
    }
    //모든 알림 읽음
    public boolean setReadAllAlert(int uno){
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> query = db.collection("alert").whereEqualTo("uno", uno).get();
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                db.collection("alert").document(document.getId()).update("read", true);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //특정 알림 읽음
    public boolean setReadAlert(String ano){
        try {
            Firestore db = FirestoreClient.getFirestore();
            db.collection("alert").document(ano).update("read", true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
package com.danieldk.brewu.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.danieldk.brewu.Models.Brew;
import com.danieldk.brewu.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class BrewViewModel extends ViewModel {

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private User user;
    private List<Brew> brews;

    public void getBrews(){
        db.collection("Brews").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                List<Brew> newBrews;
                if (e != null) {
                    Log.w("TAG", "listen:error", e);
                    return;
                }

                if (value != null) {
                    brews = value.toObjects(Brew.class);

                    for(Brew brew : brews){
                        Log.d("Brew", brew.toString());
                    }
                }
            }
        });
    };

    public void createBrew(Brew brew) {
        db.collection("Brews").document()
                .set(brew)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Brew", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Brew", "Error writing document", e);
                    }
                });
    }
}

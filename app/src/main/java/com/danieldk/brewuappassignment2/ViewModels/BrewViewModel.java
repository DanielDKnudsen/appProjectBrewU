package com.danieldk.brewuappassignment2.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.danieldk.brewuappassignment2.BrewService;
import com.danieldk.brewuappassignment2.Models.Brew;
import com.danieldk.brewuappassignment2.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.annotation.Nullable;

public class BrewViewModel extends ViewModel {
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private User user;
    private MutableLiveData<List<Brew>> allBrews;
    private MutableLiveData<List<Brew>> myBrews;

    public void loadAllBrews(){
        allBrews = new MutableLiveData<>();
        db.collection("Brews").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("TAG", "listen:error", e);
                    return;
                }
                if (value != null) {
                    int i = 0;
                    List<Brew> list;
                    list = value.toObjects(Brew.class);
                    for (QueryDocumentSnapshot document : value) {
                        list.get(i).setId(document.getId());
                        i++;
                    }
                    allBrews.setValue(list);
                }
            }
        });
    };

    public void loadMyBrews(){
        myBrews = new MutableLiveData<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("Brews")
                .whereEqualTo("userId", user.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    task.getResult();
                    myBrews.setValue(task.getResult().toObjects(Brew.class));
                } else {
                    Log.d("MyBrews", "Error getting documents: ", task.getException());
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

    public MutableLiveData<List<Brew>> getAllBrews()
    {
        return allBrews;
    }
    public MutableLiveData<List<Brew>> getMyBrews() {
        return myBrews;
    }

    public void UpdateBrew(Brew brew) {
        db.collection("Brews")
                .document(brew.getId())
                .set(brew)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Brew", "Set new brew");
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

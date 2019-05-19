package com.danieldk.brewuappassignment2.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.danieldk.brewuappassignment2.Models.Brew;
import com.danieldk.brewuappassignment2.Models.Step;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.annotation.Nullable;

public class BrewViewModel extends ViewModel {
    // inspiration from https://developer.android.com/topic/libraries/architecture/viewmodel
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<List<Brew>> allBrews;
    private MutableLiveData<List<Brew>> myBrews;
    private MutableLiveData<List<Step>> steps;

    public void loadAllBrews(){
        // looked at documentation https://firebase.google.com/docs/firestore/query-data/listen
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
                    allBrews.setValue(value.toObjects(Brew.class));
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
                    myBrews.setValue(task.getResult().toObjects(Brew.class));
                } else {
                    Log.d("MyBrews", "Error getting documents: ", task.getException());
                }
            }
        });
    };

    public void createBrew(Brew brew, List<Step> steps) {
        db.collection("Brews")
                .add(brew)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        brew.setId( documentReference.getId());
                        db.collection("Brews").document(brew.getId()).set(brew);
                        for (Step step: steps
                             ) {
                            step.setBrewId(brew.getId());
                            db.collection("Steps").add(step);
                        }
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
    public MutableLiveData<List<Step>> getSteps() {
        return steps;
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

    public void loadSteps(String brewId) {
        steps = new MutableLiveData<>();
        db.collection("Steps")
                .whereEqualTo("brewId", brewId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Step> test = task.getResult().toObjects(Step.class);
                    steps.setValue(task.getResult().toObjects(Step.class));
                } else {
                    Log.d("Steps", "Error getting document: ", task.getException());
                }
            }
        });
    }
}

package com.example.capstone.Fragment;

import static android.app.Activity.RESULT_OK;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.capstone.Model.Student;
import com.example.capstone.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.UUID;

public class ChangePersonalDetails extends Fragment {
    private ImageView ivChangeProfilePicture;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Button btnSavePersonalDetails,btnGetImage;
    private DatabaseReference reference;
    private EditText edtChangeFirstName,edtChangeLastName;
    private String userID;
    private FirebaseUser student;
    private FirebaseAuth mAuth;
    private boolean isconnected;

    public ChangePersonalDetails() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootview=inflater.inflate(R.layout.fragment_change_personal_details, container, false);

        student = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Students");
        userID=student.getUid();

        ivChangeProfilePicture=rootview.findViewById(R.id.ivChangeProfilePicture);
        btnSavePersonalDetails=rootview.findViewById(R.id.btnSavePersonalDetails);
        btnGetImage=rootview.findViewById(R.id.btnGetImage);

        edtChangeFirstName=rootview.findViewById(R.id.edtChangeFirstName);
        edtChangeLastName=rootview.findViewById(R.id.edtChangeLastName);

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        loadImage();
        loadFireBaseData();


        btnGetImage.setOnClickListener(v -> {
            choosePicture();
        });

        btnSavePersonalDetails.setOnClickListener(v -> {
            ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                isconnected = true;
            }
            else{
                isconnected = false;
            }

            if(isconnected){
                if(imageUri!=null){
                    uploadPicture();
                    updateFirebaseName();
                }else{
                    updateFirebaseName();
                    Snackbar.make(getActivity().findViewById(android.R.id.content),"Personal information updated successfully.",Snackbar.LENGTH_LONG).show();
                    requireActivity().onBackPressed();
                }
            }else{
                Snackbar.make(getActivity().findViewById(android.R.id.content),"Please connect to Internet.",Snackbar.LENGTH_LONG).show();
            }

        });

       return rootview;
    }

    public void loadImage(){

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student studentProfile= snapshot.getValue(Student.class);
                String spicture=studentProfile.getSpicture();
                if(spicture!=null){
                    Glide.with(getActivity()).load(spicture).into(ivChangeProfilePicture);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void loadFireBaseData() {

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student studentProfile= snapshot.getValue(Student.class);
                String sfname=studentProfile.getSFname();
                String slname=studentProfile.getSLname();
                edtChangeFirstName.setText(sfname);
                edtChangeLastName.setText(slname);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK&&data!=null){
            imageUri=data.getData();
            ivChangeProfilePicture.setImageURI(imageUri);
        }
    }

    private void uploadPicture() {

        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randomKey= UUID.randomUUID().toString();

        StorageReference storageRef = storageReference.child("images/"+randomKey);

        storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mAuth=FirebaseAuth.getInstance();
                        String userID= mAuth.getCurrentUser().getUid();
                        reference.child(userID).child("spicture").setValue(uri.toString());
                        pd.dismiss();
                        Snackbar.make(getActivity().findViewById(android.R.id.content),"Personal information updated successfully.",Snackbar.LENGTH_LONG).show();
                        requireActivity().onBackPressed();
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getContext(), "Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent=(100.00*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                pd.setMessage("Progress: "+(int) progressPercent+"%");

            }
        });
    }

    public void updateFirebaseName(){
        String lastname = edtChangeLastName.getText().toString();
        String firstname = edtChangeFirstName.getText().toString();

        if(lastname.isEmpty()){
            edtChangeLastName.setError("Last name is required.");
            edtChangeLastName.requestFocus();
            return;
        }
        if(firstname.isEmpty()){
            edtChangeLastName.setError("Last name is required.");
            edtChangeLastName.requestFocus();
            return;
        }


        mAuth=FirebaseAuth.getInstance();
        String userID= mAuth.getCurrentUser().getUid();
        reference.child(userID).child("slname").setValue(edtChangeLastName.getText().toString());
        reference.child(userID).child("sfname").setValue(edtChangeFirstName.getText().toString());

    }

}
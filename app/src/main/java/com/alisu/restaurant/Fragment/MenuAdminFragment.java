package com.alisu.restaurant.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alisu.restaurant.Adapter.MenuAdapter;
import com.alisu.restaurant.Adapter.MenuAdminAdapter;
import com.alisu.restaurant.R;
import com.alisu.restaurant.model.Menu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class MenuAdminFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton fabAdd;

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage storage;
    StorageReference storageReference;
    ArrayList<Menu> list = new ArrayList<>();
    MenuAdminAdapter adapter;

    TextView etName,etDesc,etPrice,etFoodId;
    Button btnUpload,btnSelect;
    String name;
    String desc;
    int price;
    String foodId;
    Menu newMenu = new Menu();
    Menu menu = new Menu();
    Menu updateMenu = new Menu();
    Uri saveUri;
    private final int PICK_IMAGE_REQUEST = 71;
     String KEY ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_menu,container,false);

        //firebase
        database = FirebaseDatabase.getInstance();
        reference= database.getReference("restaurant");
        storage =FirebaseStorage.getInstance();
        storageReference = storage.getReference("/menu");

        //init
        recyclerView = view.findViewById(R.id.rv_menu_admin);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        fabAdd = view.findViewById(R.id.fab_menu);


        list = new ArrayList<Menu>();
        reference = FirebaseDatabase.getInstance().getReference().child("restaurant");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    menu = dataSnapshot1.getValue(Menu.class);
                    list.add(menu);

                }
                adapter = new MenuAdminAdapter(list,getContext());
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickCallback(new MenuAdminAdapter.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(Menu menu) {
                        KEY = menu.getNama();
                        updateMenu = new Menu(menu.getNama(),menu.getDescription(),menu.getUrl(),menu.getId(),menu.getHarga());

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFoodDialog();

            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!=null)
        {
            saveUri = data.getData();
            btnSelect.setText("Image selected!");

        }
    }

    private void showAddFoodDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Add Menu");
        alertDialog.setMessage("Please fill full information!");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_new_food = inflater.inflate(R.layout.add_new_food,null);
        etName =add_new_food.findViewById(R.id.etName);
        etDesc =add_new_food.findViewById(R.id.etDesc);
        etPrice =add_new_food.findViewById(R.id.etharga);
        etFoodId =add_new_food.findViewById(R.id.etFoodId);

        btnSelect = add_new_food.findViewById(R.id.btn_select);
        btnUpload = add_new_food.findViewById(R.id.btn_upload);

        //event for button
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        alertDialog.setView(add_new_food);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                if (newMenu != null){

                    final String KEY = newMenu.getNama();
                    reference.child(KEY).setValue(newMenu);
                    list.clear();
                }
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();

    }


    private void uploadImage() {
        if (saveUri != null){
            final ProgressDialog mDialog = new ProgressDialog(getContext());
            mDialog.setMessage("Uploading .... ");
            mDialog.show();

            String imageName = UUID.randomUUID().toString() ;
            final StorageReference imageFolder = storageReference.child("menu/"+imageName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //setValue
                                    name = etName.getText().toString();
                                    desc = etDesc.getText().toString();
                                    foodId = etFoodId.getText().toString();
                                    price = Integer.parseInt(etPrice.getText().toString());
                                    newMenu = new Menu(name,desc,uri.toString(),foodId,price);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(getContext(), "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("Uploaded "+progress+"%");
                        }
                    });

        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {


        if (item.getTitle().equals("Update")){
            showUpdateDialog(KEY,updateMenu);


        }

        else if(item.getTitle().equals("Delete")){

            reference.child(KEY).removeValue();
            list.clear();



        }

        return super.onContextItemSelected(item);
    }


    private void showUpdateDialog(final String Key, final Menu menu) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Update Menu");
        alertDialog.setMessage("Please fill full information!");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_new_food = inflater.inflate(R.layout.add_new_food,null);
        etName =add_new_food.findViewById(R.id.etName);
        etDesc =add_new_food.findViewById(R.id.etDesc);
        etPrice =add_new_food.findViewById(R.id.etharga);
        etFoodId =add_new_food.findViewById(R.id.etFoodId);

        btnSelect = add_new_food.findViewById(R.id.btn_select);
        btnUpload = add_new_food.findViewById(R.id.btn_upload);

        //set default edit text
        etName.setText(menu.getNama());
        etDesc.setText(menu.getDescription());
        etPrice.setText(String.valueOf(menu.getHarga()));
        etFoodId.setText(menu.getId());

        //event for button
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
                recyclerView.clearOnChildAttachStateChangeListeners();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               changeImage(menu);
            }
        });

        alertDialog.setView(add_new_food);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

                //updated information
                menu.setNama(etName.getText().toString());
                menu.setDescription(etDesc.getText().toString());
                menu.setId(etFoodId.getText().toString());
                menu.setHarga(Integer.valueOf(etPrice.getText().toString()));

                reference.child(Key).setValue(menu);
                list.clear();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    private void changeImage(final Menu menu) {
        if (saveUri != null){
            final ProgressDialog mDialog = new ProgressDialog(getContext());
            mDialog.setMessage("Uploading .... ");
            mDialog.show();

            String imageName = UUID.randomUUID().toString() ;
            final StorageReference imageFolder = storageReference.child("menu/"+imageName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //setValue
                                    menu.setUrl(uri.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(getContext(), "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("Uploaded "+progress+"%");
                        }
                    });

        }
    }

}

package com.warehousenesia.id;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class RequestProduct extends AppCompatActivity implements View.OnClickListener {

    private static final String UPLOAD_URL = "http://192.168.100.17:1997/product/addrequestproduct";
    private static final int IMAGE_REQUEST_CODE = 3;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private ImageView imageView;
    private EditText pro_name, description, category;
    private TextView tvPath;
    private Button btnUpload, upl_img;
    private Bitmap bitmap;
    private Uri filePath;
    SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_product);

        imageView = findViewById(R.id.img);
        btnUpload = findViewById(R.id.add);
        upl_img = findViewById(R.id.chooseImage);
        tvPath    = findViewById(R.id.path);

        sharedPrefManager = new SharedPrefManager(getApplication());
        requestStoragePermission();

        btnUpload.setOnClickListener(this);
        upl_img.setOnClickListener(this);

    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    public void onClick(View view){
        if (view == upl_img){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_REQUEST_CODE);
        }else if (view == btnUpload){
            uploadMultipart();
        }
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                tvPath.setText("Path: ".concat(getPath(filePath)));
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private void uploadMultipart() {

        final Spinner spinner = findViewById(R.id.country);
        String spin = spinner.getSelectedItem().toString();

        String id;
        if (spin.equals("Japan")){
            id = "1";
        }else if (spin.equals("Hongkong")){
            id ="2";
        }else if (spin.equals("Australia")){
            id = "3";
        }else if (spin.equals("Malaysia")){
            id = "4";
        }else if (spin.equals("Singapore")){
            id = "5";
        }else if (spin.equals("Korea Selatan")){
            id = "6";
        }else if (spin.equals("Thailand")){
            id = "7";
        }else {
            id = "0";
        }

        String akhir = id;

        pro_name = findViewById(R.id.nama_barang);
        description = findViewById(R.id.deskripsi);
        category = findViewById(R.id.kategori);

        String nama_barang = pro_name.getText().toString();
        String deskripsi = description.getText().toString();
        String kategori = category.getText().toString();

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        JSONObject konfirm = new JSONObject();
        try {

            konfirm.put("id_user", sharedPrefManager.getSpIdmember());
            konfirm.put("nama_barang", nama_barang);
            konfirm.put("deskripsi", deskripsi);
            konfirm.put("kategori", kategori);
            konfirm.put("id_negara", akhir);

            String uploadId = UUID.randomUUID().toString();
            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                    .addFileToUpload(path, "gambar") //Adding file
                    .addParameter("data", konfirm.toString())
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            Toast.makeText(getApplication(), "Request Successful", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

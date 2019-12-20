package com.warehousenesia.id;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {
    SharedPrefManager sharedPrefManager;
    private EditText username, telpon, email, fullname, company;
    private CircleImageView circleImageView;
    private TextView tvPath;
    private Button btn;
    private static final int IMAGE_REQUEST_CODE = 3;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPrefManager = new SharedPrefManager(getApplication());

        tvPath    = findViewById(R.id.path);

        username = findViewById(R.id.nama_lengkap);
        username.setText(sharedPrefManager.getSPNama());

        telpon = findViewById(R.id.no_telpon);
        telpon.setText(sharedPrefManager.getSP_PhoneNumber());

        email = findViewById(R.id.email);
        email.setText(sharedPrefManager.getSP_Email());

        fullname = findViewById(R.id.full_name);
        fullname.setText(sharedPrefManager.getSP_FullName());

        company = findViewById(R.id.company_name);
        company.setText(sharedPrefManager.getSP_CompanyName());

        circleImageView = findViewById(R.id.img);
        Glide.with(this)
                .load(sharedPrefManager.getSP_GambarUser())
                .placeholder(R.drawable.account)
                .into(circleImageView);

        btn = findViewById(R.id.btnUpload);

        circleImageView.setOnClickListener(this);
        btn.setOnClickListener(this);
        requestStoragePermission();

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

    @Override
    public void onClick(View view) {
        if (view == circleImageView){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_REQUEST_CODE);
        }else if (view == btn){
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
                circleImageView.setImageBitmap(bitmap);
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

        username = findViewById(R.id.nama_lengkap);
        String usernames = username.getText().toString();
        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, usernames);

        telpon = findViewById(R.id.no_telpon);
        String telpons = telpon.getText().toString();
        sharedPrefManager.saveSPString(SharedPrefManager.SP_PhoneNumber, telpons);

        email = findViewById(R.id.email);
        String emails = email.getText().toString();
        sharedPrefManager.saveSPString(SharedPrefManager.SP_Email, emails);

        fullname = findViewById(R.id.full_name);
        String fullnames = fullname.getText().toString();
        sharedPrefManager.saveSPString(SharedPrefManager.SP_FullName, fullnames);

        company = findViewById(R.id.company_name);
        String companys = company.getText().toString();
        sharedPrefManager.saveSPString(SharedPrefManager.SP_CompanyName, companys);

        //getting the actual path of the image
        String path = getPath(filePath);

        circleImageView = findViewById(R.id.img);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_GambarUser, path);

        String id = sharedPrefManager.getSpIdmember();

        String newurl = "http://192.168.100.12:1997/auth/editProfile/"+id;

        //Uploading code
        JSONObject user = new JSONObject();
        JSONObject agents = new JSONObject();
        try {

            user.put("username", usernames);
            user.put("phone", telpons);
            user.put("email", emails);

            agents.put("fullname", fullnames);
            agents.put("companyName", companys);

            String uploadId = UUID.randomUUID().toString();
            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, newurl)
                    .addFileToUpload(path, "gambar") //Adding file
                    .addParameter("data", user.toString())
                    .addParameter("kotak", agents.toString())
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(3)
                    .startUpload(); //Starting the upload
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            Toast.makeText(getApplication(), "Edit Profile Successful", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}

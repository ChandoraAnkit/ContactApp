package com.testapp.chandora.androidy.contactApp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;


import com.testapp.chandora.androidy.contactApp.R;
import com.testapp.chandora.androidy.contactApp.data.model.Contact;
import com.testapp.chandora.androidy.contactApp.utils.PermissionUtils;

import butterknife.ButterKnife;

import static com.testapp.chandora.androidy.contactApp.utils.PermissionUtils.checkPermissions;


public class MainActivity extends AppCompatActivity implements
        ContactsListFragment.OnContactClickListener {

    public static int REQUEST_READ_CONTACTS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (!checkPermissions(this, Manifest.permission.READ_CONTACTS)){
            PermissionUtils.requestPermission(this, Manifest.permission.READ_CONTACTS, REQUEST_READ_CONTACTS);
        }else {
            addContactFragment();
        }

    }

    private void addContactFragment(){

        ContactsListFragment contactsListFragment = ContactsListFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, contactsListFragment)
                .commit();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Add contacts list fragment
                addContactFragment();

            } else {
                //Again request permission
                PermissionUtils.requestPermission(this, Manifest.permission.READ_CONTACTS, REQUEST_READ_CONTACTS);
            }
        }
    }

    @Override
    public void onContactClick(Contact contact) {

        DetailsFragment detailsFragment = DetailsFragment.newInstance(contact);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, detailsFragment)
                .addToBackStack(null)
                .commit();
    }
}

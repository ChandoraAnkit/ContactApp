package com.testapp.chandora.androidy.contactApp.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.testapp.chandora.androidy.contactApp.data.model.Contact;
import com.testapp.chandora.androidy.contactApp.event.ContactFetchedEvent;
import com.testapp.chandora.androidy.contactApp.utils.BusProvider;


/**
 * Created by chandora on 25-May-2019
 */
public class ContactDetailsFetchTask extends AsyncTask<Contact, Void, Contact> {

    private Context mContext;

    public ContactDetailsFetchTask(Context context) {
        mContext = context;
    }

    @Override
    protected Contact doInBackground(Contact... contacts) {

        Contact contact = contacts[0];

        ContentResolver contentResolver = mContext.getContentResolver();

        //For getting email address
        Cursor emailCursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{contact.getId()},
                null);

        if (emailCursor != null && emailCursor.moveToFirst()) {
            String email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            contact.setEmail(email);
            emailCursor.close();
        }

        //For getting phone number if exists
        if (contact.isHasPhone() > 0) {

            Cursor cursorPhone = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[]{contact.getId()},
                    null);
            if (cursorPhone != null && cursorPhone.moveToFirst()) {
                String phone = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contact.setPhone(phone);
                cursorPhone.close();
            }
        }

        //For getting email address
        Cursor addressCursor = contentResolver.query(
                ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{contact.getId()},
                null);

        if (addressCursor != null && addressCursor.moveToFirst()) {

            StringBuilder builder = new StringBuilder();

            String street = addressCursor.getString(addressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
            String city = addressCursor.getString(addressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
            String country = addressCursor.getString(addressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));

            if (street != null && !street.isEmpty()) {
                builder.append(street);

                if (city != null && !city.isEmpty()){
                    builder.append(" , ");
                }
            }

            if (city != null && !city.isEmpty()) {
                builder.append(city);

                if (country != null && !country.isEmpty()) {
                    builder.append(" , ");
                }

            }

            if (country != null && !country.isEmpty()) {
                builder.append(country);
            }

            contact.setAddress(builder.toString());
            addressCursor.close();
        }


        return contact;
    }

    @Override
    protected void onPostExecute(Contact contact) {
        super.onPostExecute(contact);

        BusProvider.getInstance().post(new ContactFetchedEvent(contact));
    }
}

package com.testapp.chandora.androidy.contactApp.event;

import com.testapp.chandora.androidy.contactApp.data.model.Contact;

/**
 * Created by chandora on 25-May-2019
 */

public class ContactFetchedEvent {

    private static Contact mContact;

    public ContactFetchedEvent(Contact contact){
        mContact = contact;
    }

    public static Contact getContactDetails() {
        return mContact;
    }


}
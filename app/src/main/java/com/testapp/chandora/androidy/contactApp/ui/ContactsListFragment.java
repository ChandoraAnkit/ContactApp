package com.testapp.chandora.androidy.contactApp.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testapp.chandora.androidy.contactApp.R;
import com.testapp.chandora.androidy.contactApp.adapter.ContactsAdapter;
import com.testapp.chandora.androidy.contactApp.data.model.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ContactsListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        ContactsAdapter.OnContactClickListener {

    private static final String[] PROJECTION = {

            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_URI,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };

    private OnContactClickListener mListener;

    private ContactsAdapter mContactsAdapter;

    @BindView(R.id.contacts_rv)
    RecyclerView mContactsRv;

    public ContactsListFragment() {
        // Required empty public constructor
    }

    public static ContactsListFragment newInstance() {
        ContactsListFragment fragment = new ContactsListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnContactClickListener) {
            mListener = (OnContactClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnContactClickListener!");
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoaderManager.getInstance(this).initLoader(0, null, this);

        mContactsAdapter = new ContactsAdapter();

        mContactsAdapter.setOnContactClickListener(this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(view.getContext(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getActivity().getResources().getDrawable(R.drawable.divider));

        mContactsRv.addItemDecoration(dividerItemDecoration);
        mContactsRv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mContactsRv.setAdapter(mContactsAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(
                getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                null,
                null,
                ContactsContract.Contacts.SORT_KEY_PRIMARY
        );

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mContactsAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mContactsAdapter.swapCursor(null);
    }

    @Override
    public void onItemClicked(Contact contact) {
        mListener.onContactClick(contact);
    }


    public interface OnContactClickListener {
        void onContactClick(Contact contact);
    }
}

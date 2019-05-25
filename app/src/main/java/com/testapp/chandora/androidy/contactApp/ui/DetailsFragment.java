package com.testapp.chandora.androidy.contactApp.ui;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.otto.Subscribe;
import com.testapp.chandora.androidy.contactApp.R;
import com.testapp.chandora.androidy.contactApp.data.ContactDetailsFetchTask;
import com.testapp.chandora.androidy.contactApp.data.model.Contact;
import com.testapp.chandora.androidy.contactApp.event.ContactFetchedEvent;
import com.testapp.chandora.androidy.contactApp.ui.custom.SingleDataView;
import com.testapp.chandora.androidy.contactApp.utils.BusProvider;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsFragment extends Fragment {

    public final static String CONTACT = "CONTACT";

    private Contact contact;

    @BindView(R.id.details_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.details_user_iv)
    ImageView mUserImageView;

    @BindView(R.id.no_image_view)
    FrameLayout mUserNoImageLayout;

    @BindView(R.id.letter_tv)
    TextView mNoImageLetter;

    @BindView(R.id.phone_view)
    SingleDataView mPhoneView;

    @BindView(R.id.email_view)
    SingleDataView mEmailView;

    @BindView(R.id.address_view)
    SingleDataView mAddressView;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(Contact contact) {
        DetailsFragment fragment = new DetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(CONTACT,contact);
        fragment.setArguments(bundle);

        return fragment;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        contact = (Contact) getArguments().getSerializable(CONTACT);
        mToolbar.setTitle(contact.getName());

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        ContactDetailsFetchTask fetchTask = new ContactDetailsFetchTask(getContext());
        fetchTask.execute(contact);
    }


    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onContactDetailsFetched(ContactFetchedEvent event){

        Contact contact = ContactFetchedEvent.getContactDetails();

        if (contact.getImageUri() != null ){

            mUserImageView.setVisibility(View.VISIBLE);
            mUserNoImageLayout.setVisibility(View.INVISIBLE);

            Glide.with(getContext())
                    .load(Uri.parse(contact.getImageUri()))
                    .placeholder(R.drawable.ic_male)
                    .into(mUserImageView);
        }else {

            mUserImageView.setVisibility(View.INVISIBLE);
            mUserNoImageLayout.setVisibility(View.VISIBLE);

            Glide.with(getContext())
                    .load(R.drawable.ic_male)
                    .into(mUserImageView);

            mNoImageLetter.setText(contact.getFirstLetter());
            mUserNoImageLayout.setBackground(contact.getColorDrawable());
        }



        mPhoneView.setHeaderValue(contact.getPhone());

        mEmailView.setHeaderValue(contact.getEmail());

        mAddressView.setHeaderValue(contact.getAddress());


    }
}

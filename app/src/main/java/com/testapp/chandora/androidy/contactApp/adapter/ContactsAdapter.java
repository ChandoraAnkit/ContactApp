package com.testapp.chandora.androidy.contactApp.adapter;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.testapp.chandora.androidy.contactApp.R;
import com.testapp.chandora.androidy.contactApp.data.model.Contact;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.testapp.chandora.androidy.contactApp.utils.DrawableUtils.changeDrawableColor;

/**
 * Created by chandora on 24-May-2019
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    private ArrayList<Contact> mContactsList;

    private Cursor dataCursor = null;

    private OnContactClickListener mClickListener;

    public ContactsAdapter() {
        mContactsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_layout, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(mContactsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mContactsList.size();
    }

    public void setDataItems(ArrayList<Contact> contactsList) {

        if (contactsList != null) {
            mContactsList.clear();
            mContactsList.addAll(contactsList);
            notifyDataSetChanged();
        }
    }


    public void setOnContactClickListener(OnContactClickListener listener) {
        mClickListener = listener;
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (dataCursor == newCursor) {
            return null;
        }
        Cursor oldCursor = dataCursor;
        this.dataCursor = newCursor;
        if (newCursor != null) {
            prepareData();
        }
        return oldCursor;
    }

    public void prepareData() {

        ArrayList<Contact> items = new ArrayList<>();

        if (dataCursor != null && dataCursor.getCount() > 0) {

            if (mContactsList != null && mContactsList.size() > 0)
                mContactsList.clear();

            while (dataCursor.moveToNext()) {

                try {

                    Contact contact = new Contact();

                    String id = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Contacts._ID));
                    contact.setId(id);

                    String name = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    contact.setName(name);
                    contact.setFirstLetter(String.valueOf(name.charAt(0)).toUpperCase());

                    String imageUri = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                    contact.setImageUri(imageUri);

                    int hasPhone = dataCursor.getInt(dataCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    contact.setHasPhone(hasPhone);


                    items.add(contact);
                }catch (Exception exception){
                    Log.i("ERROR => ", "prepareData: "+exception.getLocalizedMessage());
                }
            }

            setDataItems(items);
        }

    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.contact_image_iv)
        ImageView mContactImageView;

        @BindView(R.id.contact_name_tv)
        TextView mContactNameTv;

        @BindView(R.id.no_image_view)
        FrameLayout mNoImageLayout;

        @BindView(R.id.letter_tv)
        TextView mEmptyLetter;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(Contact contact) {

            mContactNameTv.setText(contact.getName());

            if (contact.getImageUri() != null) {

                mContactImageView.setVisibility(View.VISIBLE);
                mNoImageLayout.setVisibility(View.INVISIBLE);

                Glide.with(itemView.getContext())
                        .load(Uri.parse(contact.getImageUri()))
                        .apply(RequestOptions.circleCropTransform()
                        ).into(mContactImageView);

            } else {

                mContactImageView.setVisibility(View.INVISIBLE);
                mNoImageLayout.setVisibility(View.VISIBLE);

                Random random = new Random();
                int color = Color.argb(255, random.nextInt(256), random.nextInt(256),
                        random.nextInt(256));

                Drawable drawable = changeDrawableColor(itemView.getContext(),R.drawable.round,color);
                contact.setColorDrawable(drawable);

                mEmptyLetter.setText(contact.getFirstLetter());
                mNoImageLayout.setBackground(drawable);
            }

        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClicked(mContactsList.get(getAdapterPosition()));
        }
    }

    public interface OnContactClickListener {
        void onItemClicked(Contact contact);
    }
}

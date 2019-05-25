package com.testapp.chandora.androidy.contactApp.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;

import com.testapp.chandora.androidy.contactApp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chandora on 25-May-2019
 */
public class SingleDataView extends CardView {

    @BindView(R.id.details_header)
    TextView mHeaderTextView;

    @BindView(R.id.details_value)
    TextView mHeaderValueTextView;

    @BindView(R.id.details_action_iv)
    ImageView mActionImageView;


    private String header;
    private String headerValue;
    private int imageId = -1;

    public SingleDataView(@NonNull Context context) {
        super(context);
    }

    public SingleDataView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SingleDataView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * Defines to initialize views
     */
    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CustomSingleContactDetailView, 0, 0);

        try {

            header = typedArray.getString(R.styleable.CustomSingleContactDetailView_header);
            headerValue = typedArray.getString(R.styleable.CustomSingleContactDetailView_headerValue);
            imageId = typedArray.getResourceId(R.styleable.CustomSingleContactDetailView_actionImage, -1);


        } finally {
            //It will free the resources after use.
            typedArray.recycle();
        }

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_single_detail_layout, this, true);

        ButterKnife.bind(this, view);


        setHeader(header);
        setHeaderValue(headerValue);


        if (imageId != -1) {
            mActionImageView.setImageDrawable(AppCompatResources.getDrawable(getContext(), imageId));
        }

    }

    public void setHeader(String header) {

        if (header != null)
            mHeaderTextView.setText(header);


    }

    public void setHeaderValue(String headerValue) {

        if (headerValue != null)
            mHeaderValueTextView.setText(headerValue);
        else
            mHeaderValueTextView.setText("Not Available.");

    }
}


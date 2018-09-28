package es.developer.achambi.coreframework.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;

public class FloatingTextButton extends FrameLayout {
    public FloatingTextButton(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public FloatingTextButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FloatingTextButton(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init( Context context, AttributeSet attributeSet ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.floating_text_button_layout, this);

        if ( attributeSet != null ) {
            TypedArray typedArray =
                    context.obtainStyledAttributes( attributeSet, R.styleable.FloatingTextButton );
            if( typedArray.hasValue( R.styleable.FloatingTextButton_text ) ) {
                String text = typedArray.getString( R.styleable.FloatingTextButton_text );
                TextView textView = findViewById(R.id.floating_button_text);
                textView.setText( text );
            }
            typedArray.recycle();
        }
    }
}

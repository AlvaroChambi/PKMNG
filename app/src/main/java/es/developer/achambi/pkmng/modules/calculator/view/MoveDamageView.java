package es.developer.achambi.pkmng.modules.calculator.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;

public class MoveDamageView extends CardView {
    public static final int LEFT_ORIENTATION = -1;
    public static final int RIGHT_ORIENTATION = 1;

    private int orientation;

    public MoveDamageView(Context context) {
        super(context);
        init(context);
    }

    public MoveDamageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MoveDamageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init( Context context ) {
        orientation = RIGHT_ORIENTATION;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.move_damage_view_layout, this );
        setOrientation( orientation );
    }

    public void setOrientation( int orientation ) {
        switch (orientation) {
            case RIGHT_ORIENTATION:
                findViewById(R.id.move_damage_view_left_right).setVisibility(VISIBLE);
                findViewById(R.id.move_damage_view_right_left).setVisibility(GONE);
                break;
            case LEFT_ORIENTATION:
                findViewById(R.id.move_damage_view_left_right).setVisibility(GONE);
                findViewById(R.id.move_damage_view_right_left).setVisibility(VISIBLE);
                break;
        }
    }

    public void setIsEmpty( boolean empty ) {
        if( !empty ) {
            findViewById(R.id.move_damage_empty_view).setVisibility(View.GONE);
            findViewById(R.id.move_damage_empty_view_right_left).setVisibility(View.GONE);
        } else {
            findViewById(R.id.move_damage_empty_view).setVisibility(View.VISIBLE);
            findViewById(R.id.move_damage_empty_view_right_left).setVisibility(View.VISIBLE);
        }
    }

    public void setMoveName( String text ) {
        ((TextView)findViewById(R.id.move_damage_name_text)).setText(text);
        ((TextView)findViewById(R.id.move_damage_name_text_right_left)).setText(text);
    }

    public void setMoveType( String text ) {
        ((TextView)findViewById(R.id.move_damage_type_text)).setText(text);
        ((TextView)findViewById(R.id.move_damage_type_text_right_left)).setText(text);
    }

    public void setMoveCategory( String text ) {
        ((TextView)findViewById(R.id.move_damage_category_text)).setText(text);
        ((TextView)findViewById(R.id.move_damage_category_text_right_left)).setText(text);
    }

    public void setMovePower( String text ) {
        ((TextView)findViewById(R.id.move_damage_power_text)).setText(text);
        ((TextView)findViewById(R.id.move_damage_power_text_right_left)).setText(text);
    }

    public void setMoveEffect( String text ) {
        ((TextView)findViewById(R.id.move_damage_effect_text)).setText(text);
        ((TextView)findViewById(R.id.move_damage_effect_text_right_left)).setText(text);
    }

    public void setMoveResult( String text ) {
        ((TextView)findViewById(R.id.move_damage_result_text)).setText(text);
        ((TextView)findViewById(R.id.move_damage_result_text_right_left)).setText(text);
    }
}

package es.developer.achambi.pkmng.core.ui;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import es.developer.achambi.pkmng.R;

public class FloatingActionMenu extends ConstraintLayout
    implements View.OnClickListener {
    public interface MenuOptionClickedListener {
        void onMenuOptionClicked( int id );
    }

    private MenuOptionClickedListener listener;

    public FloatingActionMenu(Context context) {
        super(context);
        init(context);
    }

    public FloatingActionMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloatingActionMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init( Context context ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.floating_action_menu_layout, this);
        findViewById(R.id.configuration_floating_save_button_middle).setOnClickListener(this);
        findViewById(R.id.configuration_floating_save_button_left).setOnClickListener(this);
        findViewById(R.id.configuration_floating_save_button_main).setOnClickListener(this);
        findViewById(R.id.configuration_floating_save_button_right).setOnClickListener(this);
        findViewById(R.id.floating_menu_background).setOnClickListener(this);
    }

    public void setListener( MenuOptionClickedListener listener ) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.configuration_floating_save_button_main:
                showMenuOptions();
                break;
            default:
                hideMenuOptions();
                break;
        }
        if( listener != null ) {
            listener.onMenuOptionClicked( v.getId() );
        }
    }

    private void showMenuOptions() {
        findViewById(R.id.configuration_floating_save_button_left).setVisibility(VISIBLE);
        findViewById(R.id.configuration_floating_save_button_middle).setVisibility(VISIBLE);
        findViewById(R.id.configuration_floating_save_button_right).setVisibility(VISIBLE);
        findViewById(R.id.floating_menu_background).setVisibility(VISIBLE);
    }

    private void hideMenuOptions() {
        findViewById(R.id.configuration_floating_save_button_left).setVisibility(GONE);
        findViewById(R.id.configuration_floating_save_button_middle).setVisibility(GONE);
        findViewById(R.id.configuration_floating_save_button_right).setVisibility(GONE);
        findViewById(R.id.floating_menu_background).setVisibility(GONE);
    }
}

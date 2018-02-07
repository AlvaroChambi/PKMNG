package es.developer.achambi.pkmng.core.ui;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.utils.ParcelUtil;

public class FloatingActionMenu extends ConstraintLayout
    implements View.OnClickListener {
    public interface MenuOptionClickedListener {
        void onMenuOptionClicked( int id );
    }

    private MenuOptionClickedListener listener;
    private boolean expanded;

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
        expanded = false;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.floating_action_menu_layout, this);
        findViewById(R.id.configuration_floating_save_button_middle).setOnClickListener(this);
        findViewById(R.id.configuration_floating_save_button_left).setOnClickListener(this);
        findViewById(R.id.configuration_floating_save_button_main).setOnClickListener(this);
        findViewById(R.id.configuration_floating_save_button_right).setOnClickListener(this);
        findViewById(R.id.configuration_floating_save_button_middle).setVisibility(INVISIBLE);
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
        expanded = true;
        findViewById(R.id.configuration_floating_save_button_left).setVisibility(VISIBLE);
        findViewById(R.id.configuration_floating_save_button_right).setVisibility(VISIBLE);
        findViewById(R.id.floating_menu_background).setVisibility(VISIBLE);
    }

    private void hideMenuOptions() {
        expanded = false;
        findViewById(R.id.configuration_floating_save_button_left).setVisibility(GONE);
        findViewById(R.id.configuration_floating_save_button_right).setVisibility(GONE);
        findViewById(R.id.floating_menu_background).setVisibility(GONE);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState =  super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.expandedSaved = expanded;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.expanded = ss.expandedSaved;
        if(expanded) {
            showMenuOptions();
        }
    }

    static class SavedState extends BaseSavedState {
        boolean expandedSaved;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            this.expandedSaved = ParcelUtil.readBoolean(source);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            ParcelUtil.writeBoolean( out, this.expandedSaved );
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}

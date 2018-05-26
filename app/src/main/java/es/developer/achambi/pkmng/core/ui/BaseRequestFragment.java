package es.developer.achambi.pkmng.core.ui;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.threading.Error;

public abstract class BaseRequestFragment extends BaseFragment implements View.OnClickListener {
    public static final float TRANSPARENT_LOADING_BACKGROUND = 0.4f;
    public void startLoading() {
        View loadingFrame = getView().findViewById(getLoadingFrame());
        View background = loadingFrame.findViewById(R.id.base_request_background);
        background.setOnClickListener(this);//meant to catch any click action while it's visible
        View retry = loadingFrame.findViewById( R.id.base_request_retry_text );
        retry.setOnClickListener( this );
        loadingFrame.setVisibility(View.VISIBLE);
        loadingFrame.findViewById( R.id.base_request_progress_bar ).setVisibility( View.VISIBLE );
        loadingFrame.findViewById( R.id.base_request_error_message ).setVisibility(View.GONE);
        loadingFrame.findViewById( R.id.base_request_retry_text ).setVisibility(View.GONE);
    }

    /**
     * Start loading animation applying the given alpha to the background
     * @param backgroundAlpha
     */
    public void startLoading(float backgroundAlpha ) {
        startLoading();
        View loadingFrame = getView().findViewById(getLoadingFrame());
        View background = loadingFrame.findViewById(R.id.base_request_background);
        background.setAlpha( backgroundAlpha );
    }

    protected void hideLoading() {
        View loadingFrame = getView().findViewById(getLoadingFrame());
        loadingFrame.setVisibility(View.GONE);
    }

    protected void showError( Error error ) {
        View loadingFrame = getView().findViewById(getLoadingFrame());
        loadingFrame.findViewById( R.id.base_request_progress_bar ).setVisibility( View.GONE );
        TextView errorMessage = loadingFrame.findViewById( R.id.base_request_error_message );
        errorMessage.setText( error.getMessage() );
        errorMessage.setVisibility(View.VISIBLE);
        loadingFrame.findViewById( R.id.base_request_retry_text ).setVisibility(View.VISIBLE);
    }

    protected void showSnackBackError( Error error ) {
        hideLoading();
        Snackbar snackbar = Snackbar.make( getView(), error.getMessage(), Snackbar.LENGTH_INDEFINITE );
        snackbar.show();
    }

    public abstract int getLoadingFrame();
    public void onRetry() {
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.base_request_retry_text:
                onRetry();
                break;
            default:
                break;
        }
    }
}

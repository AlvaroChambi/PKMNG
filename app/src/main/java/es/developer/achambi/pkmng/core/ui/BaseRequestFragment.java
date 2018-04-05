package es.developer.achambi.pkmng.core.ui;

import android.view.View;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.threading.Error;

public abstract class BaseRequestFragment extends BaseFragment implements View.OnClickListener {
    public void doRequest() {
        View loadingFrame = getView().findViewById(getLoadingFrame());
        View retry = loadingFrame.findViewById( R.id.base_request_retry_text );
        retry.setOnClickListener( this );
        loadingFrame.setVisibility(View.VISIBLE);
        loadingFrame.findViewById( R.id.base_request_progress_bar ).setVisibility( View.VISIBLE );
        loadingFrame.findViewById( R.id.base_request_error_message ).setVisibility(View.GONE);
        loadingFrame.findViewById( R.id.base_request_retry_text ).setVisibility(View.GONE);
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

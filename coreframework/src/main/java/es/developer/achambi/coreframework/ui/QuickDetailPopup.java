package es.developer.achambi.coreframework.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public class QuickDetailPopup {
    public static void displayDetails( View content, View anchor ) {
        PopupWindow popup = new PopupWindow( content, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT );
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.setOutsideTouchable( true );
        popup.showAsDropDown( anchor );
    }
}

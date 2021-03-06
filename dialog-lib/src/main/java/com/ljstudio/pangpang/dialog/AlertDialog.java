package com.ljstudio.pangpang.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


public class AlertDialog extends BaseDialog {

    private TextView txtMessage;
    private TextView txtTitle;
    private TextView btnSure;

    private IListener listener;

    public AlertDialog(Context context) {
        this(context, null, null, null);
    }

    public AlertDialog(Context context, IListener listener, String title, String message) {
        super(context, R.layout.dialog_alert);
        this.listener = listener;

        txtMessage = (TextView) findViewById(R.id.txtMessage);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        btnSure = (TextView) findViewById(R.id.btnSure);
        setTitle(title);
        setMessage(message);

        initEvents();
    }


    public void setTitle(String title) {
        if (title != null) {
            txtTitle.setText(title);
        }
    }

    public void setButtonText(String text) {
        btnSure.setText(text);
    }

    public void setMessage(String txt) {
        if (txt != null) {
            txtMessage.setText(txt);
        }
    }

    public void setListener(IListener listener) {
        this.listener = listener;
    }

    private void initEvents() {

        findViewById(R.id.box).setClickable(true);
        final View.OnClickListener lis = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btnLeft) {
                    onCancel();
                } else {
                    if (listener != null) {
                        listener.onDone();
                    }
                }
                dismiss();
            }
        };
        findViewById(R.id.btnSure).setOnClickListener(lis);
    }

    @Override
    protected void onCancel() {
        if (listener != null) {
            listener.onDone();
        }
    }

    public static interface IListener {
        void onDone();
    }
}

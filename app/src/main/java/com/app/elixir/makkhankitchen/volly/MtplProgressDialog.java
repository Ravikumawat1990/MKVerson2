package com.app.elixir.makkhankitchen.volly;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;


public class MtplProgressDialog extends Dialog {


    public MtplProgressDialog(Context context, String Message,
                              boolean isCancelable) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mtpl_progress_dialog);

        setCancelable(isCancelable);
        //   ProgressBar pBar = (ProgressBar) findViewById(R.id.dialogProgressBar);
        // ProgressBar pBar = (ProgressBar) findViewById(R.id.dialogProgressBar);
        ImageView imageView = (ImageView) findViewById(R.id.dialogProgressBar);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(context).load(R.raw.animated).into(imageViewTarget);

        TextView txtMsg = (TextView) findViewById(R.id.mtpl_customdialog_txtMessage);

        txtMsg.setText(Message);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


    }
}
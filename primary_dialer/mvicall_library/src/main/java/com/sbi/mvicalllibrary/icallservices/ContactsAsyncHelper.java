package com.sbi.mvicalllibrary.icallservices;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.sbi.mvicalllibrary.R;

import java.io.IOException;
import java.io.InputStream;


public class ContactsAsyncHelper {

    public interface OnImageLoadCompleteListener {
        void onImageLoadComplete(int token, Drawable photo, Bitmap photoIcon, Object cookie);
    }

    private static final int EVENT_LOAD_IMAGE = 1;

    private final Handler mResultHandler = new Handler() {
        /** Called when loading is done. */
        @Override
        public void handleMessage(Message msg) {
            WorkerArgs args = (WorkerArgs) msg.obj;
            switch (msg.arg1) {
                case EVENT_LOAD_IMAGE:
                    if (args.listener != null) {
                        args.listener.onImageLoadComplete(msg.what, args.photo, args.photoIcon, args.cookie);
                    }
                    break;
                default:
            }
        }
    };

    private static Handler sThreadHandler;
    private static final ContactsAsyncHelper sInstance;

    static {
        sInstance = new ContactsAsyncHelper();
    }

    private static final class WorkerArgs {
        public Context context;
        public Uri displayPhotoUri;
        public Drawable photo;
        public Bitmap photoIcon;
        public Object cookie;
        public OnImageLoadCompleteListener listener;
    }

    private class WorkerHandler extends Handler {
        public WorkerHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            WorkerArgs args = (WorkerArgs) msg.obj;

            switch (msg.arg1) {
                case EVENT_LOAD_IMAGE:
                    InputStream inputStream = null;
                    try {
                        try {
                            inputStream = args.context.getContentResolver()
                                    .openInputStream(args.displayPhotoUri);
                        } catch (Exception e) {
                            Log.e(this, "Error opening photo input stream", e);
                        }

                        if (inputStream != null) {
                            args.photo = Drawable.createFromStream(inputStream, args.displayPhotoUri.toString());
                            args.photoIcon = getPhotoIconWhenAppropriate(args.context, args.photo);
                        } else {
                            args.photo = null;
                            args.photoIcon = null;
                        }
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                               e.printStackTrace();
                            }
                        }
                    }
                    break;
                default:
            }

            Message reply = ContactsAsyncHelper.this.mResultHandler.obtainMessage(msg.what);
            reply.arg1 = msg.arg1;
            reply.obj = msg.obj;
            reply.sendToTarget();
        }

        private Bitmap getPhotoIconWhenAppropriate(Context context, Drawable photo) {
            if (!(photo instanceof BitmapDrawable)) {
                return null;
            }
            int iconSize = context.getResources()
                    .getDimensionPixelSize(R.dimen.notification_icon_size);
            Bitmap orgBitmap = ((BitmapDrawable) photo).getBitmap();
            int orgWidth = orgBitmap.getWidth();
            int orgHeight = orgBitmap.getHeight();
            int longerEdge = orgWidth > orgHeight ? orgWidth : orgHeight;
            if (longerEdge > iconSize) {
                float ratio = ((float) longerEdge) / iconSize;
                int newWidth = (int) (orgWidth / ratio);
                int newHeight = (int) (orgHeight / ratio);
                if (newWidth <= 0 || newHeight <= 0) {
                    return null;
                }
                return Bitmap.createScaledBitmap(orgBitmap, newWidth, newHeight, true);
            } else {
                return orgBitmap;
            }
        }
    }

    private ContactsAsyncHelper() {
        HandlerThread thread = new HandlerThread("ContactsAsyncWorker");
        thread.start();
        sThreadHandler = new WorkerHandler(thread.getLooper());
    }

    public static final void startObtainPhotoAsync(int token, Context context, Uri displayPhotoUri, OnImageLoadCompleteListener listener, Object cookie) {

        if (displayPhotoUri == null) {
            Log.wtf("startObjectPhotoAsync", "Uri is missing");
            return;
        }

        WorkerArgs args = new WorkerArgs();
        args.cookie = cookie;
        args.context = context;
        args.displayPhotoUri = displayPhotoUri;
        args.listener = listener;

        Message msg = sThreadHandler.obtainMessage(token);
        msg.arg1 = EVENT_LOAD_IMAGE;
        msg.obj = args;

        sThreadHandler.sendMessage(msg);
    }


}

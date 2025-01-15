package com.sbi.mvicalllibrary.icallservices;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.google.common.base.Objects;
import com.sbi.mvicalllibrary.R;


public class VideoCallFragment extends BaseFragment<VideoCallPresenter, VideoCallPresenter.VideoCallUi> implements VideoCallPresenter.VideoCallUi {

    private static final String TAG = VideoCallFragment.class.getSimpleName();
    private static final boolean DEBUG = false;
    private static final int DIMENSIONS_NOT_SET = -1;
    public static final int SURFACE_DISPLAY = 1;
    public static final int SURFACE_PREVIEW = 2;
    public static final int ORIENTATION_UNKNOWN = -1;
    private static boolean sVideoSurfacesInUse = false;
    private static VideoCallSurface sPreviewSurface = null;
    private static VideoCallSurface sDisplaySurface = null;
    private static Point sDisplaySize = null;
    private ViewStub mVideoViewsStub;
    private View mVideoViews;
    private View mPreviewVideoContainer;
    private View mCameraOff;
    private ImageView mPreviewPhoto;
    private boolean mIsLayoutComplete = false;
    private boolean mIsLandscape;

    private static class VideoCallSurface implements TextureView.SurfaceTextureListener,
            View.OnClickListener, View.OnAttachStateChangeListener {
        private final int mSurfaceId;
        private VideoCallPresenter mPresenter;
        private TextureView mTextureView;
        private SurfaceTexture mSavedSurfaceTexture;
        private Surface mSavedSurface;
        private boolean mIsDoneWithSurface;
        private int mWidth = DIMENSIONS_NOT_SET;
        private int mHeight = DIMENSIONS_NOT_SET;

        public VideoCallSurface(VideoCallPresenter presenter, int surfaceId,
                TextureView textureView) {
            this(presenter, surfaceId, textureView, DIMENSIONS_NOT_SET, DIMENSIONS_NOT_SET);
        }

        public VideoCallSurface(VideoCallPresenter presenter, int surfaceId, TextureView textureView, int width, int height) {
            mPresenter = presenter;
            mWidth = width;
            mHeight = height;
            mSurfaceId = surfaceId;

            recreateView(textureView);
        }


        public void recreateView(TextureView view) {
            if (DEBUG) {

            }

            if (mTextureView == view) {
                return;
            }

            mTextureView = view;
            mTextureView.setSurfaceTextureListener(this);
            mTextureView.setOnClickListener(this);

            final boolean areSameSurfaces =
                    Objects.equal(mSavedSurfaceTexture, mTextureView.getSurfaceTexture());
            if (mSavedSurfaceTexture != null && !areSameSurfaces) {
                mTextureView.setSurfaceTexture(mSavedSurfaceTexture);
                if (createSurface(mWidth, mHeight)) {
                    onSurfaceCreated();
                }
            }
            mIsDoneWithSurface = false;
        }

        public void resetPresenter(VideoCallPresenter presenter) {
            mPresenter = presenter;
        }

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width,
                                              int height) {
            boolean surfaceCreated;

            if (mSavedSurfaceTexture == null) {
                mSavedSurfaceTexture = surfaceTexture;
                surfaceCreated = createSurface(width, height);
            } else {
                mTextureView.setSurfaceTexture(mSavedSurfaceTexture);
                surfaceCreated = true;
            }

            if (surfaceCreated) {
                onSurfaceCreated();
            }
        }

        private void onSurfaceCreated() {
            if (mPresenter != null) {
                mPresenter.onSurfaceCreated(mSurfaceId);
            }
        }


        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width,
                                                int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {

            onSurfaceDestroyed();

            if (mIsDoneWithSurface) {
                onSurfaceReleased();
                if (mSavedSurface != null) {
                    mSavedSurface.release();
                    mSavedSurface = null;
                }
            }
            return mIsDoneWithSurface;
        }

        private void onSurfaceDestroyed() {
            if (mPresenter != null) {
                mPresenter.onSurfaceDestroyed(mSurfaceId);
            } else {

            }
        }


        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }

        @Override
        public void onViewAttachedToWindow(View v) {
            if (mSavedSurfaceTexture != null) {
                mTextureView.setSurfaceTexture(mSavedSurfaceTexture);
            }
        }

        @Override
        public void onViewDetachedFromWindow(View v) {}

        public TextureView getTextureView() {
            return mTextureView;
        }

        public void setDoneWithSurface() {

            mIsDoneWithSurface = true;
            if (mTextureView != null && mTextureView.isAvailable()) {
                return;
            }

            if (mSavedSurface != null) {
                onSurfaceReleased();
                mSavedSurface.release();
                mSavedSurface = null;
            }
            if (mSavedSurfaceTexture != null) {
                mSavedSurfaceTexture.release();
                mSavedSurfaceTexture = null;
            }
        }

        private void onSurfaceReleased() {
            if (mPresenter != null) {
                mPresenter.onSurfaceReleased(mSurfaceId);
            }
        }

        public Surface getSurface() {
            return mSavedSurface;
        }

        public void setSurfaceDimensions(int width, int height) {
            mWidth = width;
            mHeight = height;

            if (mSavedSurfaceTexture != null) {
                createSurface(width, height);
            }
        }

        private boolean createSurface(int width, int height) {
            if (width != DIMENSIONS_NOT_SET && height != DIMENSIONS_NOT_SET
                    && mSavedSurfaceTexture != null) {
                mSavedSurfaceTexture.setDefaultBufferSize(width, height);
                mSavedSurface = new Surface(mSavedSurfaceTexture);
                return true;
            }
            return false;
        }


        @Override
        public void onClick(View view) {
            if (mPresenter != null) {
                mPresenter.onSurfaceClick(mSurfaceId);
            }
        }

        public Point getSurfaceDimensions() {
            return new Point(mWidth, mHeight);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsLandscape = getResources().getBoolean(R.bool.is_layout_landscape);
        getPresenter().init(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.video_call_fragment, container, false);

        return view;
    }

    private void centerDisplayView(View displayVideo) {
        if (!mIsLandscape) {
            float spaceBesideCallCard = InCallPresenter.getInstance().getSpaceBesideCallCard();
            float videoViewTranslation = displayVideo.getHeight() / 2
                    - spaceBesideCallCard / 2;
            displayVideo.setTranslationY(videoViewTranslation);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mVideoViewsStub = view.findViewById(R.id.videoCallViewsStub);

        if (sVideoSurfacesInUse) {
            inflateVideoCallViews();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public VideoCallPresenter createPresenter() {

        VideoCallPresenter presenter = new VideoCallPresenter();
        onPresenterChanged(presenter);
        return presenter;
    }

    @Override
    public VideoCallPresenter.VideoCallUi getUi() {
        return this;
    }

    private void inflateVideoUi(boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        getView().setVisibility(visibility);

        if (show) {
            inflateVideoCallViews();
        }

        if (mVideoViews != null) {
            mVideoViews.setVisibility(visibility);
        }
    }

    public void showVideoViews(boolean previewPaused, boolean showIncoming) {
        inflateVideoUi(true);

        View incomingVideoView = mVideoViews.findViewById(R.id.incomingVideo);
        if (incomingVideoView != null) {
            incomingVideoView.setVisibility(showIncoming ? View.VISIBLE : View.INVISIBLE);
        }
        if (mCameraOff != null) {
            mCameraOff.setVisibility(!previewPaused ? View.VISIBLE : View.INVISIBLE);
        }
        if (mPreviewPhoto != null) {
            mPreviewPhoto.setVisibility(!previewPaused ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void hideVideoUi() {
        inflateVideoUi(false);
    }

    @Override
    public void cleanupSurfaces() {

        if (sDisplaySurface != null) {
            sDisplaySurface.setDoneWithSurface();
            sDisplaySurface = null;
        }
        if (sPreviewSurface != null) {
            sPreviewSurface.setDoneWithSurface();
            sPreviewSurface = null;
        }
        sVideoSurfacesInUse = false;
    }

    @Override
    public ImageView getPreviewPhotoView() {
        return mPreviewPhoto;
    }

    private void onPresenterChanged(VideoCallPresenter presenter) {

        if (sDisplaySurface != null) {
            sDisplaySurface.resetPresenter(presenter);
        }
        if (sPreviewSurface != null) {
            sPreviewSurface.resetPresenter(presenter);
        }
    }

    @Override
    public boolean isDisplayVideoSurfaceCreated() {
        boolean ret = sDisplaySurface != null && sDisplaySurface.getSurface() != null;

        return ret;
    }

    @Override
    public boolean isPreviewVideoSurfaceCreated() {
        boolean ret = sPreviewSurface != null && sPreviewSurface.getSurface() != null;

        return ret;
    }

    @Override
    public Surface getDisplayVideoSurface() {
        return sDisplaySurface == null ? null : sDisplaySurface.getSurface();
    }

    @Override
    public Surface getPreviewVideoSurface() {
        return sPreviewSurface == null ? null : sPreviewSurface.getSurface();
    }

    @Override
    public void setPreviewSize(int width, int height) {

        if (sPreviewSurface != null) {
            TextureView preview = sPreviewSurface.getTextureView();

            if (preview == null ) {
                return;
            }

            ViewGroup.LayoutParams params = preview.getLayoutParams();
            params.width = width;
            params.height = height;
            preview.setLayoutParams(params);

            if (mPreviewVideoContainer != null) {
                ViewGroup.LayoutParams containerParams = mPreviewVideoContainer.getLayoutParams();
                containerParams.width = width;
                containerParams.height = height;
                mPreviewVideoContainer.setLayoutParams(containerParams);
            }

            Matrix transform = new Matrix();
            transform.setScale(-1, 1, width/2, 0);
            preview.setTransform(transform);
        }
    }

    @Override
    public void setPreviewSurfaceSize(int width, int height) {
        final boolean isPreviewSurfaceAvailable = sPreviewSurface != null;
        if (isPreviewSurfaceAvailable) {
            sPreviewSurface.setSurfaceDimensions(width, height);
        }
    }

    @Override
    public int getCurrentRotation() {
        try {
            return getActivity().getWindowManager().getDefaultDisplay().getRotation();
        } catch (Exception e) {

        }
        return ORIENTATION_UNKNOWN;
    }

    @Override
    public void setDisplayVideoSize(int width, int height) {

        if (sDisplaySurface != null) {
            TextureView displayVideo = sDisplaySurface.getTextureView();
            if (displayVideo == null) {

                return;
            }
            sDisplaySize = new Point(width, height);
            setSurfaceSizeAndTranslation(displayVideo, sDisplaySize);
        } else {

        }
    }

    @Override
    public Point getScreenSize() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
    }

    @Override
    public Point getPreviewSize() {
        if (sPreviewSurface == null) {
            return null;
        }
        return sPreviewSurface.getSurfaceDimensions();
    }

    private void inflateVideoCallViews() {

        if (mVideoViews == null ) {
            mVideoViews = mVideoViewsStub.inflate();
        }

        if (mVideoViews != null) {
            mPreviewVideoContainer = mVideoViews.findViewById(R.id.previewVideoContainer);
            mCameraOff = mVideoViews.findViewById(R.id.previewCameraOff);
            mPreviewPhoto = mVideoViews.findViewById(R.id.previewProfilePhoto);

            TextureView displaySurface = mVideoViews.findViewById(R.id.incomingVideo);


            Point screenSize = sDisplaySize == null ? getScreenSize() : sDisplaySize;
            setSurfaceSizeAndTranslation(displaySurface, screenSize);

            if (!sVideoSurfacesInUse) {


                sDisplaySurface = new VideoCallSurface(getPresenter(), SURFACE_DISPLAY, mVideoViews.findViewById(R.id.incomingVideo), screenSize.x,
                        screenSize.y);
                sPreviewSurface = new VideoCallSurface(getPresenter(), SURFACE_PREVIEW, mVideoViews.findViewById(R.id.previewVideo));
                sVideoSurfacesInUse = true;
            } else {
                sDisplaySurface.recreateView(mVideoViews.findViewById(
                        R.id.incomingVideo));
                sPreviewSurface.recreateView(mVideoViews.findViewById(
                        R.id.previewVideo));
            }

            final ViewTreeObserver observer = mVideoViews.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    View displayVideo = mVideoViews.findViewById(R.id.incomingVideo);
                    if (displayVideo != null) {
                        centerDisplayView(displayVideo);
                    }
                    mIsLayoutComplete = true;

                    ViewTreeObserver observer = mVideoViews.getViewTreeObserver();
                    if (observer.isAlive()) {
                        observer.removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }

    private void setSurfaceSizeAndTranslation(TextureView textureView, Point size) {
        ViewGroup.LayoutParams params = textureView.getLayoutParams();
        params.width = size.x;
        params.height = size.y;
        textureView.setLayoutParams(params);

        if (mIsLayoutComplete) {
            centerDisplayView(textureView);
        }
    }
}

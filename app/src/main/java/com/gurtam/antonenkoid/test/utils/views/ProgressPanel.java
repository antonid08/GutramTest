package com.gurtam.antonenkoid.test.utils.views;

import com.gurtam.antonenkoid.test.R;
import com.gurtam.antonenkoid.test.utils.UiUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Component that shows progress wheel over their child views.
 * <p>
 *
 * Usage:
 * <ul>
 *     <li>put views into {@link ProgressPanel} in layout</li>
 *     <li>call {@link #show()} and {@link #hide()} when it required.</li>
 * </ul>
 *
 * @author antonenkoid
 */
public class ProgressPanel extends FrameLayout implements Progress {

    @BindView(R.id.progressBar)
    View progressBar;

    private View progressView;

    public ProgressPanel(Context context) {
        super(context);
        init();
    }

    public ProgressPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        progressView = UiUtils.inflate(getContext(), R.layout.progress_panel_layout);

        progressView.setOnClickListener((view) -> {});

        ButterKnife.bind(progressView);
    }

    @Override
    public final void show() {
        if (indexOfChild(progressView) < 0) {
            addView(progressView);
        }

        changeProgressVisibility(true);
    }

    @Override
    public final void hide() {
        changeProgressVisibility(false);
    }

    private void changeProgressVisibility(boolean show) {
        UiUtils.setVisibility(show, progressView);
    }

}

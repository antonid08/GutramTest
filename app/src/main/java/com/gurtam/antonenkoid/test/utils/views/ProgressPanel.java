package com.gurtam.antonenkoid.test.utils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.gurtam.antonenkoid.test.R;
import com.gurtam.antonenkoid.test.utils.UiUtils;

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

    public ProgressPanel(Context context) {
        super(context);
        init();
    }

    public ProgressPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.progress_panel_layout, this);

        setOnClickListener((view) -> {
        });

        ButterKnife.bind(this);
    }

    @Override
    public final void show() {
        changeProgressVisibility(true);
    }

    @Override
    public final void hide() {
        changeProgressVisibility(false);
    }

    private void changeProgressVisibility(boolean show) {
        UiUtils.setVisibility(show, progressBar);
    }

}

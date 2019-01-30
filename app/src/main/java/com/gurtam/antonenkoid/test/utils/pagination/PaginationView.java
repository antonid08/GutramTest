package com.gurtam.antonenkoid.test.utils.pagination;

import com.gurtam.antonenkoid.test.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;


/**
 * Page navigation view. Contains buttons "next page" and "previous page" and callback {@link OnPageChangedListener}
 *
 * @author antonenkoid
 */
public class PaginationView extends FrameLayout {

    private OnPageChangedListener onPageChangedListener;

    private View previous;
    private View next;

    public PaginationView(Context context) {
        super(context);
        initViews(context);
    }

    public PaginationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.pagination_view, this, true);

        previous = findViewById(R.id.previous);
        previous.setOnClickListener(new PreviousPageClickedListener());

        next = findViewById(R.id.next);
        next.setOnClickListener(new NextPageClickedListener());
    }

    /**
     * Set previous page available or not
     */
    public void setPreviousPageAvailable(boolean available) {
        previous.setEnabled(available);
    }

    /**
     * Set next page available or not
     */
    public void setNextPageAvailable(boolean available) {
        next.setEnabled(available);
    }

    /**
     * Set listener for page changing.
     */
    public void setOnPageChangedListener(OnPageChangedListener listener) {
        onPageChangedListener = listener;
    }


    /**
     * Interface that represents events of changing pages.
     */
    public interface OnPageChangedListener {

        /**
         * Previous page was selected.
         */
        void onPreviousPage();

        /**
         * Next page was selected.
         */
        void onNextPage();
    }

    private class PreviousPageClickedListener implements OnClickListener {

        @Override
        public void onClick(View element) {
            if (onPageChangedListener != null) {
                onPageChangedListener.onPreviousPage();
            }
        }
    }

    private class NextPageClickedListener implements OnClickListener {

        @Override
        public void onClick(View element) {
            if (onPageChangedListener != null) {
                onPageChangedListener.onNextPage();
            }
        }
    }
}

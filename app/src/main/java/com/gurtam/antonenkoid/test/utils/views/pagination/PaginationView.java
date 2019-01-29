/*
 * Copyright 2007 Qulix Systems, Inc. All rights reserved.
 * QULIX SYSTEMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * Copyright (c) 2003-2007 Qulix Systems, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Qulix Systems. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 *
 * QULIX MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.gurtam.antonenkoid.test.utils.views.pagination;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.gurtam.antonenkoid.test.R;

import androidx.annotation.Nullable;


/**
 * Класс для View постраничной навигации.
 *
 * <p>Для использования нужно установить {@code onPageChangedListener}, который содержит коллбэки переключения страниц.</p>
 *
 * @author Q-IAN
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
     * Установить доступность view "предыдущая страница"
     *
     * @param enabled доступна ли view
     */
    public void setPreviousEnabled(boolean enabled) {
        previous.setEnabled(enabled);
    }

    /**
     * Установить доступность view "следующая страница"
     *
     * @param enabled доступна ли view
     */
    public void setNextEnabled(boolean enabled) {
        next.setEnabled(enabled);
    }

    /**
     * Установить listener для смены страницы.
     *
     * @param listener listener для смены страницы.
     */
    public void setOnPageChangedListener(OnPageChangedListener listener) {
        onPageChangedListener = listener;
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

    /**
     * Интерфейс с коллбэками переключения страниц. Коллбэки вызываются после нажатия на соответствующие элементы управления.
     */
    public interface OnPageChangedListener {

        /**
         * Срабатывает, когда происходит переключение на предыдущую страницу.
         */
        void onPreviousPage();

        /**
         * Срабатывает, когда происходит переключение на следующую страницу.
         */
        void onNextPage();
    }
}

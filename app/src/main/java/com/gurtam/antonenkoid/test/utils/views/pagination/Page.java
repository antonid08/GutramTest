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

/**
 * <p>Класс страницы в постраничной навигации. Хранит номер и размер страницы.</p>
 *
 * @author Q-IAN
 */
public class Page {

    private final int index;
    private final int size;

    /**
     * Конструктор. Номер и размер странцы задаются из вне.
     *
     * @param index номер страницы
     * @param size размер страницы
     */
    public Page(int index, int size) {
        this.index = index;
        this.size = size;
    }

    /**
     * Получить номер страницы.
     *
     * @return номер страницы.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Получить размер страницы.
     *
     * @return размер страницы.
     */
    public int getSize() {
        return size;
    }
}
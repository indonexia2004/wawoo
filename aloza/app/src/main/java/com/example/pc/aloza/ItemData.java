/**
 * Copyright (C) 2013, HarveyNash Group.
 *
 * The program(s) herein may be used and/or copied only with the 
 * written permission of HarveyNash Group. or in accordance with 
 * the terms and conditions stipulated in the agreement/contract 
 * under which the program(s) have been supplied.
 *
 * $Id: ItemData.java ,v 2.0 2013/10/07 DaiBui
 * Date       Author Changes
 * October 7 2013 DaiBui Created 
 */

package com.example.pc.aloza;

public class ItemData {

    private String title;
    private String content;

    public ItemData(String title, String content) {
        this.setTitle(title);
        this.setContent(content);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the id
     */
    public String getContent() {
        return content;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setContent(String content) {
        this.content = content;
    }

}

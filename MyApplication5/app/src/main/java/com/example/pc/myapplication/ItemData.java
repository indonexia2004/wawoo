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

package com.example.pc.myapplication;

public class ItemData {

    private String title;
    private boolean isCheck;
    private String id;

    public ItemData(String title, String id) {
        this.setTitle(title);
        this.setId(id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the isCheck
     */
    public boolean isCheck() {
        return isCheck;
    }

    /**
     * @param isCheck
     *            the isCheck to set
     */
    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}

package com.example.pc.myapplication;

public class EkData {

    private int id;
    private String message;
    private boolean isCheck;

    public EkData(String message) {
        this.message = message;
    }

    public EkData() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
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

}

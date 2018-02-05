/* Subscription
 *
 *  Feb 05, 2018
 *
 *  Copyright © 2018 patenio CMPUT301, University of Alberta - All Rights Reserved.
 *  You may use, distribute or modify this code under terms and conditions of Code
 *  of Student Behaviour at
 *  University of Alberta.
 *  You can find a copy of the license in this project. Otherwise, please contact patenio@ualberta.ca
 */

package ca.ualberta.cs.patenio_subbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Represents a subscription.
 *
 * @author patenio
 */

public class Subscription {

    private String name;
    private Date date;
    private double monthlycharge;
    private String comment;

    /**
     * Constructor with comment
     * @param name - name of subscription
     * @param date - date of subscription (yyyy-mm-dd)
     * @param monthlycharge - monthly charge of subscription (nonnegative)
     * @param comment - comment of subscription
     */
    public Subscription(String name, Date date, double monthlycharge, String comment){
        this.name = name;
        this.date = date;
        this.monthlycharge = monthlycharge;
        this.comment = comment;
    }

    /**
     * Constructor without comment
     * @param name - name of subscription
     * @param date - date of subscription (yyyy-mm-dd)
     * @param monthlycharge - monthly charge of subscription (nonnegative)
     */
    public Subscription(String name, Date date, double monthlycharge){
        this.name = name;
        this.date = date;
        this.monthlycharge = monthlycharge;
        this.comment = "";
    }

    /**
     * Set a name for subscription.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns current name of subscription.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a date for subscription.
     * @param date - date of subscription
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns current date of subscription.
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets montly charge for subscription.
     * @param monthlycharge - monthly charge of subscription
     */
    public void setMonthlyCharge(double monthlycharge) {
        this.monthlycharge = monthlycharge;
    }

    /**
     * Returns monthly charge of current subscription.
     * @return monthly charge
     */
    public double getMonthlyCharge() {
        return monthlycharge;
    }

    /**
     * Sets a comment for subscription.
     * @param comment - the comment of a subscription
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Returns comment of current subscription.
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Returns String representation of subscription.
     * @return
     */
    public String toString(){
        return "name: "+name +", date: "+ new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date).toString()+ ", monthly charge: "+monthlycharge+", comment: "+comment;
    }
}

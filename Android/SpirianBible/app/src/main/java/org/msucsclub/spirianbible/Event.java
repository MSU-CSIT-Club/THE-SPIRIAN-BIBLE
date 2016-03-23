package org.msucsclub.spirianbible;

import java.util.Date;

/**
 * Created by patneedham on 3/23/16.
 */
public class Event {

    private Date startDate, endDate;
    private String name;

    public Event(Date startDate, Date endDate, String name) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getName() {
        return name;
    }
}

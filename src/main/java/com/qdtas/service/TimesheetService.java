package com.qdtas.service;

import com.qdtas.entity.Timesheet;

import java.util.List;

public interface TimesheetService {

    public Timesheet addTimesheet(Timesheet ts);

    public Timesheet getTimesheetById(long tsId);

    public List<Timesheet> getTimesheetByEmployeeId(long empId , int pgn, int size);


        public void deleteById(long tsId);

    public Timesheet updateTimesheet(long tsId, Timesheet ts);

    public int getTotalCount();


}

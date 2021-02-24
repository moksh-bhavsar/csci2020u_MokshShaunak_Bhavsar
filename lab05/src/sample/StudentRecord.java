package sample;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentRecord{
    public final StringProperty studentID = new SimpleStringProperty();
    public final SimpleFloatProperty midterm = new SimpleFloatProperty();
    public final SimpleFloatProperty assignments = new SimpleFloatProperty();
    public final SimpleFloatProperty finalExam = new SimpleFloatProperty();
    public final SimpleFloatProperty finalMark = new SimpleFloatProperty();
    public final StringProperty finalGrade = new SimpleStringProperty();

    public final String getFinalGrade(){
        return finalGrade.get();
    }

    public final void setFinalGrade(String grade){
        finalGrade.set(grade);
    }

    public final String getStudentID(){
        return studentID.get();
    }

    public final void setStudentID(String sid){
        studentID.set(sid);
    }

    public final Float getMidterm(){
        return midterm.get();
    }

    public final void setMidterm(Float midtermMarks){
        midterm.set(midtermMarks);
    }

    public final Float getAssignments(){
        return assignments.get();
    }

    public final void setAssignments(Float assignment){
        assignments.set(assignment);
    }

    public final Float getFinalExam(){
        return finalExam.get();
    }

    public final void setFinalExam(Float fExam){
        finalExam.set(fExam);
    }

    public final Float getFinalMark(){
        return finalMark.get();
    }

    public final void setFinalMark(Float fMark){
        finalMark.set(fMark);
    }

    public StudentRecord(String SID, float assignmentsMarks, float midtermMarks, float finalExamMarks){
        this.setStudentID(SID);
        this.setMidterm(midtermMarks);
        this.setAssignments(assignmentsMarks);
        this.setFinalExam(finalExamMarks);
        calculateLetterGrade();
    }

    public void calculateLetterGrade(){
        Float totalAssignments = (20*this.getAssignments());
        Float totalMidterm = (30*this.getMidterm());
        Float totalFinalExam = (50*this.getFinalExam());
        Float totalSum = totalAssignments + totalMidterm + totalFinalExam;
        this.setFinalMark(totalSum/100f);
        if ((this.getFinalMark() <=100) && (this.getFinalMark() >= 80)){
            this.setFinalGrade("A");
        }
        else if((this.getFinalMark() <= 79) && (this.getFinalMark() >= 70)){
            this.setFinalGrade("B");
        }
        else if ((this.getFinalMark() <= 69) && (this.getFinalMark() >= 60)){
            this.setFinalGrade("C");
        }
        else if ((this.getFinalMark() <= 59) && (this.getFinalMark() >= 50)){
            this.setFinalGrade("D");
        }
        else{
            this.setFinalGrade("F");
        }
    }

}
/*
Classroom Attendance System

Teacher thread:

Takes attendance

Student thread:

Waits until attendance starts
Requirements
Students should not proceed immediately.
Students must wait.
Teacher gives signal.
Students continue after signal.

Expected Flow:

Student Waiting...

Teacher Started Attendance

Student Marked Present
Concepts Tested
wait()
notifyAll()


Instructions - 
Create Class Classroom

    Variable:
        attendanceStarted = false

    synchronized method waitForAttendance()

        While attendanceStarted is false

            Print:
                "Student Waiting..."

            wait()

        Print:
            "Student Marked Present"


    synchronized method startAttendance()

        attendanceStarted = true

        Print:
            "Teacher Started Attendance"

        notifyAll()


Create Class TeacherThread

    Classroom classroom

    run()

        classroom.startAttendance()


Create Class StudentThread

    Classroom classroom

    run()

        classroom.waitForAttendance()


Main Method

    Create Classroom object

    Create multiple Student threads

    Create Teacher thread

    Start Student threads

    Wait for a few seconds

    Start Teacher thread
Thread Coordination
Synchronization
*/
class Classroom {
    boolean attendanceStarted = false;

    public synchronized void waitForAttendance() {
        while (!attendanceStarted) {
            System.out.println("Student Waiting...");
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Student Marked Present");
    }

    public synchronized void startAttendance() {
        attendanceStarted = true;
        System.out.println("Teacher Started Attendance");
        notifyAll();
    }
}

class TeacherThread extends Thread {
    Classroom classroom;

    public TeacherThread(Classroom classroom) {
        this.classroom = classroom;
    }

    public void run() {
        classroom.startAttendance();
    }
}

class StudentThread extends Thread {
    Classroom classroom;

    public StudentThread(Classroom classroom) {
        this.classroom = classroom;
    }

    public void run() {
        classroom.waitForAttendance();
    }
}

public class ClassroomAttendanceSystem {
    public static void main(String[] args) {
        Classroom classroom = new Classroom();

        StudentThread student1 = new StudentThread(classroom);
        StudentThread student2 = new StudentThread(classroom);
        StudentThread student3 = new StudentThread(classroom);
        
        TeacherThread teacher = new TeacherThread(classroom);

        student1.start();
        student2.start();
        student3.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        teacher.start();
    }
}

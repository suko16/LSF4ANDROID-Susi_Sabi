package de.ur.mi.lsf4android;


public class Course {

    // variables
    private String title;
    private String begin;
    private String end;
    private String number;
    private String html;
    private long id;
    private int group;

    //creates multiple constructors for different usages

    public Course(String begin, String end, String number, String title, String html) {
        this.title = title;
        this.begin = begin;
        this.end = end;
        this.number = number;
        this.html = html;

    }

    public Course(String titel, String number, long id, String html){
        this.title = titel;
        this.id = id;
        this.number = number;
        this.html = html;
    }

    public Course (String day, String time, String room, String teacher, int group){
        title = day;
        begin = time;
        end = room;
        number = teacher;
        this.group = group;

    }

    public Course(){
        Course v = new Course("", "", "", "", "");
    }

    //Getter and Setter

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setNumber(String number) {this.number = number;}

    public String getTitle() {return title;   }

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }

    public String getNumber() {return number;}

    public String getHtml() {return html;}

    public void setHtml(String html) {this.html = html;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getGroup() {return group;}

    public void setGroup(int group) {this.group = group;}
}

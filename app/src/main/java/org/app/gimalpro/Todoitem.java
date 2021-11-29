package org.app.gimalpro;

public class Todoitem {

    private int NUMBER;
    private String ID;
    private String title;
    private String content;
    private String writedate;
    private String futuredate;
    private int switch_chek;

    public int getSwitch_chek() {
        return switch_chek;
    }

    public void setSwitch_chek(int switch_chek) {
        this.switch_chek = switch_chek;
    }

    public String getFuturedate() {
        return futuredate;
    }

    public void setFuturedate(String futuredate) {
        this.futuredate = futuredate;
    }

    public int getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(int NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWritedate() {
        return writedate;
    }

    public void setWritedate(String writedate) {
        this.writedate = writedate;
    }

    public Todoitem() {
    }
}

//fertig
//hat Sabi gebaut


package de.ur.mi.lsf4android;


public class Veranstaltung {
    // Variablen anlegen
    public String titel;
    public String beginn;
    public String ende;
    public String number;
    public String html;
    public long id;


    public Veranstaltung(String beginn, String ende, String number, String titel, String html) {
        this.titel = titel;
        this.beginn = beginn;
        this.ende = ende;
        this.number = number;
        this.html = html;

    }

    public Veranstaltung(String titel, String number, long id, String html){
        this.titel = titel;
        this.id = id;
        this.number = number;
        this.html = html;
    }

    public Veranstaltung(){
        Veranstaltung v = new Veranstaltung("", "", "", "", "");
    }


    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setBeginn(String beginn) {
        this.beginn = beginn;
    }

    public void setEnde(String ende) {
        this.ende = ende;
    }

    public void setNumber(String number) {this.number = number;}

    public String getTitel() {return titel;   }

    public String getBeginn() {
        return beginn;
    }

    public String getEnde() {
        return ende;
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
}

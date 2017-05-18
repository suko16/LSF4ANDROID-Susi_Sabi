//verglichen
//hat Sabi gebaut

package de.ur.mi.lsf4android;


public class EigeneV_Objekt {

    public long id;
    public String titel;
    public String number;
    public String html;

    public EigeneV_Objekt(String titel, String number, long id, String html){
        this.titel = titel;
        this.id = id;
        this.number = number;
        this.html = html;
    }


    public String getTitel() {return titel;   }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getNumber(){return number;}

    public void setNumber(String number) {this.number = number;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHtml(){
        return html;
    }



}

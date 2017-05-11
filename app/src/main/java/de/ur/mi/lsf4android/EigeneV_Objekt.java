//verglichen
//hat Sabi gebaut

package de.ur.mi.lsf4android;

/**
 * Created by Sabi on 25.04.2017.
 */

public class EigeneV_Objekt {

    public long id;
    public String titel;
    public String number;

    public EigeneV_Objekt(String titel, String number, long id){
        this.titel = titel;
        this.id = id;
        this.number = number;
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


    @Override
    public String toString() {
        String output = id+ " " + number + " " + titel;
        return output;
    }

}

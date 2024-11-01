public class Artikler {
    public static void main(String[] args) {
        ArtikelTest s = new ArtikelTest();
        s.main(args);
    }
}

class Forlag {
    private String navn; 
    private String sted; 

    public Forlag (String navn, String sted) { // Konstruktør med navn og sted som parametre 
        this.navn = navn; 
        this.sted = sted; 
    }
    
    public String toString() {
        return "Forlaget " + navn + ", " + sted; 
    } 
}

class Tidsskrift {
    private String titel; 
    private Forlag forlag; 
    private String issn;

    public Tidsskrift (String titel, Forlag forlag, String issn) { // Tidsskrift med titel som parameter 
            this.titel = titel; 
            this.issn = issn; 
            this.forlag = forlag; 
        }
    public void setIssn (String issn) {
        this.issn = issn; 
    }
    public void setForlag (Forlag forlag) {
        this.forlag = forlag; 
    }
   
    public String getTitel() {
        return titel; 
    }

public String toString() {
    return titel + ", fra " + forlag + ", " + (issn == "" ? "ISSN-nummeret kendes ikke" : issn);
}
}
class Artikel {
    private String [] forfattere; 
    private String titelArtikel; 
    private Tidsskrift tidsskrift; 
    private String [] referenceliste; 

    public Artikel (String titelArtikel, String [] forfattere, Tidsskrift tidsskrift, String [] referenceliste) { // hvis man vil sætte referencelist til start
    this.forfattere  = forfattere; 
    this.titelArtikel = titelArtikel; 
    this.referenceliste = referenceliste;
    this.tidsskrift = tidsskrift; 
    }

    public Artikel (String titelArtikel, String [] forfattere, Tidsskrift tidsskrift) { // hvis referencelisten skal være tom
        this.forfattere  = forfattere; 
        this.titelArtikel = titelArtikel;
        this.tidsskrift = tidsskrift;  
    }
    public void setReferencer (String [] referenceliste) {
    this.referenceliste = referenceliste; 

    }
    public String toString() {
        String temp = "";
        for (int i = 0; i < forfattere.length; i++) {
            temp += forfattere[i]; 
            if (i < forfattere.length-1) {
                temp += " & ";
            }
        }
        temp += ": \"" + titelArtikel +"\" " + tidsskrift.getTitel();
        return temp;
    }
}


class ArtikelTest {
    public static void main(String[] args) {
        Forlag UP = new Forlag ("University Press", "Denmark"); 
        Tidsskrift Tidsskrift1 = new Tidsskrift ("Journal of Logic", UP, ""); 
        Tidsskrift Tidsskrift2 = new Tidsskrift ("Brain", UP, "");
        String [] ref1 = {"B"} , forfattere1  = {"A. Abe.", "A. Turing"}; 
        String [] forfattere2 = {"B. Bim"}; 
        Artikel a = new Artikel("A",forfattere1,Tidsskrift1,ref1);
        Artikel b = new Artikel("B",forfattere2,Tidsskrift1);
        System.out.println(UP);
        System.out.println(Tidsskrift1);
        System.out.println(Tidsskrift2);
        System.out.println(a);
        System.out.println(b);
        
    }
}
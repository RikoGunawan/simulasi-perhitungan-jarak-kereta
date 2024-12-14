
package project.rikogunawan;

public class PerjalananModel {
    private String idperjalanan;
    private String idkereta;
    private int waktutotal;
    private double jaraktotal;
    
    //Properti Lokal
    private String kotaasal, kotatujuan;

    // Default constructor
    public PerjalananModel() {}
    
    // Constructor
    public PerjalananModel(String idperjalanan, String idkereta, int waktutotal, double jaraktotal, String kotaasal, String kotatujuan) {
        this.idperjalanan = idperjalanan;
        this.idkereta = idkereta;
        this.waktutotal = waktutotal;
        this.jaraktotal = jaraktotal;
        this.kotaasal = kotaasal;
        this.kotatujuan = kotatujuan;
    }
    
    public String getIdperjalanan() {
        return idperjalanan;
    }

    public void setIdperjalanan(String idperjalanan) {
        this.idperjalanan = idperjalanan;
    }

    public String getIdkereta() {
        return idkereta;
    }

    public void setIdkereta(String idkereta) {
        this.idkereta = idkereta;
    }

    public int getWaktutotal() {
        return waktutotal;
    }

    public void setWaktutotal(int waktutotal) {
        this.waktutotal = waktutotal;
    }

    public double getJaraktotal() {
        return jaraktotal;
    }

    public void setJaraktotal(double jaraktotal) {
        this.jaraktotal = jaraktotal;
    }

    public String getKotaasal() {
        return kotaasal;
    }

    public void setKotaasal(String kotaasal) {
        this.kotaasal = kotaasal;
    }

    public String getKotatujuan() {
        return kotatujuan;
    }

    public void setKotatujuan(String kotatujuan) {
        this.kotatujuan = kotatujuan;
    }

}

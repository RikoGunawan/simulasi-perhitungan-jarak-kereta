
package project.rikogunawan;

public class RuteModel {
    private String idrute, idperjalanan, idstasiunasal, idstasiuntujuan;
    private int urut, waktu;
    private double jarak;

    //Properti Lokal
    private String namastasiunasal;  
    private String namastasiuntujuan; 

    public RuteModel() {
    }

     public RuteModel(String idrute, String idperjalanan, String namastasiunasal, String namastasiuntujuan, int urut, int waktu, double jarak) {
        this.idrute = idrute;
        this.idperjalanan = idperjalanan;
        this.namastasiunasal = namastasiunasal;
        this.namastasiuntujuan = namastasiuntujuan;
        this.urut = urut;
        this.waktu = waktu;
        this.jarak = jarak;
    }
    
    public String getIdrute() {
        return idrute;
    }

    public void setIdrute(String idrute) {
        this.idrute = idrute;
    }

    public String getIdperjalanan() {
        return idperjalanan;
    }

    public void setIdperjalanan(String idperjalanan) {
        this.idperjalanan = idperjalanan;
    }

    
    public String getIdstasiunasal() {
        return idstasiunasal;
    }

    public void setIdstasiunasal(String idstasiunasal) {
        this.idstasiunasal = idstasiunasal;
    }

    public String getIdstasiuntujuan() {
        return idstasiuntujuan;
    }

    public void setIdstasiuntujuan(String idstasiuntujuan) {
        this.idstasiuntujuan = idstasiuntujuan;
    }

    public int getUrut() {
        return urut;
    }

    public void setUrut(int urut) {
        this.urut = urut;
    }

    public int getWaktu() {
        return waktu;
    }

    public void setWaktu(int waktu) {
        this.waktu = waktu;
    }

    public double getJarak() {
        return jarak;
    }

    public void setJarak(double jarak) {
        this.jarak = jarak;
    }

    public String getNamastasiunasal() {
        return namastasiunasal;
    }

    public void setNamastasiunasal(String namastasiunasal) {
        this.namastasiunasal = namastasiunasal;
    }

    public String getNamastasiuntujuan() {
        return namastasiuntujuan;
    }

    public void setNamastasiuntujuan(String namastasiuntujuan) {
        this.namastasiuntujuan = namastasiuntujuan;
    }
    
    
}

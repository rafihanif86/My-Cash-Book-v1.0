package com.example.mycashbookv10;

public class Model {
    private int id;
    private String tanggal, nominal, keterangan, jenis;

    public Model(int id, String tanggal, String nominal, String keterangan, String jenis) {
        this.id = id;
        this.tanggal = tanggal;
        this.nominal = nominal;
        this.keterangan = keterangan;
        this.jenis = jenis;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}

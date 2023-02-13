package com.nds.ekirahardian.EkirahardianNDS;

import java.time.LocalDate;

public class Karyawan {
    public Karyawan() {}
    
    private String id;
    private String nama;
    private LocalDate tanggalmasuk;
    private String nohp;
    private int limitreimbursement;
    private LocalDate createddate;
    private LocalDate updateddate;
    
    public Karyawan(
            String id,
            String nama,
            LocalDate tanggalmasuk,
            String nohp,
            int limitreimbursement,
            LocalDate createddate,
            LocalDate updateddate
    ) {
      this.id = id;
      this.nama = nama;
      this.tanggalmasuk = tanggalmasuk;
      this.nohp = nohp;
      this.limitreimbursement = limitreimbursement;
      this.createddate = createddate;
      this.updateddate = updateddate;
    }
    
    public void set_id(String id) {
      this.id = id;
    } public String get_id() {
      return id;
    }
    
    public void set_nama(String nama) {
      this.nama = nama;
    } public String get_nama() {
      return nama;
    }
    
    public void set_tanggalmasuk(LocalDate tanggalmasuk) {
      this.tanggalmasuk = tanggalmasuk;
    } public LocalDate get_tanggalmasuk() {
      return tanggalmasuk;
    }
    
    public void set_nohp(String nohp) {
      this.nohp = nohp;
    } public String get_nohp() {
      return nohp;
    }
    
    public void set_limitreimbursement(int limitreimbursement) {
      this.limitreimbursement = limitreimbursement;
    } public int get_limitreimbursement() {
      return limitreimbursement;
    }
    
    public void set_createddate(LocalDate createddate) {
      this.createddate = createddate;
    } public LocalDate get_createddate() {
      return createddate;
    }
    
    public void set_updateddate(LocalDate updateddate) {
      this.updateddate = updateddate;
    } public LocalDate get_updateddate() {
      return updateddate;
    }
    
    @Override
    public String toString() {
        return "{" + //JSON
                    "\"id\": \"" + id + "\"" + ", " +
                    "\"nama\": \"" + nama + "\"" + ", " +
                    "\"tanggalmasuk\": \"" + tanggalmasuk + "\"" + ", " +
                    "\"nohp\": \"" + nohp + "\"" + ", " +
                    "\"limitreimbursement\": \"" + limitreimbursement + "\"" + ", " +
                    "\"createddate\": \"" + createddate + "\"" + ", " +
                    "\"updateddate\": \"" + updateddate + "\"" + ", " +
                "}";
    }
}
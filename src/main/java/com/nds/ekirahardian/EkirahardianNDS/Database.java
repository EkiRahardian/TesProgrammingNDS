package com.nds.ekirahardian.EkirahardianNDS;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Database {
    @Autowired
    private JdbcTemplate jdbc;
    
    public int tambahKaryawan(Karyawan karyawan) {
        String sql = "INSERT INTO information_schema.karyawan (id, nama, tanggalmasuk, nohp, limitreimbursement, createddate, updateddate) VALUES(?,?,?,?,?,?,?)";
        int result;
        try {
            result = jdbc.update(sql,
            new Object[] {
                karyawan.get_id(),
                karyawan.get_nama(),
                karyawan.get_tanggalmasuk(),
                karyawan.get_nohp(),
                karyawan.get_limitreimbursement(),
                karyawan.get_createddate(),
                karyawan.get_updateddate()
            });
        }
        catch(Exception e) {
            result = 0;
        }
        return result;
    }
    
    public int ambilIdKaryawanTerakhir() {
        String sql = "SELECT id FROM information_schema.id_karyawan_terakhir WHERE tipe='id'";
        return jdbc.queryForObject(sql, int.class);
    }
    
    public int ubahIdKaryawanTerakhir(int id) {
        String sql = "UPDATE information_schema.id_karyawan_terakhir SET id=? WHERE tipe='id'";
        return jdbc.update(sql, id);
    }
    
    public Karyawan cariKaryawanDenganID(String id) {
        String sql = "SELECT * FROM information_schema.karyawan WHERE id=?";
        return jdbc.queryForObject(sql, new KaryawanMapper(),  new Object[] {id});
    }
    public List<Karyawan> cariKaryawanDenganNamaTanggalNoHP(String nama, String tanggalmasuk, String nohp) {
        nama = "%" + nama + "%";
        nohp = "%" + nohp + "%";
        String sql;
        if (tanggalmasuk.isBlank()) {
            sql = "SELECT * from information_schema.karyawan WHERE (nama ILIKE ?) AND (nohp ILIKE ?)";
            return jdbc.query(sql,new KaryawanMapper(), new Object[] {nama, nohp});
        }
        else {
            sql = "SELECT * from information_schema.karyawan WHERE (nama ILIKE ?) AND (tanggalmasuk = CAST(? as date)) AND (nohp ILIKE ?)";
            return jdbc.query(sql,new KaryawanMapper(), new Object[] {nama, tanggalmasuk, nohp});
        }
    }
    public List<Karyawan> cariSemuaKaryawan() {
        String sql = "SELECT * FROM information_schema.karyawan";
        return jdbc.query(sql, new KaryawanMapper());
    }
    public int ubahKaryawan(Karyawan karyawan) {
        String sql = "UPDATE information_schema.karyawan SET nama=?, tanggalmasuk=?, nohp=?, limitreimbursement=?, updateddate=? WHERE id=?";
        int result;
        try {
            result = jdbc.update(sql,
            new Object[] {
                karyawan.get_nama(),
                karyawan.get_tanggalmasuk(),
                karyawan.get_nohp(),
                karyawan.get_limitreimbursement(),
                karyawan.get_updateddate(),
                karyawan.get_id()
            });
        }
        catch(Exception e) {
            result = 0;
        }
        return result;
  }
    public int hapusKaryawanDenganID(String id) {
        String sql = "DELETE FROM information_schema.karyawan WHERE id=?";
        return jdbc.update(sql, new Object[] {id});
    }
}
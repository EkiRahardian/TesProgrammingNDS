package com.nds.ekirahardian.EkirahardianNDS;

import static java.lang.Integer.parseInt;
import java.time.LocalDate;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.jsoup.nodes.*;
import org.jsoup.parser.*;

@Controller
public class Index {
    
    @Autowired
    Database database;
    
    private String boxNotifikasi = "";
    private String dataKaryawan = "";
    private boolean isBlankOrNull (String string) {
        if (string == null) {
            return true;
        }
        else return string.isBlank();
    }
    
    @GetMapping({"/"})
    public String getDataKaryawan(
        @RequestParam(name = "nama", required = false) String nama,
        @RequestParam(name = "tanggalmasuk", required = false) String tanggalmasuk,
        @RequestParam(name = "nohp", required = false) String nohp, 
        Model model
    ) {
        if (nama == null){
            nama = "";
        }
        if (tanggalmasuk == null){
            tanggalmasuk = "";
        }
        if (nohp == null){
            nohp = ""; 
        }
        List<Karyawan> listDataKaryawan;
        if (nama == "" && tanggalmasuk == "" && nohp == "") {
            listDataKaryawan = new ArrayList<>();
        }
        else {
            listDataKaryawan = database.cariKaryawanDenganNamaTanggalNoHP(nama, tanggalmasuk, nohp);
        }
        dataKaryawan = "";
        for (int i = 0; i < listDataKaryawan.size(); i++) {
            Karyawan karyawanIteration = listDataKaryawan.get(i);
            dataKaryawan += karyawanIteration.toString() + ", ";
        }
        model.addAttribute("dataKaryawan", dataKaryawan);
        dataKaryawan = "";
        return "index"; //index.html
    }
    @PostMapping({"/"})
    public String deleteKaryawan(
        @RequestParam(name = "delete", required = false) String delete,
        
        @RequestParam(name = "update", required = false) String update,
        @RequestParam(name = "update_nama", required = false) String update_nama,
        @RequestParam(name = "update_tanggalmasuk", required = false) String update_tanggalmasuk,
        @RequestParam(name = "update_nohp", required = false) String update_nohp,
        @RequestParam(name = "update_limitreimbursement", required = false) String update_limitreimbursement,
        
        @RequestParam(name = "create", required = false) String create,
        @RequestParam(name = "create_nama", required = false) String create_nama,
        @RequestParam(name = "create_tanggalmasuk", required = false) String create_tanggalmasuk,
        @RequestParam(name = "create_nohp", required = false) String create_nohp,
        @RequestParam(name = "create_limitreimbursement", required = false) String create_limitreimbursement,
        Model model
    ) {
        LocalDate tanggalSekarang = LocalDate.now();
        LocalDate bulan2SebelumnTanggalSekarang = tanggalSekarang.minusMonths(2);
        LocalDate bulan3SetelahTanggalSekarang = tanggalSekarang.plusMonths(3);
        if (isBlankOrNull(delete) == false) {
            int terhapus = database.hapusKaryawanDenganID(delete);
            Element divHasilHapus = new Element(Tag.valueOf("div"), "");
            if (terhapus == 1) {
                divHasilHapus
                    .attr("class", "alert alert-success")
                    .text("Data berhasil dihapus.");
            }
            else {
                divHasilHapus
                    .attr("class", "alert alert-danger")
                    .text("Data gagal dihapus, data tersebut mungkin sudah tidak ada.");
            }
            boxNotifikasi = divHasilHapus.toString();
        }
        else if (
            isBlankOrNull(update) == false &&
            isBlankOrNull(update_nama) == false &&
            isBlankOrNull(update_tanggalmasuk) == false &&
            isBlankOrNull(update_nohp) == false &&
            isBlankOrNull(update_limitreimbursement) == false
        ) {
            Element divHasilUbah = new Element(Tag.valueOf("div"), "");
            LocalDate tanggalMasuk = LocalDate.parse(update_tanggalmasuk);
            if (tanggalMasuk.isAfter(bulan2SebelumnTanggalSekarang) && tanggalMasuk.isBefore(bulan3SetelahTanggalSekarang)) {
                Karyawan updateKaryawan = new Karyawan();
                updateKaryawan.set_nama(update_nama);
                updateKaryawan.set_tanggalmasuk(tanggalMasuk);
                updateKaryawan.set_nohp(update_nohp);
                updateKaryawan.set_limitreimbursement(parseInt(update_limitreimbursement));
                updateKaryawan.set_updateddate(tanggalSekarang);
                updateKaryawan.set_id(update);
                int terubah = database.ubahKaryawan(updateKaryawan);
                if (terubah == 1) {
                    divHasilUbah
                        .attr("class", "alert alert-success")
                        .text("Data berhasil diubah.");
                }
                else {
                    divHasilUbah
                        .attr("class", "alert alert-danger")
                        .text("Data gagal diubah, nama tidak boleh sama dengan orang lain.");
                }
            }
            else {
                divHasilUbah
                    .attr("class", "alert alert-danger")
                    .text("Data gagal diubah, tanggal masuk kerja tidak boleh lebih lama dari 2 bulan lalu atau lebih baru dari 3 bulan lagi.");
            }
            boxNotifikasi = divHasilUbah.toString();
        }
        else if (
            isBlankOrNull(create_nama) == false &&
            isBlankOrNull(create_tanggalmasuk) == false &&
            isBlankOrNull(create_nohp) == false &&
            isBlankOrNull(create_limitreimbursement) == false
        ) {
            Element divHasilBuat = new Element(Tag.valueOf("div"), "");
            LocalDate tanggalMasuk = LocalDate.parse(create_tanggalmasuk);
            if (tanggalMasuk.isAfter(bulan2SebelumnTanggalSekarang) && tanggalMasuk.isBefore(bulan3SetelahTanggalSekarang)) {
                int idKaryawanTerakhir = database.ambilIdKaryawanTerakhir();
                int idKaryawanSelanjutnya = idKaryawanTerakhir + 1;
                Karyawan karyawanBaru = new Karyawan();
                karyawanBaru.set_id("K-" + String.format("%03d", idKaryawanSelanjutnya));
                karyawanBaru.set_nama(create_nama);
                karyawanBaru.set_tanggalmasuk(tanggalMasuk);
                karyawanBaru.set_nohp(create_nohp);
                karyawanBaru.set_limitreimbursement(parseInt(create_limitreimbursement));
                karyawanBaru.set_createddate(tanggalSekarang);
                karyawanBaru.set_updateddate(tanggalSekarang);
                idKaryawanTerakhir = database.ubahIdKaryawanTerakhir(idKaryawanSelanjutnya);
                int ditambah = database.tambahKaryawan(karyawanBaru);
                if (ditambah == 1) {
                    divHasilBuat
                        .attr("class", "alert alert-success")
                        .text("Data berhasil dimasukkan.");
                }
                else {
                    idKaryawanTerakhir = database.ubahIdKaryawanTerakhir(idKaryawanSelanjutnya - 1);
                    divHasilBuat
                        .attr("class", "alert alert-danger")
                        .text("Data gagal dimasukkan, nama tidak boleh sama dengan orang lain.");
                }
            }
            else {
                divHasilBuat
                    .attr("class", "alert alert-danger")
                    .text("Data gagal dimasukkan, tanggal masuk kerja tidak boleh lebih lama dari 2 bulan lalu atau lebih baru dari 3 bulan lagi.");
            }
            boxNotifikasi = divHasilBuat.toString();
        }
        model.addAttribute("boxNotifikasi", boxNotifikasi);
        boxNotifikasi = "";
        return "index";
    }
}
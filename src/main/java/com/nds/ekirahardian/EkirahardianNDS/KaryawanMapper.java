package com.nds.ekirahardian.EkirahardianNDS;

import static java.lang.Integer.parseInt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;

public class KaryawanMapper implements RowMapper<Karyawan> {
    @Override
    public Karyawan mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Karyawan(
                rs.getString("id"),
                rs.getString("nama"),
                LocalDate.parse(rs.getString("tanggalmasuk")),
                rs.getString("nohp"),
                parseInt(rs.getString("limitreimbursement")),
                LocalDate.parse(rs.getString("createddate")),
                LocalDate.parse(rs.getString("updateddate"))
       );
   }
}
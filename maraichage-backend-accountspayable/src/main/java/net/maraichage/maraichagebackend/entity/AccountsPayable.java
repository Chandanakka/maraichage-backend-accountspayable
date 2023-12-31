package net.maraichage.maraichagebackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;

//import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_General_Ledger_Payable")
public class AccountsPayable {
    @Id
    private String glvoucherno;

    private Date gldate;
    private String glexpensetype;
    private String glpaidto;
    private double glpaidamount;


    @Lob
    @Column(nullable = true)
    private byte[] glimage;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String type;

}
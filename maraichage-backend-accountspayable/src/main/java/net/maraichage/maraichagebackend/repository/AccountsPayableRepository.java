package net.maraichage.maraichagebackend.repository;

import net.maraichage.maraichagebackend.entity.AccountsPayable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface AccountsPayableRepository extends JpaRepository<AccountsPayable, String> {
    List<AccountsPayable> findBygldateBetween(Date startDate, Date endDate);

}

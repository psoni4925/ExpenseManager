package in.pranjal.expensetrackerapi.repository;



import in.pranjal.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    //SELECT * FROM tbl_expenses WHERE userid = ? AND category= ?
    Page<Expense> findByUserIdAndCategory(Long id ,String category , Pageable page);

    //SELECT * FROM tbl_expenses WHERE name LIKE '%keyword%';
    Page<Expense> findByUserIdAndNameContaining(Long id, String name, Pageable page);

    //SELECT * FROM tbl_expenses WHERE date BETWEEN 'startDate' AND 'endDate'
    Page<Expense> findByUserIdAndDateBetween(Long id, Date startDate , Date endDate , Pageable page);

    Page<Expense> findByUserId(Long userId, Pageable page);

    Optional<Expense> findByUserIdAndId(Long userId, Long expenseId);

}

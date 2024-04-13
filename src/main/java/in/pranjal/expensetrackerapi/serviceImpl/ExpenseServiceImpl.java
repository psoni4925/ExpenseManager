package in.pranjal.expensetrackerapi.serviceImpl;

import in.pranjal.expensetrackerapi.entity.Expense;
import in.pranjal.expensetrackerapi.exception.ResourceNotFound;
import in.pranjal.expensetrackerapi.repository.ExpenseRepository;
import in.pranjal.expensetrackerapi.service.ExpenseService;
import in.pranjal.expensetrackerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public Page<Expense> getAllExpenses(Pageable page) {

        return expenseRepository.findByUserId(userService.getLoggedInUser().getId(), page);
    }

    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepository.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
        if(expense.isPresent()){
            return expense.get();
        }
        throw new ResourceNotFound("Expense is not found for the id " + id);
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense expense = getExpenseById(id);
        expenseRepository.delete(expense);
    }

    @Override
    public Expense saveExpenseDetail(Expense expense) {

        expense.setUser(userService.getLoggedInUser());
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpenseDetail(Long id, Expense expense) {

        Expense existingExpense = getExpenseById(id);
        expense.setName( expense.getName() != null ? expense.getName() : existingExpense.getName());
        expense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
        expense.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
        expense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
        expense.setDate(expense.getDate() != null ? expense.getDate(): existingExpense.getDate());
        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> readByCategory(String category, Pageable page) {
        return expenseRepository.findByUserIdAndCategory(userService.getLoggedInUser().getId(),category,page).toList();

    }

    @Override
    public List<Expense> readByName(String keyword, Pageable page) {
        return expenseRepository.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(),keyword , page).toList();
    }

    @Override
    public List<Expense> readByDate(Date startDate, Date endDate, Pageable page) {
        if(startDate == null)
            startDate = new Date(0);

        if(endDate == null)
            endDate = new Date(System.currentTimeMillis());

       return expenseRepository.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(),startDate,endDate,page).toList();
    }
}

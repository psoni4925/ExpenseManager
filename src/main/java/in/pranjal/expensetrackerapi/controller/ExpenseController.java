package in.pranjal.expensetrackerapi.controller;


import in.pranjal.expensetrackerapi.entity.Expense;
import in.pranjal.expensetrackerapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;


@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(Pageable page){
        return expenseService.getAllExpenses(page).toList();
    }


    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable Long id){
        return expenseService.getExpenseById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public Expense saveExpenseDetails(@Valid @RequestBody Expense expense){
        return expenseService.saveExpenseDetail(expense);
    }


   @PutMapping("/expenses/{id}")
   public Expense updateExpenseDetails(@PathVariable Long id, @RequestBody Expense expense){
        return expenseService.updateExpenseDetail(id, expense);
   }

   @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam("id") Long id){
         expenseService.deleteExpenseById(id);
    }

    @GetMapping("/expenses/category")
    public List<Expense> getExpenseByCategory(@RequestParam String category , Pageable page){
        return expenseService.readByCategory(category,page);
    }

    @GetMapping("/expenses/name")
    public List<Expense> getExpenseByName(@RequestParam String keyword , Pageable page){
        return expenseService.readByName(keyword,page);
    }

    @GetMapping("/expenses/date")
    public List<Expense> getAllExpenseByDate(
            @RequestParam(required = false)Date startDate,
            @RequestParam(required = false)Date endDate,
            Pageable page
            ){
        return expenseService.readByDate(startDate,endDate,page);
    }
}

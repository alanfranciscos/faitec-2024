package com.eventify.eventify.controller.event.Expense;

import com.eventify.eventify.dto.event.EventExpansesResponse;
import com.eventify.eventify.models.event.expense.Expense;
import com.eventify.eventify.port.service.event.expense.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("api/v1/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping()
    public ResponseEntity<List<Expense>> getEntities(){
        List<Expense> expenses = expenseService.findAll();
        return ResponseEntity.ok().body(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getEntityById(@PathVariable final int id){
        Expense expense = expenseService.findById(id);
        return ResponseEntity.ok().body(expense);
    }

//    @PostMapping()
//    public ResponseEntity<Expense> createEntity(@RequestBody final Expense data){
    @PostMapping()
    public ResponseEntity<Expense> createEntity(
        @RequestParam(value = "meetup_id", required = false) String meetup_id,
        @RequestParam(value = "cost", required = false) String cost,
        @RequestParam(value = "about", required = false) String about
        ){

        Expense expense = new Expense();
        expense.setMeetup_id(Integer.valueOf(meetup_id));
        expense.setCost(Double.parseDouble(cost));
        expense.setAbout(about);


        int id = expenseService.create(expense);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEntity(@PathVariable final int id, @RequestBody final Expense data){
        expenseService.update(id, data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Expense> deleteEntity(@PathVariable final int id){
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/expanses-account")
//    public ResponseEntity<EventExpansesResponse> getExpensesByAccountId(){
//        EventExpansesResponse expense = expenseService.getExpensesByAccountId();
//        return ResponseEntity.ok().body(expense);
//    }
}

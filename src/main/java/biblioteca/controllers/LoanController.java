package biblioteca.controllers;

import biblioteca.entities.Loan;
import biblioteca.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("loan")
public class LoanController {
    @Autowired
    private LoanService service;

    @PostMapping
    Loan create(@RequestBody Loan laon) {
        return service.create(laon);
    }

    @PutMapping
    Loan update(@RequestBody Loan laon) {
        return service.update(laon);
    }

    @GetMapping(path = "/list")
    List<Loan> listAll() {
        return service.getAll();
    }
}

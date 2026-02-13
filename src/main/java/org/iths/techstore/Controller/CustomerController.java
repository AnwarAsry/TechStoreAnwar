package org.iths.techstore.Controller;

import org.iths.techstore.Model.Customer;
import org.iths.techstore.Service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String getAllCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    @GetMapping("/new")
    public String addNewCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "add-customer";
    }

    @PostMapping
    public String addCustomer(@ModelAttribute Customer customer) {
        customerService.createCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id}")
    public String getOneCustomerById(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customer";
    }

    @GetMapping("/{id}/edit")
    public String updateExistingCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "edit-customer";
    }

    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute Customer customer) {
        customerService.updateCustomer(id, customer);
        return "redirect:/customers";
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}

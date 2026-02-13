package org.iths.techstore.Controller;

import org.iths.techstore.Model.Supplier;
import org.iths.techstore.Service.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // Root endpoint for suppliers
    @GetMapping
    public String showSupplierPage(Model model) {
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "suppliers";
    }

    // Endpoint to show the details of a specific supplier
    @GetMapping("/{id}")
    public String getSupplier(@PathVariable Long id, Model model) {
        model.addAttribute("supplier", supplierService.getSupplierById(id));
        return "supplier";
    }

    // Endpoint to show the form for creating a new supplier
    @GetMapping("/new")
    public String showNewSupplierForm() {
        return "new-supplier";
    }

    // Endpoint to handle the submission of the new supplier form
    @PostMapping
    public String createSupplier(@ModelAttribute Supplier supp) {
        Supplier supp2 = supplierService.createSupplier(supp);

        return "redirect:/suppliers/" + supp2.getId();
    }

    // Endpoint to show the form for editing an existing supplier
    @GetMapping("/{id}/edit")
    public String showEditSupplierForm(@PathVariable Long id, Model model) {
        model.addAttribute("supplier", supplierService.getSupplierById(id));
        return "edit-supplier";
    }

    // Endpoint to handle the submission of the edit supplier form
    @PutMapping("/{id}")
    public String updateSupplier(@PathVariable Long id, @ModelAttribute Supplier supp) {
        Supplier updatedSupplier = supplierService.updateSupplier(id, supp);
        return "redirect:/suppliers/" + updatedSupplier.getId();
    }

    // Endpoint to handle the deletion of a supplier
    @DeleteMapping("/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return "redirect:/suppliers";
    }
}

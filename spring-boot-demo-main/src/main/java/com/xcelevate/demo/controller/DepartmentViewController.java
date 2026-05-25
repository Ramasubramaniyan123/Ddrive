package com.xcelevate.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xcelevate.demo.entity.Department;
import com.xcelevate.demo.model.request.DepartmentRequest;
import com.xcelevate.demo.model.response.DepartmentResponse;
import com.xcelevate.demo.repository.DepartmentRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/departments")
public class DepartmentViewController {

    @Autowired
    private DepartmentRepository departmentRepository;

    private DepartmentResponse convertToResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .active(department.getActive())
                .build();
    }

    // Main departments listing page with search, filter, and pagination
    @GetMapping
    public String departments(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "all") String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {
        
        // Create pageable with sorting
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // Get all departments for filtering
        List<Department> allDepartments = departmentRepository.findAll();
        
        // Apply filters
        List<Department> filteredDepartments = allDepartments.stream()
                .filter(dept -> {
                    // Search filter
                    if (!search.isEmpty()) {
                        return dept.getName().toLowerCase().contains(search.toLowerCase()) ||
                               dept.getDescription().toLowerCase().contains(search.toLowerCase());
                    }
                    return true;
                })
                .filter(dept -> {
                    // Status filter
                    if ("active".equals(status)) {
                        return Boolean.TRUE.equals(dept.getActive());
                    } else if ("inactive".equals(status)) {
                        return Boolean.FALSE.equals(dept.getActive());
                    }
                    return true; // "all"
                })
                .collect(Collectors.toList());
        
        // Convert to responses
        List<DepartmentResponse> departmentResponses = filteredDepartments.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        
        // Calculate statistics
        long totalDepartments = allDepartments.size();
        long activeDepartments = allDepartments.stream()
                .mapToLong(dept -> Boolean.TRUE.equals(dept.getActive()) ? 1 : 0)
                .sum();
        long inactiveDepartments = totalDepartments - activeDepartments;
        
        // Add attributes to model
        model.addAttribute("departments", departmentResponses);
        model.addAttribute("currentSearch", search);
        model.addAttribute("currentStatus", status);
        model.addAttribute("currentPage", page);
        model.addAttribute("currentSize", size);
        model.addAttribute("currentSortBy", sortBy);
        model.addAttribute("currentSortDir", sortDir);
        
        // Statistics
        model.addAttribute("totalDepartments", totalDepartments);
        model.addAttribute("activeDepartments", activeDepartments);
        model.addAttribute("inactiveDepartments", inactiveDepartments);
        model.addAttribute("filteredCount", departmentResponses.size());
        
        return "departments";
    }

    // Department details page
    @GetMapping("/{id}")
    public String departmentDetails(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Department> departmentOpt = departmentRepository.findById(id);
        
        if (departmentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Department not found!");
            return "redirect:/departments";
        }
        
        Department department = departmentOpt.get();
        model.addAttribute("department", convertToResponse(department));
        
        // Add some additional statistics for this department
        // You could add related users count, creation date, etc. here
        Map<String, Object> stats = new HashMap<>();
        stats.put("createdDate", "N/A"); // You'd need to add timestamp fields to entity
        stats.put("lastModified", "N/A");
        model.addAttribute("departmentStats", stats);
        
        return "department-details";
    }

    // Show create department form
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("departmentRequest", new DepartmentRequest());
        model.addAttribute("isEdit", false);
        return "department-form";
    }

    // Handle create department form submission
    @PostMapping("/create")
    public String createDepartment(@Valid @ModelAttribute DepartmentRequest departmentRequest,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "department-form";
        }
        
        try {
            Department department = Department.builder()
                    .name(departmentRequest.getName())
                    .description(departmentRequest.getDescription())
                    .active(departmentRequest.getActive() != null ? departmentRequest.getActive() : true)
                    .build();
            
            departmentRepository.save(department);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Department '" + department.getName() + "' created successfully!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error creating department: " + e.getMessage());
        }
        
        return "redirect:/departments";
    }

    // Show edit department form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Department> departmentOpt = departmentRepository.findById(id);
        
        if (departmentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Department not found!");
            return "redirect:/departments";
        }
        
        Department department = departmentOpt.get();
        DepartmentRequest departmentRequest = DepartmentRequest.builder()
                .name(department.getName())
                .description(department.getDescription())
                .active(department.getActive())
                .build();
        
        model.addAttribute("departmentRequest", departmentRequest);
        model.addAttribute("departmentId", id);
        model.addAttribute("isEdit", true);
        
        return "department-form";
    }

    // Handle edit department form submission
    @PostMapping("/{id}/edit")
    public String updateDepartment(@PathVariable Long id,
                                 @Valid @ModelAttribute DepartmentRequest departmentRequest,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("departmentId", id);
            model.addAttribute("isEdit", true);
            return "department-form";
        }
        
        Optional<Department> departmentOpt = departmentRepository.findById(id);
        if (departmentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Department not found!");
            return "redirect:/departments";
        }
        
        try {
            Department department = departmentOpt.get();
            department.setName(departmentRequest.getName());
            department.setDescription(departmentRequest.getDescription());
            department.setActive(departmentRequest.getActive());
            
            departmentRepository.save(department);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Department '" + department.getName() + "' updated successfully!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error updating department: " + e.getMessage());
        }
        
        return "redirect:/departments";
    }

    // Toggle department status
    @PostMapping("/{id}/toggle")
    public String toggleDepartmentStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Department> departmentOpt = departmentRepository.findById(id);
        
        if (departmentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Department not found!");
            return "redirect:/departments";
        }
        
        try {
            Department department = departmentOpt.get();
            department.setActive(!Boolean.TRUE.equals(department.getActive()));
            departmentRepository.save(department);
            
            String status = Boolean.TRUE.equals(department.getActive()) ? "activated" : "deactivated";
            redirectAttributes.addFlashAttribute("successMessage", 
                "Department '" + department.getName() + "' " + status + " successfully!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error toggling department status: " + e.getMessage());
        }
        
        return "redirect:/departments";
    }

    // Delete department
    @PostMapping("/{id}/delete")
    public String deleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Department> departmentOpt = departmentRepository.findById(id);
        
        if (departmentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Department not found!");
            return "redirect:/departments";
        }
        
        try {
            Department department = departmentOpt.get();
            String departmentName = department.getName();
            departmentRepository.deleteById(id);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Department '" + departmentName + "' deleted successfully!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error deleting department: " + e.getMessage());
        }
        
        return "redirect:/departments";
    }

    // Department statistics API endpoint for AJAX calls
    @GetMapping("/stats")
    @ResponseBody
    public Map<String, Object> getDepartmentStats() {
        List<Department> allDepartments = departmentRepository.findAll();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", allDepartments.size());
        stats.put("active", allDepartments.stream()
                .mapToLong(dept -> Boolean.TRUE.equals(dept.getActive()) ? 1 : 0)
                .sum());
        stats.put("inactive", allDepartments.size() - (Long) stats.get("active"));
        
        return stats;
    }


}

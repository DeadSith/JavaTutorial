package com.sith.spring_lab.controllers;

import com.sith.spring_lab.models.Department;
import com.sith.spring_lab.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/{id}")
    public String getDepartment(int id, ModelMap map) {
        map.addAttribute("id", id);
        map.addAttribute("department", departmentService.findById(id));
        return "department/index";
    }

    @GetMapping("/add")
    public String getAddForm() {
        return "department/add";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(int id, ModelMap map) {
        map.addAttribute("id", id);
        map.addAttribute("department", departmentService.findById(id));
        return "department/edit";
    }

    public String addDepartment(int id, Department d, ModelMap map) {
        try {
            departmentService.save(d);
            return "redirect:/department/" + id;
        } catch (Exception ignored) {
            map.addAttribute("error", true);
            return "department/add";
        }
    }

    //todo: edit, delete, rewrite services
}

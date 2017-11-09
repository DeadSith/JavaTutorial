package com.sith.spring_lab.controllers;

import com.sith.spring_lab.models.Department;
import com.sith.spring_lab.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/{id}")
    public String getDepartment(@PathVariable int id, ModelMap map) {
        map.addAttribute("id", id);
        Department d = departmentService.findById(id);
        if (d == null) {
            return "redirect:/error";
        }
        map.addAttribute("department", d);
        return "department/index";
    }

    @GetMapping("/add")
    public String getAddForm() {
        return "department/add";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable int id, ModelMap map) {
        map.addAttribute("id", id);
        Department d = departmentService.findById(id);
        if (d == null) {
            return "redirect:/error";
        }
        map.addAttribute("department", d);
        return "department/edit";
    }

    @PostMapping("/add")
    public String addDepartment(Department d, BindingResult result, ModelMap map) {
        try {
            departmentService.save(d);
            return "redirect:/department/" + d.getId();
        } catch (Exception ignored) {
            System.err.println(ignored);
            map.addAttribute("error", true);
            return "department/add";
        }
    }

    @PostMapping("/edit/{id}")
    public String editDepartment(Department d, @PathVariable int id, ModelMap map) {
        try {
            Department entity = departmentService.findById(id);
            if (entity == null) {
                return "redirect:/department/add/";
            }
            entity.setName(d.getName());
            entity.setPhoneNumber(d.getPhoneNumber());
            departmentService.update(entity);
            return "redirect:/department/" + id;
        } catch (Exception ignored) {
            map.addAttribute("error", true);
            return "department/edit/" + id;
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable int id, ModelMap map) {
        try {
            departmentService.deleteById(id);
        } catch (Exception ignored) {
        }
        return "redirect:/home";
    }
    //todo: edit, delete, rewrite services
}
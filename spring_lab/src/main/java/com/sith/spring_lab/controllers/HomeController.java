package com.sith.spring_lab.controllers;

import com.sith.spring_lab.models.Department;
import com.sith.spring_lab.services.DepartmentService;
import com.sith.spring_lab.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    DepartmentService departmentService;
    @Autowired
    FacultyService facultyService;

    @GetMapping("/home")
    public String listDepartments(ModelMap model) {
        List<Department> departments = departmentService.getAll();
        model.addAttribute("departments", departments);
        return "home/index";
    }

    @GetMapping("/error")
    public String showError(ModelMap model){
        return "home/error";
    }

    @PostMapping("/find/{name}")
    public String find(@PathVariable String name, ModelMap model) {
        model.addAttribute("departments",departmentService.findByName(name));
        model.addAttribute("faculties",facultyService.findByName(name));
        return "home/find";
    }

    //@GetMapping("/dump/get")

}

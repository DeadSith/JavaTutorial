package com.sith.spring_lab.controllers;

import com.sith.spring_lab.models.Department;
import com.sith.spring_lab.models.Faculty;
import com.sith.spring_lab.services.DepartmentService;
import com.sith.spring_lab.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    FacultyService facultyService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/{id}")
    public String getFaculty(@PathVariable int id, ModelMap map) {
        map.addAttribute("id", id);
        try {
            Faculty f = facultyService.findById(id);
            map.addAttribute("faculty", f);
            map.addAttribute("department", f.getDepartment());
            return "faculty/index";
        } catch (Exception ignored) {
            return "redirect:/error";
        }
    }

    @GetMapping("/add/{id}")
    public String getAddForm(@PathVariable int id, ModelMap map) {
        Department d = departmentService.findById(id);
        if (d == null) {
            return "redirect:/error";
        }
        map.addAttribute("id", id);
        return "faculty/add";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable int id, ModelMap map) {
        map.addAttribute("id", id);
        Faculty f = facultyService.findById(id);
        if (f == null) {
            return "redirect:/error";
        }
        map.addAttribute("faculty", f);
        return "faculty/edit";
    }

    @PostMapping("/add/{id}")
    public String addFaculty(@PathVariable int id, Faculty f, ModelMap map) {
        try {
            Department d = departmentService.findById(id);
            if (d == null) {
                return "redirect:/error";
            }
            facultyService.save(f, d);
            return "redirect:/department/" + id;
        } catch (Exception ignored) {
            System.err.println(ignored);
            map.addAttribute("error", true);
            return "faculty/add";
        }
    }

    @PostMapping("/edit/{id}")
    public String editFaculty(Faculty f, @PathVariable int id, ModelMap map) {
        try {
            Faculty entity = facultyService.findById(id);
            if (entity == null) {
                return "redirect:/faculty/add/" + id;
            }
            entity.setName(f.getName());
            entity.setSubjects(facultyService.convertStringForDb(f.getSubjects()));
            entity.setTeachers(facultyService.convertStringForDb(f.getTeachers()));
            facultyService.update(entity);
            return "redirect:/faculty/" + id;
        } catch (Exception ignored) {
            map.addAttribute("error", true);
            return "faculty/edit/" + id;
        }
    }


    @PostMapping("/delete/{id}")
    public String deleteFaculty(@PathVariable int id, ModelMap map) {
        try {
            facultyService.deleteById(id);
        } catch (Exception ignored) {
        }
        return "redirect:/home";
    }
}

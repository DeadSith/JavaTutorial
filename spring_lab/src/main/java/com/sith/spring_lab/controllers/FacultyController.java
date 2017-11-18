package com.sith.spring_lab.controllers;

import com.sith.spring_lab.models.Department;
import com.sith.spring_lab.models.Faculty;
import com.sith.spring_lab.services.DepartmentService;
import com.sith.spring_lab.services.FacultyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param id faculty to get
     * @return Info about faculty or error, if not found
     */
    @GetMapping("/{id}")
    public String getFaculty(@PathVariable int id, ModelMap map) {
        map.addAttribute("id", id);
        try {
            Faculty f = facultyService.findById(id);
            map.addAttribute("faculty", f);
            map.addAttribute("department", f.getDepartment());
            return "faculty/index";
        } catch (Exception ex) {
            logger.error("Request: /" + id + ". Reason: No Faculty with id " + id + " exists.");
            return "redirect:/error";
        }
    }

    /**
     * @param id department to add faculty to
     * @return add form or error page, if department not found
     */
    @GetMapping("/add/{id}")
    public String getAddForm(@PathVariable int id, ModelMap map) {
        Department d = departmentService.findById(id);
        if (d == null) {
            logger.error("Request: /add/" + id + ". Reason: No department with id " + id + " exists.");
            return "redirect:/error";
        }
        map.addAttribute("id", id);
        return "faculty/add";
    }

    /**
     * @param id faculty to add
     * @return edit form or error page, if faculty not found
     */
    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable int id, ModelMap map) {
        map.addAttribute("id", id);
        Faculty f = facultyService.findById(id);
        if (f == null) {
            logger.error("Request: /edit/" + id + ". Reason: No faculty with id " + id + " exists.");
            return "redirect:/error";
        }
        map.addAttribute("faculty", f);
        return "faculty/edit";
    }

    /**
     * @param id department to add to
     * @param f  faculty to add
     * @return department page; error page, if department not found; add form, if input error
     */
    @PostMapping("/add/{id}")
    public String addFaculty(@PathVariable int id, Faculty f, ModelMap map) {
        try {
            Department d = departmentService.findById(id);
            if (d == null) {
                logger.error("Request: POST:/add/" + id + ". Reason: No department with id " + id + " exists.");
                return "redirect:/error";
            }
            facultyService.save(f, d);
            return "redirect:/department/" + id;
        } catch (Exception ex) {
            logger.error("Request: POST:/add/" + id + ". Reason: " + ex.getMessage());
            map.addAttribute("error", true);
            return "faculty/add";
        }
    }

    /**
     * @param f values to change
     * @param id faculty to change
     * @return faculty page or edit from, if input error
     */
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
        } catch (Exception ex) {
            logger.error("Request: POST:/edit/" + id + ". Reason: " + ex.getMessage());
            map.addAttribute("error", true);
            return "faculty/edit/" + id;
        }
    }


    /**
     * @param id faculty to delete
     * @return home page
     */
    @PostMapping("/delete/{id}")
    public String deleteFaculty(@PathVariable int id, ModelMap map) {
        try {
            facultyService.deleteById(id);
        } catch (Exception ex) {
            logger.error("Request: POST:/delete/" + id + ". Reason: " + ex.getMessage());
        }
        return "redirect:/home";
    }
}

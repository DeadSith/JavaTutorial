package com.sith.spring_lab.controllers;

import com.sith.spring_lab.models.Department;
import com.sith.spring_lab.services.DepartmentService;
import com.sith.spring_lab.services.FacultyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    DepartmentService departmentService;
    @Autowired
    FacultyService facultyService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    @PostMapping("/find")
    public String find(@RequestParam(value = "search") String name, ModelMap model) {
        model.addAttribute("departments",departmentService.findByName(name));
        model.addAttribute("faculties",facultyService.findByName(name));
        return "home/find";
    }

    @GetMapping("/dump/download")
    public void getDump(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/json");
            response.setHeader("Content-disposition", "attachment; filename=dump.json");
            String dumpDirectory = System.getProperty("user.home");
            //Get directory to save from environment
            File dump = new File(dumpDirectory + "/dump.json");
            departmentService.serializeCollection(departmentService.getAll(), new PrintWriter(dump));
            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(dump);
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.flush();
        } catch (Exception ex) {
            logger.error("Request: /dump/download. Reason: " + ex.getMessage());
            response.sendRedirect("/error");
        }
    }
}

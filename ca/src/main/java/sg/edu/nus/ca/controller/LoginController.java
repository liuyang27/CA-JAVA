package sg.edu.nus.ca.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.edu.nus.ca.javabean.UserSession;
import sg.edu.nus.ca.model.Admin;
import sg.edu.nus.ca.model.Employee;
import sg.edu.nus.ca.repository.EmployeeRepository;

@Controller
@SessionAttributes("session")
public class LoginController {

	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	public void setEmpRepo(EmployeeRepository empRepo) {
		this.empRepo = empRepo;
	}
	
	@RequestMapping(path = "/")
    public String Login() {
        return "login";
    }
	
	@RequestMapping(path = "/asadmin")
    public String LoginAsAdmin(Model model) {
        return "loginform";
    }
	
	@RequestMapping(path = "/asmanager")
    public String LoginAsManager(Model model) {
		return "loginform_manager";
    }
	
	@RequestMapping(path = "/asemployee")
    public String LoginAsEmployee(Model model) {
		return "loginform_employee";
    }
	
	@RequestMapping(path = "/verifyadmin",method=RequestMethod.POST)
    public String VerifyAdmin(@RequestParam("userid") String username,@RequestParam("password") String password,Model model) {
		List<Admin> a=empRepo.findAdmin(username, password);
		if(a.size()==0) {
			model.addAttribute("Error","error");
			return "loginform";
		} else {
			return "adminhome";
		} 
    }
	
	@RequestMapping(path = "/verifyEmp",method=RequestMethod.POST)
    public String VerifyEmp(@RequestParam("userid") String userid,@RequestParam("password") String password,Model model,HttpSession session) {
		List<Employee> e=empRepo.findEmp(userid, password);
		if(e.size()==0) {
			model.addAttribute("Error","error");
			return "loginform_employee";
		} else {
			UserSession us = new UserSession();
			us.setEmployee(e.get(0));
			session.setAttribute("US", us);
			return "emphome";
		} 
    }
	
	@RequestMapping(path = "/HomePage")
    public String VerifyEmp(HttpSession session) {
			UserSession us = (UserSession)session.getAttribute("US");
			String role=us.getEmployee().getRole();
			if(role.equals("Employee"))
				return "emphome";
			else
				return "managerhome";
	} 
   
	
	
	@RequestMapping(path = "/verifyManager",method=RequestMethod.POST)
    public String VerifyManager(@RequestParam("userid") String userid,@RequestParam("password") String password,Model model,HttpSession session) {
		List<Employee> e=empRepo.findManager(userid, password);
		if(e.size()==0) {
			model.addAttribute("Error","error");
			return "loginform_manager";
		} else {
			UserSession us = new UserSession();
			us.setEmployee(e.get(0));
			session.setAttribute("US", us);
			return "managerhome";
		} 
    }
	
}

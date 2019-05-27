package sg.edu.nus.ca.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.ca.javabean.LeaveReport;
import sg.edu.nus.ca.javabean.UserSession;
import sg.edu.nus.ca.model.Employee;
import sg.edu.nus.ca.model.LeaveForm;
import sg.edu.nus.ca.repository.ApplyLeaveRepository;
import sg.edu.nus.ca.repository.EmployeeRepository;

@Controller
@SessionAttributes("session")
public class ManagerController {
	@Autowired
	private ApplyLeaveRepository LeaveRepo;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@RequestMapping("/manageleave")
	public ModelAndView ManageEmployeeLeave(HttpSession session) {
			
		UserSession us = (UserSession)session.getAttribute("US");
		String username= us.getEmployee().getId();
		List<Employee> emplist =empRepo.findByManagerId(username);	
		List<String> listEmpid = new ArrayList<String>();
		Iterator<Employee> it = emplist.iterator();	
		while(it.hasNext()) {
			Employee emp = it.next();
			listEmpid.add(emp.getId());
		}
		//List<String> pendingleaves = new ArrayList<String>(){{add("Applied");add("Updated");}};	
		List<String> pendingleaves = Arrays.asList("Applied", "Updated");
		List<LeaveForm> leaveHistory = LeaveRepo.findByEmployeeIdInAndLeaveStatusIn(listEmpid, pendingleaves);		
		ModelAndView mav = new ModelAndView("ManageEmployeeLeaves");
		mav.addObject("leaveHistory", leaveHistory);		
		return mav;
	}
	
	
	@RequestMapping(path="/leave/manage/{id}")
	public String LeaveDetails(Model model,@PathVariable("id") int id) {
		LeaveForm leavedetail =LeaveRepo.findById(id).orElse(null);
		List<LeaveForm> empleavehistory=LeaveRepo.findAllByName(leavedetail.getEmployee().getId());
		model.addAttribute("empleavehistory", empleavehistory);
		model.addAttribute("leaveform", leavedetail);
		return "ManageLeaveDetails";
	}
	

	@RequestMapping(path = "/ApproveRejectLeave", method = RequestMethod.POST)
	public String saveLeave(LeaveForm leaveform) {

		int fromid = leaveform.getId();
		LeaveForm leaveDB = LeaveRepo.findById(fromid).orElse(null);
		leaveform.setEmployee(leaveDB.getEmployee());
	
		if(leaveform.getLeaveStatus().equals("Approve")){
			leaveform.setLeaveStatus("Approved");
			double days=leaveDB.getNumOfDays()-leaveform.getNumOfDays();
			leaveform.setNumOfDays(days);
		}
		else {
			leaveform.setLeaveStatus("Rejected");
		}
		LeaveRepo.save(leaveform);
		
		return "redirect:/manageleave";

    }

	
	@RequestMapping("/report")
	public ModelAndView Report(HttpSession session) {		
		UserSession us = (UserSession)session.getAttribute("US");
		String username= us.getEmployee().getId();			
		ModelAndView mav = new ModelAndView("report");
		mav.addObject("managerName", username);		
		return mav;
	}
	
	
	@RequestMapping(path = "/generateReport/ajax", method = RequestMethod.POST)	
	@ResponseBody
	public List<LeaveReport> getReport(String datefrom,String dateto,HttpSession session) {
		UserSession us = (UserSession)session.getAttribute("US");
		String username= us.getEmployee().getId();
		List<Employee> emplist =empRepo.findByManagerId(username);			
		List<LeaveForm> leaveHistory = new ArrayList<LeaveForm>();
		
		
		Iterator<Employee> it = emplist.iterator();	
		while(it.hasNext()) {
			Employee emp = it.next();
			List<LeaveForm> History=LeaveRepo.findSearch(emp.getId(),datefrom,dateto);
			leaveHistory.addAll(History);
		}
		
		List<LeaveReport> list = new ArrayList<LeaveReport>();
		Iterator<LeaveForm> leabeformIterator = leaveHistory.iterator();
		int n=1;
		 SimpleDateFormat formatter= new SimpleDateFormat ("dd/MM/yyyy"); 
		while(leabeformIterator.hasNext()) {		
			LeaveForm lf =new LeaveForm();
			lf=leabeformIterator.next();
			LeaveReport lr = new LeaveReport();
			//lr.setId(String.valueOf(n++));
			lr.setCategory(lf.getCategory());
			lr.setEmployeeid(lf.getEmployee().getName());
			String starttime =formatter.format(lf.getStart());
			String endtime =formatter.format(lf.getEnd());
			lr.setStart(starttime);
			lr.setEnd(endtime);
			lr.setNumOfDays(lf.getNumOfDays());
			lr.setLeaveStatus(lf.getLeaveStatus());
			
			list.add(lr);		
		}
		
		//List<LeaveReport> list = new ArrayList<Testmodel>();

		return list;
    }
	
	
	
	
}

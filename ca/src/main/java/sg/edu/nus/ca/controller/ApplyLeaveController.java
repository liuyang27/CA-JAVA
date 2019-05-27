package sg.edu.nus.ca.controller;

import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.edu.nus.ca.javabean.UserSession;
import sg.edu.nus.ca.model.LeaveForm;
import sg.edu.nus.ca.model.PublicHolidays;
import sg.edu.nus.ca.repository.ApplyLeaveRepository;
import sg.edu.nus.ca.repository.EmployeeRepository;
import sg.edu.nus.ca.repository.LeaveEntitleRepository;
import sg.edu.nus.ca.repository.PublicHolidayRepository;

@Controller
@SessionAttributes("session")
public class ApplyLeaveController {

	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private LeaveEntitleRepository leavaEntityRepo;
	
	@Autowired
	private ApplyLeaveRepository LeaveRepo;
	
	@Autowired
	private PublicHolidayRepository phRepo;
	
	@RequestMapping(path="/leave/apply")
	public String ApplyLeave(Model model,HttpSession session,LeaveForm leaveform) {
		List<String> leavetypes =leavaEntityRepo.findLeaveTypes();	
		UserSession us = (UserSession)session.getAttribute("US");
		leaveform.setEmployee(us.getEmployee());
		
		model.addAttribute("leaveform", leaveform);
		model.addAttribute("leavetypes", leavetypes);
		List<PublicHolidays> publicHolidays = getHolidays();
		model.addAttribute("publicHolidays", publicHolidays);
		return "LeaveForm";
	}


	@RequestMapping(path = "/ApplyLeave", method = RequestMethod.POST)
	public String saveLeave(LeaveForm leaveform,HttpSession session) {
		UserSession us = (UserSession)session.getAttribute("US");
		leaveform.setEmployee(us.getEmployee());
		LeaveForm checkExist= LeaveRepo.findById(leaveform.getId()).orElse(null);
		if(checkExist==null) {
			leaveform.setLeaveStatus("Applied");
			LeaveRepo.save(leaveform);
			if(us.getEmployee().getRole().equals("Employee")) {			
				return "emphome";
			}
			else {
				return "managerhome";	
			}
				
		}
		else
		{
			leaveform.setLeaveStatus("Updated");
			LeaveRepo.save(leaveform);
	        return "redirect:/leave/history";
		}
		
    }
	
	@RequestMapping(path="/leave/history")
	public String LeaveHistory(Model model,HttpSession session) {
		UserSession us = (UserSession)session.getAttribute("US");
		String username= us.getEmployee().getId();
		List<LeaveForm> leaveHistory =(List<LeaveForm>) LeaveRepo.findAllByName(username);
		model.addAttribute("leaveHistory", leaveHistory);
		return "LeaveHistory";
	}
	
	
	@RequestMapping(path="/leave/details/{id}")
	public String LeaveDetails(Model model,@PathVariable("id") int id) {
		LeaveForm leavedetail =LeaveRepo.findById(id).orElse(null);
		model.addAttribute("leavedetail", leavedetail);
		return "LeaveDetails";
	}
	
	@RequestMapping(path="/leave/delete/{id}")
	public String LeaveDelete(@PathVariable("id") int id) {
		LeaveForm leavedetail =LeaveRepo.findById(id).orElse(null);
		LeaveRepo.delete(leavedetail);
		return "redirect:/leave/history";
	}
	
	@RequestMapping(path="/leave/cancel/{id}")
	public String LeaveCancel(@PathVariable("id") int id) {
		LeaveForm leavedetail =LeaveRepo.findById(id).orElse(null);
		leavedetail.setLeaveStatus("Cancelled");
		LeaveRepo.save(leavedetail);
		return "redirect:/leave/history";
	}
	

	@RequestMapping(path="/leave/edit/{id}")
	public String LeaveEdit(@PathVariable("id") int id,Model model) {
		LeaveForm leavedetail =LeaveRepo.findById(id).orElse(null);
		List<String> leavetypes =leavaEntityRepo.findLeaveTypes();
		
		List<PublicHolidays> publicHolidays = getHolidays();
		
		model.addAttribute("publicHolidays", publicHolidays);
		model.addAttribute("leavetypes", leavetypes);
		model.addAttribute("leaveform", leavedetail);
		return "LeaveForm";
	}


	private List<PublicHolidays> getHolidays() {
		List<PublicHolidays> publicHolidays=phRepo.findAll();
		ListIterator<PublicHolidays> it =publicHolidays.listIterator();
		while(it.hasNext()) {		
			PublicHolidays ph=it.next();
			Calendar cal = Calendar.getInstance();
			cal.setTime(ph.getDate());			

			if(cal.get(Calendar.DAY_OF_WEEK)==1)
			{
				PublicHolidays newph=new PublicHolidays();
				cal.add(Calendar.HOUR, 24);
				//String str=cal.getTime().toLocaleString();
				java.util.Date utilPHdate =(java.util.Date) cal.getTime();
				java.sql.Date sqlPHdate=new java.sql.Date(utilPHdate.getTime());
				newph.setDate(sqlPHdate);
				newph.setName("Compensation Holiday");
				it.add(newph);

			}
		}
		return publicHolidays;
	}
	
	@RequestMapping(path="/leave/claim")
	public String LeaveClaim(Model model,HttpSession session,LeaveForm leaveform) {

		UserSession us = (UserSession)session.getAttribute("US");
		leaveform.setEmployee(us.getEmployee());
		leaveform.setCategory("Compensation");
		
		model.addAttribute("leaveform", leaveform);

		List<PublicHolidays> publicHolidays=getHolidays();
		model.addAttribute("publicHolidays", publicHolidays);
		return "CompensationForm";
	}



	
}

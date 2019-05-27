package sg.edu.nus.ca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.ca.model.LeaveForm;

@Repository
public interface ApplyLeaveRepository extends JpaRepository<LeaveForm,Integer>{

//	@Query(value="SELECT * from emp_leave where EmployeeId=?1",nativeQuery=true)
//	List<LeaveForm> findAllByName(String employeeId);
	
	@Query(value="SELECT * from emp_leave where employee_id=?1 order by ID DESC",nativeQuery=true)
	List<LeaveForm> findAllByName(String employeeId);
	
	List<LeaveForm> findByEmployeeIdInAndLeaveStatusIn(List<String> employeeid,List<String> leavestatus);
	
	
	@Query(value="SELECT * from emp_leave where employee_id=?1 and START>=?2 and END<=?3",nativeQuery=true)	
	List<LeaveForm> findSearch(String employeeid,String datefrom,String dateto);

	
}

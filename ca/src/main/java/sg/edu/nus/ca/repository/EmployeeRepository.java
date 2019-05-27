package sg.edu.nus.ca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.nus.ca.model.Admin;
import sg.edu.nus.ca.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,String> {

	@Query(value="select * from employee where role=?1",nativeQuery=true)
	List<Employee> findByRole(String role);

	@Query(value="select a from Admin a where a.id=?1 and a.password=?2")
	List<Admin> findAdmin(String username,String password);

	@Query(value="select Id from employee where emailid=?1",nativeQuery=true)
	String findByEmail(String email);
	
	
	@Query(value="select e from Employee e where e.id=?1 and e.password=?2")
	List<Employee> findEmp(String userid,String password);
	
	@Query(value="select e from Employee e where e.id=?1 and e.password=?2 and e.role='Manager'")
	List<Employee> findManager(String userid,String password);
	
	@Query(value="select * from employee where managerid=?1",nativeQuery=true)
	List<Employee> findByManagerId(String managerid);
}

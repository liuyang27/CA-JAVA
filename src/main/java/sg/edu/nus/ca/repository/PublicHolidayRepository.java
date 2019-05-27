package sg.edu.nus.ca.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.nus.ca.model.PublicHolidays;

public interface PublicHolidayRepository extends JpaRepository<PublicHolidays,Date> {

	@Query(value="select * from public_holidays where date=?1",nativeQuery=true)
	PublicHolidays getPublicHoliday(String date);
}

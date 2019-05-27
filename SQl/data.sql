DROP TABLE IF EXISTS EMP_LEAVE;
DROP TABLE IF EXISTS HIBERNATE_SEQUENCE;
DROP TABLE IF EXISTS LEAVE_ENTITLEMENT;
DROP TABLE IF EXISTS PUBLIC_HOLIDAYS;
DROP TABLE IF EXISTS EMPLOYEE;
DROP TABLE IF EXISTS EMP_SEQ;
DROP TABLE IF EXISTS ADMIN; 
DROP TABLE IF EXISTS LEAVE_BALANCE;
                      
CREATE TABLE leave_entitlement (LeaveType varchar(50) NOT NULL, Role varchar(50) NOT NULL,
								LeaveCount int(11),
								Id int(11) NOT NULL AUTO_INCREMENT,PRIMARY KEY (Id));                  
                      
 CREATE TABLE admin (Id varchar(10) NOT NULL, Password varchar(36),
					 Role varchar(50), Email varchar(50),
					 PRIMARY KEY (Id));
 
 
CREATE TABLE emp_seq (next_val bigint(20));

CREATE TABLE employee (id varchar(10) NOT NULL,name varchar(50),
						password varchar(36),role varchar(50),
						 designation varchar(50),managerid varchar(10),
						emailid varchar(50),
						PRIMARY KEY (id), KEY Manager_idx (managerid),
						CONSTRAINT Manager FOREIGN KEY (managerid) REFERENCES employee (id) ON DELETE SET NULL ON UPDATE SET NULL

);



CREATE TABLE emp_leave (Id INT NOT NULL auto_increment, EmployeeId VARCHAR(10),
                      Category VARCHAR(50), START DATETIME,
                      END DATETIME, NumOfDays DOUBLE, Reason TEXT, WorkDissemination TEXT,
                      ContactDetails VARCHAR(50), ManagerComments TEXT,
                      LeaveStatus VARCHAR(50),
					  CONSTRAINT EMP_FK FOREIGN KEY (EmployeeId) REFERENCES EMPLOYEE (Id), 
                      PRIMARY KEY (Id));  

CREATE TABLE hibernate_sequence (next_val bigint(20));
 

CREATE TABLE public_holidays (Date date NOT NULL,Name varchar(50),PRIMARY KEY (Date)); 

CREATE TABLE leave_balance (employeeid varchar(10) NOT NULL,leavetypeid int(11) NOT NULL,
							leavebalance double NOT NULL,
                              PRIMARY KEY (employeeid,leavetypeid),
                              KEY LeaveType_idx (leavetypeid),
							CONSTRAINT Employee FOREIGN KEY (employeeid) REFERENCES employee (id) ON DELETE CASCADE,
                            CONSTRAINT LeaveType FOREIGN KEY (leavetypeid) REFERENCES leave_entitlement (Id) ON DELETE CASCADE

);
 
 INSERT INTO `leave_entitlement` VALUES ('Annual','Employee',18,1);
 INSERT INTO `leave_entitlement` VALUES ('Annual','Manager',14,2);
 INSERT INTO `leave_entitlement` VALUES ('Medical','Employee',60,3);
 INSERT INTO `leave_entitlement` VALUES  ('Medical','Manager',60,4);
 INSERT INTO `leave_entitlement` VALUES  ('Maternity','Employee',120,8);
 
 INSERT INTO `admin` VALUES ('admin','admin','Admin','admin@company.com');
 
 INSERT INTO `emp_seq` VALUES (41868);

 INSERT INTO `employee` values('system','System','123','Manager','General Manager',NULL,'system@company.com'); 
 INSERT INTO `employee` VALUES ('EMP41553','varun','123','Manager','General Manager','system','var@company.com');
 INSERT INTO `employee` VALUES ('EMP41602','ganesh','123','Manager','Finance Manager','system','gan@company.com');
 INSERT INTO `employee` VALUES ('EMP41652','tan','123','Employee','Sales Executive','EMP41602','tan@company.com');
 INSERT INTO `employee` VALUES ('EMP41756','Chen Yingxuan','123','Manager','General Manager','system','cyx@company.com');
 INSERT INTO `employee` VALUES ('EMP41762','Lakshmanan Vellaiappan','123','Employee','Finance Executive','EMP41756','lv@company.com');
 INSERT INTO `employee` VALUES ('EMP41766','Liu Yang','123','Employee','Finance Executive','EMP41756','ly@company.com');
 INSERT INTO `employee` VALUES ('EMP41773','Loke Seng Liat','123','Employee','Administration Executive','EMP41756','lsl@company.com');
 INSERT INTO `employee` VALUES ('EMP41785','Tan Li Xian','123','Employee','Administration Executive','EMP41756','tlx@company.com');
 INSERT INTO `employee` VALUES ('EMP41799','Tan Wei Shan','123','Employee','Data Scientist','EMP41756','tws@company.com');
 INSERT INTO `employee` VALUES ('EMP41854','Tan Yew Vei','123','Employee','Data Scientist','EMP41756','tyv@company.com');
 INSERT INTO `employee` VALUES ('EMP41867','Wang Hong Tao','123','Employee','Cleaning Service','EMP41756','wht@company.com');

 INSERT INTO `hibernate_sequence` VALUES (9);
 
 INSERT INTO `public_holidays` VALUES ('2019-01-01','New Year Day');
 INSERT INTO `public_holidays` VALUES  ('2019-02-05','Chinese New Year');
 INSERT INTO `public_holidays` VALUES ('2019-02-06','Chinese New Year') ;
 INSERT INTO `public_holidays` VALUES ('2019-04-19','Good Friday');
 INSERT INTO `public_holidays` VALUES ('2019-05-01','Labour Day'); 
 INSERT INTO `public_holidays` VALUES ('2019-05-19','Vesak Day'); 
 INSERT INTO `public_holidays` VALUES ('2019-06-05','Hari Raya');
 INSERT INTO `public_holidays` VALUES ('2019-08-09','National Day');
 INSERT INTO `public_holidays` VALUES ('2019-08-11','Hari Raya Haji');
 INSERT INTO `public_holidays` VALUES ('2019-10-27','Deepavali');
 INSERT INTO `public_holidays` VALUES ('2019-12-25','Christmas Day');


INSERT INTO `laps_ly`.`EMP_LEAVE` (`EMPLOYEEID`,`CATEGORY`,`START`,`END`,`NUMOFDAYS`,`REASON`,`WORKDISSEMINATION`,`CONTACTDETAILS`,`MANAGERCOMMENTS`,`LEAVESTATUS`) VALUES ('EMP41553','Medical','2019-02-02','2019-02-03',2,'Flu','Liu Yang','65-1234-0000',null,'Approved');
INSERT INTO `laps_ly`.`EMP_LEAVE` (`EMPLOYEEID`,`CATEGORY`,`START`,`END`,`NUMOFDAYS`,`REASON`,`WORKDISSEMINATION`,`CONTACTDETAILS`,`MANAGERCOMMENTS`,`LEAVESTATUS`) VALUES ('EMP41602','Annual','2019-03-03','2019-03-03',1,'My Birthday','Liu Yang','65-1234-0000','Busy times','Rejected');
INSERT INTO `laps_ly`.`EMP_LEAVE` (`EMPLOYEEID`,`CATEGORY`,`START`,`END`,`NUMOFDAYS`,`REASON`,`WORKDISSEMINATION`,`CONTACTDETAILS`,`MANAGERCOMMENTS`,`LEAVESTATUS`) VALUES ('EMP41652','Compensation','2019-04-04','2019-04-05',2,'Too tired','Li Xian','60-1234-2222',NULL,'Approved');

INSERT INTO `leave_balance` VALUES ('EMP41553',2,14);
INSERT INTO `leave_balance` VALUES ('EMP41553',4,60);
INSERT INTO `leave_balance` VALUES ('EMP41602',2,14);
INSERT INTO `leave_balance` VALUES ('EMP41602',4,60);
INSERT INTO `leave_balance` VALUES ('EMP41652',1,18);
INSERT INTO `leave_balance` VALUES ('EMP41652',3,60);
	

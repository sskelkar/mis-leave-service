-- Package com.maxxton.mis.leave

-- employee
CREATE SEQUENCE seq_employee START WITH 1;

CREATE TABLE employee
(
  employee_id NUMBER NOT NULL,
  first_name VARCHAR2(50),
  middle_name VARCHAR2(50),
  last_name VARCHAR2(50),
  user_name VARCHAR2(50) NOT NULL UNIQUE,
  password VARCHAR2(128),
  email VARCHAR2(50),
  birth_date DATE,
  joining_date DATE,
  sex VARCHAR2(10),
  probation_period_months NUMBER,
  release_date DATE,
  pan_number VARCHAR2(15),
  passport_number VARCHAR2(15),
  passport_valid_till DATE,
  emergency_contact_person VARCHAR2(50),
  emergency_contact_phone_number VARCHAR2(20),
  blood_group VARCHAR2(5),
  manager_id NUMBER,
  
  CONSTRAINT pk_employee PRIMARY KEY (employee_id)
);

-- public_holiday
CREATE SEQUENCE seq_public_holiday START WITH 1;

CREATE TABLE public_holiday
(
  public_holiday_id NUMBER NOT NULL,
  holiday_date DATE NOT NULL,
  name VARCHAR2(50),
  
  CONSTRAINT pk_public_holiday PRIMARY KEY (public_holiday_id)
);


-- leave_application
CREATE SEQUENCE seq_leave_application START WITH 1;

CREATE TABLE leave_application
(
  leave_application_id NUMBER NOT NULL,
  employee_id NUMBER NOT NULL,
  leave_from DATE,
  leave_from_half NUMBER,
  leave_to DATE,
  leave_to_half NUMBER,
  leave_duration NUMBER,
  no_of_working_days NUMBER,
  application_date DATE,
  comment_by_applicant VARCHAR2(80),
  leave_type VARCHAR2(50) NOT NULL,
  leave_status VARCHAR2(50) NOT NULL,
  is_borrowed NUMBER,
  manager_id NUMBER,
  comment_by_manager VARCHAR2(80),
  
  CONSTRAINT pk_leave_application PRIMARY KEY(leave_application_id),
  CONSTRAINT fk_leave_application_emp FOREIGN KEY (employee_id) REFERENCES employee(employee_id),
  CONSTRAINT fk_leave_application_mgr FOREIGN KEY (manager_id) REFERENCES employee(employee_id),
  CONSTRAINT c_leave_from_half CHECK (leave_from_half IN (1, 2)),
  CONSTRAINT c_leave_to_half CHECK (leave_to_half IN (1, 2))
);


-- employee_leave
CREATE SEQUENCE seq_employee_leave START WITH 1;

CREATE TABLE employee_leave
(
  employee_leave_id NUMBER NOT NULL,
  employee_id NUMBER NOT NULL,
  year NUMBER,
  leave_count NUMBER,
  leave_type NUMBER NOT NULL,
    
  CONSTRAINT pk_employee_leave PRIMARY KEY (employee_leave_id),
  CONSTRAINT fk_employee_leave_emp FOREIGN KEY (employee_id) REFERENCES employee(employee_id),
);


-- employee_comp_off
CREATE SEQUENCE seq_employee_comp_off START WITH 1;

CREATE TABLE employee_comp_off
(
  employee_comp_off_id NUMBER NOT NULL,
  employee_leave_id NUMBER NOT NULL,
  manager_id NUMBER NOT NULL,
  comment_by_manager VARCHAR2(80),
  start_date DATE,
  end_date DATE,
      
  CONSTRAINT pk_employee_comp_off PRIMARY KEY (employee_comp_off_id),
  CONSTRAINT fk_employee_comp_off_leave FOREIGN KEY (employee_leave_id) REFERENCES employee_leave(employee_leave_id),
  CONSTRAINT fk_employee_comp_off_mgr FOREIGN KEY (manager_id) REFERENCES employee(employee_id)
);



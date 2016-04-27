-- ACTUAL DATA

-- leave_type
INSERT INTO leave_type(name) VALUES('Planned');
INSERT INTO leave_type(name) VALUES('Unplanned');
INSERT INTO leave_type(name) VALUES('Leave Without Pay');
INSERT INTO leave_type(name) VALUES('Compensatory Off');
INSERT INTO leave_type(name) VALUES('Encashed');
INSERT INTO leave_type(name) VALUES('Carry Forward');
INSERT INTO leave_type(name) VALUES('Maternity');
INSERT INTO leave_type(name) VALUES('Paternity');

-- leave_status
INSERT INTO leave_status(name) VALUES('Approved');
INSERT INTO leave_status(name) VALUES('Pending');
INSERT INTO leave_status(name) VALUES('Rejected');
INSERT INTO leave_status(name) VALUES('Cancelled');

-- public_holiday
INSERT INTO public_holiday(holiday_date, name) VALUES('01-jan-2015', 'New Year Day');
INSERT INTO public_holiday(holiday_date, name) VALUES('26-jan-2015', 'Republic Day');
INSERT INTO public_holiday(holiday_date, name) VALUES('05-mar-2015', 'Holi');
INSERT INTO public_holiday(holiday_date, name) VALUES('01-may-2015', 'May Day');
INSERT INTO public_holiday(holiday_date, name) VALUES('17-sep-2015', 'Ganesh Chaturthi');
INSERT INTO public_holiday(holiday_date, name) VALUES('22-oct-2015', 'Dushehra');
INSERT INTO public_holiday(holiday_date, name) VALUES('11-nov-2015', 'Diwali');
INSERT INTO public_holiday(holiday_date, name) VALUES('12-nov-2015', 'Diwali');


---------------------------------------------------------------------------------------------------
-- DUMMY DATA

-- employee
-- INSERT INTO employee(first_name, last_name, user_name) VALUES('sojjwal', 'kelkar', 'sokema');

-- employee_leave
-- INSERT INTO employee_leave(employee_id, year, leave_count, leave_type_id) VALUES(21, 2015, 5, 22);


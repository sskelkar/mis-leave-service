CREATE OR REPLACE TRIGGER trg_employee_bi
BEFORE INSERT ON employee
FOR EACH ROW
BEGIN
  IF :NEW.employee_id IS NULL THEN
    SELECT seq_employee.NEXTVAL INTO :NEW.employee_id FROM dual;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_public_holiday_bi
BEFORE INSERT ON public_holiday
FOR EACH ROW
BEGIN
  IF :NEW.public_holiday_id IS NULL THEN
    SELECT seq_public_holiday.NEXTVAL INTO :NEW.public_holiday_id FROM dual;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_leave_type_bi
BEFORE INSERT ON leave_type
FOR EACH ROW
BEGIN
  IF :NEW.leave_type_id IS NULL THEN
    SELECT seq_leave_type.NEXTVAL INTO :NEW.leave_type_id FROM dual;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_leave_status_bi
BEFORE INSERT ON leave_status
FOR EACH ROW
BEGIN
  IF :NEW.leave_status_id IS NULL THEN
    SELECT seq_leave_status.NEXTVAL INTO :NEW.leave_status_id FROM dual;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_leave_application_bi
BEFORE INSERT ON leave_application
FOR EACH ROW
BEGIN
  IF :NEW.leave_application_id IS NULL THEN
    SELECT seq_leave_application.NEXTVAL INTO :NEW.leave_application_id FROM dual;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_employee_leave_bi
BEFORE INSERT ON employee_leave
FOR EACH ROW
BEGIN
  IF :NEW.employee_leave_id IS NULL THEN
    SELECT seq_employee_leave.NEXTVAL INTO :NEW.employee_leave_id FROM dual;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_employee_comp_off_bi
BEFORE INSERT ON employee_comp_off
FOR EACH ROW
BEGIN
  IF :NEW.employee_comp_off_id IS NULL THEN
    SELECT seq_employee_comp_off.NEXTVAL INTO :NEW.employee_comp_off_id FROM dual;
  END IF;
END;
/
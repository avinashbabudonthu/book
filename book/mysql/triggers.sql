DELIMITER $$
CREATE TRIGGER staff_location_trigger_insert
AFTER INSERT
ON staff
FOR EACH ROW
BEGIN
    IF NEW.location_id IS NOT NULL THEN
        INSERT INTO staff_location_audit (id, staff_id, from_location_id, to_location_id, description, created_at, updated_at)
        VALUES (
            UUID(), 
            NEW.id, 
            NULL, 
            NEW.location_id, 
            CONCAT('Assigned ', (SELECT name FROM staff WHERE id = NEW.id), ' to location ', (SELECT name FROM locations WHERE id = NEW.location_id)), 
            CURRENT_TIMESTAMP, 
            CURRENT_TIMESTAMP
        );
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER staff_location_trigger_update
AFTER UPDATE
ON staff
FOR EACH ROW
BEGIN
    IF NEW.location_id IS NOT NULL AND OLD.location_id IS NOT NULL AND NEW.location_id != OLD.location_id THEN
        INSERT INTO staff_location_audit (id, staff_id, from_location_id, to_location_id, description, created_at, updated_at)
        VALUES (
            UUID(), 
            OLD.id, 
            OLD.location_id, 
            NEW.location_id, 
            CONCAT('Moved ', (SELECT name FROM staff WHERE id = OLD.id), ' from location ', (SELECT name FROM locations WHERE id = OLD.location_id), ' to location ', (SELECT name FROM locations WHERE id = NEW.location_id)), 
            CURRENT_TIMESTAMP, 
            CURRENT_TIMESTAMP
        );
    ELSEIF NEW.location_id IS NULL THEN
        INSERT INTO staff_location_audit (id, staff_id, from_location_id, to_location_id, description, created_at, updated_at)
        VALUES (
            UUID(), 
            OLD.id, 
            OLD.location_id, 
            NULL, 
            CONCAT('Removed ', (SELECT name FROM staff WHERE id = OLD.id), ' from location ', (SELECT name FROM locations WHERE id = OLD.location_id)), 
            CURRENT_TIMESTAMP, 
            CURRENT_TIMESTAMP
        );
    END IF;
END$$
DELIMITER ;

DELIMITER $$

CREATE TRIGGER staff_location_trigger_delete
AFTER DELETE
ON staff
FOR EACH ROW
BEGIN
    IF OLD.location_id IS NOT NULL THEN
        INSERT INTO staff_location_audit (id, staff_id, from_location_id, to_location_id, description, created_at, updated_at)
        VALUES (
            UUID(), 
            OLD.id, 
            OLD.location_id, 
            NULL, 
            CONCAT('Removed ', (SELECT name FROM staff WHERE id = OLD.id), ' from location ', (SELECT name FROM locations WHERE id = OLD.location_id)), 
            CURRENT_TIMESTAMP, 
            CURRENT_TIMESTAMP
        );
    END IF;
END$$

DELIMITER ;

-- DROP TRIGGER
drop trigger if exists staff_location_trigger_update;
drop trigger if exists staff_location_trigger_delete;
drop trigger if exists staff_location_trigger_delete;
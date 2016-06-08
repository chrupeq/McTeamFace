-- You can use this file to load seed data into the database using SQL statements
INSERT INTO event_cause(cause_code, event_id, description)  VALUES(0, 4097, 'RRC CONN SETUP-SUCCESS')
INSERT INTO event_cause(cause_code, event_id, description)  VALUES(1, 4097, 'RRC CONN SETUP-UNSPECIFIED')
INSERT INTO user_equipment(tac, marketing_name, manufacturer, access_capability, model, vendor_name, user_equipment_type, operating_system, input_mode) VALUES(100100, 'G410', 'Mitsubishi', 'GSM 1800, GSM 900', 'G410', 'Mitsubishi', NULL, NULL, NULL)
INSERT INTO user_equipment(tac, marketing_name, manufacturer, access_capability, model, vendor_name, user_equipment_type, operating_system, input_mode) VALUES(100200, 'A53', 'Siemens', 'GSM 1900, GSM850 (GSM800)', 'A53', 'Siemens', 'HANDHELD', NULL, 'BASIC')
INSERT INTO mcc_mnc(mcc, mnc, country, operator) VALUES(238, 1, 'Denmark', 'TDC-DK')
INSERT INTO mcc_mnc(mcc, mnc, country, operator) VALUES(238, 2, 'Denmark', 'Sonofon DK')
INSERT INTO failure_class(failure_class, description) VALUES(0, 'EMERGENCY')
INSERT INTO base_data(date_time, cause_code, event_id, failure_class, ue_type, market, operator, cell_id, duration, ne_version, imsi, hier3_id, hier32_id, hier321_id) VALUES('2013-01-11 17:15:00', NULL, 4097, NULL, 100100, 238, 1, 4, 1000, '11b', 344930000000011, 4809532081614990000, 8226896360947470000, 1150444940909480000)

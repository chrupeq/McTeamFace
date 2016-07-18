-- You can use this file to load seed data into the database using SQL statements
--INSERT INTO mcc_mnc(mcc, mnc, country, operator) VALUES(238, 1, 'Denmark', 'TDC-DK')
--INSERT INTO mcc_mnc(mcc, mnc, country, operator) VALUES(238, 2, 'Denmark', 'Sonofon DK')
--INSERT INTO event_cause(cause_code, event_id, description)  VALUES(0, 4097, 'RRC CONN SETUP-SUCCESS')
--INSERT INTO event_cause(cause_code, event_id, description)  VALUES(1, 4097, 'RRC CONN SETUP-UNSPECIFIED')
--INSERT INTO user_equipment(tac, marketing_name, manufacturer, access_capability, model, vendor_name, user_equipment_type, operating_system, input_mode) VALUES(100100, 'G410', 'Mitsubishi', 'GSM 1800, GSM 900', 'G410', 'Mitsubishi', NULL, NULL, NULL)
--INSERT INTO user_equipment(tac, marketing_name, manufacturer, access_capability, model, vendor_name, user_equipment_type, operating_system, input_mode) VALUES(100200, 'A53', 'Siemens', 'GSM 1900, GSM850 (GSM800)', 'A53', 'Siemens', 'HANDHELD', NULL, 'BASIC')
--INSERT INTO failure_class(failure_class, description) VALUES(0, 'EMERGENCY')
--INSERT INTO base_data(date_time, cause_code, event_id, failure_class, ue_type, market, operator, cell_id, duration, ne_version, imsi, hier3_id, hier32_id, hier321_id) VALUES('2013-01-11 17:15:00', NULL, 4097, NULL, 100100, 238, 1, 4, 1000, '11b', 344930000000011, 4809532081614990000, 8226896360947470000, 1150444940909480000)
-- event_cause table --
INSERT INTO event_cause(cause_code, event_id, description)  VALUES(23, 4125, 'UE CTXT RELEASE-UNKNOWN OR ALREADY ALLOCATED MME UE S1AP ID');
INSERT INTO event_cause(cause_code, event_id, description)  VALUES(0, 4097, 'RRC CONN SETUP-SUCCESS');
INSERT INTO event_cause(cause_code, event_id, description)  VALUES(1, 4097, 'RRC CONN SETUP-UNSPECIFIED');
INSERT INTO event_cause(cause_code, event_id, description)  VALUES(11, 4106, 'INITIAL CTXT SETUP-TRANSPORT REJECT');
INSERT INTO event_cause(cause_code, event_id, description)  VALUES(0, 4098, 'S1 SIG CONN SETUP-SUCCESS');
INSERT INTO event_cause(cause_code, event_id, description)  VALUES(12, 4097, 'RRC CONN SETUP-REJECT DUE TO OVERLOAD');
INSERT INTO event_cause(cause_code, event_id, description)  VALUES(9, 4097, 'RRC CONN SETUP-S1 INTERFACE DOWN');
INSERT INTO event_cause(cause_code, event_id, description)  VALUES(13, 4097, 'RRC CONN SETUP-UE BEARERS REJECTED DUE TO ARP ADM REJ');
-- user_equipment table --
INSERT INTO user_equipment(tac, marketing_name, manufacturer, access_capability, model, vendor_name, user_equipment_type, operating_system, input_mode) VALUES(100100, 'G410', 'Mitsubishi', 'GSM 1800, GSM 900', 'G410', 'Mitsubishi', NULL, NULL, NULL);
INSERT INTO user_equipment(tac, marketing_name, manufacturer, access_capability, model, vendor_name, user_equipment_type, operating_system, input_mode) VALUES(100200, 'A53', 'Siemens', 'GSM 1900, GSM850 (GSM800)', 'A53', 'Siemens', 'HANDHELD', NULL, 'BASIC');
INSERT INTO user_equipment(tac, marketing_name, manufacturer, access_capability, model, vendor_name, user_equipment_type, operating_system, input_mode) VALUES(21060800, 'VEA3', 'S.A.R.L. B  & B International', 'GSM 1800, GSM 900', 'VEA3', 'S.A.R.L. B  & B International', NULL, NULL, NULL);
INSERT INTO user_equipment(tac, marketing_name, manufacturer, access_capability, model, vendor_name, user_equipment_type, operating_system, input_mode) VALUES(33000153, '9109 PA', 'Alcatel Radiotelephone (LAVAL)', 'GSM 900', '9109 PA', 'Alcatel Radiotelephone (LAVAL)', NULL, NULL, NULL);
INSERT INTO user_equipment(tac, marketing_name, manufacturer, access_capability, model, vendor_name, user_equipment_type, operating_system, input_mode) VALUES(33000253, 'Dirland Miniphone', 'Alcatel Radiotelephone (LAVAL)', 'GSM 900', 'Dirland Miniphone', 'Alcatel Radiotelephone (LAVAL)', NULL, NULL, NULL);
-- mcc_mnc table --
INSERT INTO mcc_mnc(mcc, mnc, country, operator) VALUES(238, 1, 'Denmark', 'TDC-DK');
INSERT INTO mcc_mnc(mcc, mnc, country, operator) VALUES(238, 2, 'Denmark', 'Sonofon DK');
INSERT INTO mcc_mnc(mcc, mnc, country, operator) VALUES(344, 930, 'Antigua and Barbuda', 'AT&T Wireless-Antigua AG');
INSERT INTO mcc_mnc(mcc, mnc, country, operator) VALUES(310, 560, 'United States of America', 'AT&T Mobility');
INSERT INTO mcc_mnc(mcc, mnc, country, operator) VALUES(240, 21, 'Sweden', 'Banverket SE');
-- failure_class table --
INSERT INTO failure_class(failure_class, description) VALUES(0, 'EMERGENCY');
INSERT INTO failure_class(failure_class, description) VALUES(1, 'HIGH PRIORITY ACCESS');
-- a dummy failure class purely for testing purposes
INSERT INTO failure_class(failure_class, description) VALUES(6, 'NUCLEAR MELTDOWN');
-- base_data table --
INSERT INTO base_data(date_time, cause_code, event_id, failure_class, ue_type, market, operator, cell_id, duration, ne_version, imsi, hier3_id, hier32_id, hier321_id) VALUES('2013-01-11 17:15:00', 23, 4125, 0, 21060800, 344, 930, 4, 1000, '11b', 344930000000011, 4809532081614990000, 8226896360947470000, 1150444940909480000);
INSERT INTO base_data(date_time, cause_code, event_id, failure_class, ue_type, market, operator, cell_id, duration, ne_version, imsi, hier3_id, hier32_id, hier321_id) VALUES('2013-01-11 17:15:00', 11, 4106, 1, 21060800, 344, 930, 4, 1000, '11b', 344930000000011, 4809532081614990000, 8226896360947470000, 1150444940909480000);
INSERT INTO base_data(date_time, cause_code, event_id, failure_class, ue_type, market, operator, cell_id, duration, ne_version, imsi, hier3_id, hier32_id, hier321_id) VALUES('2013-01-11 17:16:00', 0, 4098, 1, 33000153, 310, 560, 4, 1000, '11b', 310560000000012, 4809532081614990000, 8226896360947470000, 1150444940909480000); 
INSERT INTO base_data(date_time, cause_code, event_id, failure_class, ue_type, market, operator, cell_id, duration, ne_version, imsi, hier3_id, hier32_id, hier321_id) VALUES('2013-01-11 17:16:00', 12, 4097, 1, 33000153, 310, 560, 4, 1000, '11b', 310560000000012, 4809532081614990000, 8226896360947470000, 1150444940909480000);
INSERT INTO base_data(date_time, cause_code, event_id, failure_class, ue_type, market, operator, cell_id, duration, ne_version, imsi, hier3_id, hier32_id, hier321_id) VALUES('2013-01-11 17:16:00', 11, 4106, 1, 33000153, 310, 560, 4, 1000, '11b', 310560000000012, 4809532081614990000, 8226896360947470000, 1150444940909480000);
INSERT INTO base_data(date_time, cause_code, event_id, failure_class, ue_type, market, operator, cell_id, duration, ne_version, imsi, hier3_id, hier32_id, hier321_id) VALUES('2013-01-11 17:17:00', 0, 4098, 1, 33000253, 240, 21, 4, 1000, '11b', 240210000000013, 4809532081614990000, 8226896360947470000, 1150444940909480000);
INSERT INTO base_data(date_time, cause_code, event_id, failure_class, ue_type, market, operator, cell_id, duration, ne_version, imsi, hier3_id, hier32_id, hier321_id) VALUES('2013-01-11 17:17:00', 0, 4098, 1, 33000253, 240, 21, 4, 1000, '11b', 240210000000013, 4809532081614990000, 8226896360947470000, 1150444940909480000);
INSERT INTO base_data(date_time, cause_code, event_id, failure_class, ue_type, market, operator, cell_id, duration, ne_version, imsi, hier3_id, hier32_id, hier321_id) VALUES('2013-01-11 17:17:00', 11, 4106, 1, 33000253, 240, 21, 4, 1000, '11b', 240210000000013, 4809532081614990000, 8226896360947470000, 1150444940909480000);
INSERT INTO base_data(date_time, cause_code, event_id, failure_class, ue_type, market, operator, cell_id, duration, ne_version, imsi, hier3_id, hier32_id, hier321_id) VALUES('2013-01-11 17:15:00', 13, 4097, 1, 21060800, 344, 930, 4, 1000, '11b', 344930000000011, 4809532081614990000, 8226896360947470000, 1150444940909480000);
INSERT INTO base_data(date_time, cause_code, event_id, failure_class, ue_type, market, operator, cell_id, duration, ne_version, imsi, hier3_id, hier32_id, hier321_id) VALUES('2013-01-11 17:15:00', NULL, 4097, NULL, 100100, 238, 1, 4, 1000, '11b', 344930000000011, 4809532081614990000, 8226896360947470000, 1150444940909480000);
-- users table --
INSERT INTO users(id, firstname, lastname, username, password, job_title) VALUES(1,'Mitja', 'Suncic', 'Suncho', 'nullypointer', 'SE')
INSERT INTO users(id, firstname, lastname, username, password, job_title) VALUES(2,'Laura', 'Hunt', 'FairyMonkey', 'princess', 'CSR')
INSERT INTO users(id, firstname, lastname, username, password, job_title) VALUES(3,'Ruaidhri', 'Garrett', 'TheBest', 'TheBestest', 'SA')
-- fileTimer table --
INSERT INTO filetimer(id, start_time, end_time) VALUES(1, 'Thu Jul 07 10:58:51 BST 2016', 'Thu Jul 07 10:58:58 BST 2016')
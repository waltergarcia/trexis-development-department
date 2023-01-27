INSERT INTO `level`(`level`) VALUES ('Junior');
INSERT INTO `level`(`level`) VALUES ('Senior');
----------------------------------------------------------------------------------------------------------
INSERT INTO `role`(`role`) VALUES ('Manager');
INSERT INTO `role`(`role`) VALUES ('Developer');
INSERT INTO `role`(`role`) VALUES ('QA Tester');
----------------------------------------------------------------------------------------------------------
INSERT INTO `cost`(`cost`) VALUES (17);
INSERT INTO `cost`(`cost`) VALUES (20);
INSERT INTO `cost`(`cost`) VALUES (36);
INSERT INTO `cost`(`cost`) VALUES (46);
----------------------------------------------------------------------------------------------------------
-- Managers
INSERT INTO `employee`(`name`, `email`, `id_role`,`id_cost`)
VALUES ('Manager A', 'manager_a@trexis.net', 1, 1);
INSERT INTO `employee`(`name`, `email`, `id_role`,`id_cost`, `id_manager`)
VALUES ('Manager B', 'manager_b@trexis.net', 1, 1, 1);
INSERT INTO `employee`(`name`, `email`, `id_role`,`id_cost`)
VALUES ('Manager C', 'manager_c@trexis.net', 1, 1);
INSERT INTO `employee`(`name`, `email`, `id_role`,`id_cost`, `id_manager`)
VALUES ('Manager D', 'manager_d@trexis.net', 1, 1, 3);
INSERT INTO `employee`(`name`, `email`, `id_role`,`id_cost`, `id_manager`)
VALUES ('Manager E', 'manager_e@trexis.net', 1, 1, 3);
INSERT INTO `employee`(`name`, `email`, `id_role`,`id_cost`, `id_manager`)
VALUES ('Manager F', 'manager_f@trexis.net', 1, 1, 5);

---- Junior developers
INSERT INTO `employee`(`name`, `email`, `id_role`,`id_cost`, `id_level`, `id_manager`)
VALUES ('Developer Junior A', 'developer_junior_a@trexis.net', 2, 3, 1, 1);
INSERT INTO `employee`(`name`, `email`, `id_role`,`id_cost`, `id_level`, `id_manager`)
VALUES ('Developer Junior B', 'developer_junior_b@trexis.net', 2, 3, 1, 4);

---- Senior developers
INSERT INTO `employee`(`name`, `email`, `id_role`,`id_cost`, `id_level`, `id_manager`)
VALUES ('Developer Senior A', 'developer_senior_a@trexis.net', 2, 4, 2, 2);
INSERT INTO `employee`(`name`, `email`, `id_role`,`id_cost`, `id_level`, `id_manager`)
VALUES ('Developer Senior B', 'developer_senior_b@trexis.net', 2, 4, 2, 4);

---- QA Testers
INSERT INTO `employee`(`name`, `email`, `id_role`,`id_cost`, `id_manager`)
VALUES ('QA Tester A', 'qa_tester_a@trexis.net', 3, 2, 1);
INSERT INTO `employee`(`name`, `email`, `id_role`,`id_cost`, `id_manager`)
VALUES ('QA Tester B', 'qa_tester_b@trexis.net', 3, 2, 4);
INSERT INTO `employee`(`name`, `email`, `id_role`,`id_cost`, `id_manager`)
VALUES ('QA Tester C', 'qa_tester_c@trexis.net', 3, 2, 6);
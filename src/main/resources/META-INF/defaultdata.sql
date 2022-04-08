INSERT INTO `departments` (`title`) VALUES ('Engineering');
INSERT INTO `departments` (`title`) VALUES ('Plumbing');
INSERT INTO `departments` (`title`) VALUES ('Roofing');
INSERT INTO `departments` (`title`) VALUES ('Carpentry');
INSERT INTO `departments` (`title`) VALUES ('Bricklaying');
INSERT INTO `departments` (`title`) VALUES ('Office');

INSERT INTO `roles` (`title`) VALUES ('Head');
INSERT INTO `roles` (`title`) VALUES ('Deputy Head');
INSERT INTO `roles` (`title`) VALUES ('Manager');
INSERT INTO `roles` (`title`) VALUES ('Senior member');
INSERT INTO `roles` (`title`) VALUES ('Junior member');
INSERT INTO `roles` (`title`) VALUES ('Apprentice');

INSERT INTO `admins` (`email`, `password`, `firstname`, `lastname`, `createdat`, `updatedat`) VALUES ('admin@google.com', '1234', 'Mr. Admin', 'Panel', CURRENT_TIME, CURRENT_TIME);

INSERT INTO `employees` (`id`, `email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES (NULL, 'steve@google.com', '1234', 'Steve', 'Jobs', 6, 1, '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000');
INSERT INTO `employees` (`id`, `email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES (NULL, 'mark@google.com', '1234', 'Mark', 'Zuckerberg', 6, 2, CURRENT_TIME, CURRENT_TIME, CURRENT_TIME);
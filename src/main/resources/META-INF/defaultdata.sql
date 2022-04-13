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

INSERT INTO `employees` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('steve@google.com', '1234', 'Steve', 'Jobs', 6, 1, '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000');
INSERT INTO `employees` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('mark@google.com', '1234', 'Mark', 'Zuckerberg', 6, 2, CURRENT_TIME, CURRENT_TIME, CURRENT_TIME);
INSERT INTO `employees` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('john@google.com', '1234', 'John', 'Deck', 6, 3, '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000');
INSERT INTO `employees` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('smith@google.com', '1234', 'Mark', 'Smith', 6, 4, CURRENT_TIME, CURRENT_TIME, CURRENT_TIME);
INSERT INTO `employees` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('issac@google.com', '1234', 'Issac', 'Bigby', 6, 4, '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000');
INSERT INTO `employees` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('emma@google.com', '1234', 'Emma', 'Watson', 6, 5, CURRENT_TIME, CURRENT_TIME, CURRENT_TIME);
INSERT INTO `employees` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('elsa@google.com', '1234', 'Elsa', 'Jean', 6, 5, '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000');
INSERT INTO `employees` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('jia@google.com', '1234', 'Jia', 'Lissa', 6, 6, CURRENT_TIME, CURRENT_TIME, CURRENT_TIME);

INSERT INTO `holiday_requests` (`title`, `datestart`, `dateend`, `status`, `employee_id`) VALUES ('Birthday', ADDTIME(CURRENT_DATE, '1 0:0:0.000000'), ADDTIME(CURRENT_DATE, '2 0:0:0.000000'), 'pending', 1);
INSERT INTO `holiday_requests` (`title`, `datestart`, `dateend`, `status`, `employee_id`) VALUES ('Family event', ADDTIME(CURRENT_DATE, '1 0:0:0.000000'), ADDTIME(CURRENT_DATE, '2 0:0:0.000000'), 'pending', 2);
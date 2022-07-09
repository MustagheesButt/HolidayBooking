INSERT INTO `Department` (`title`) VALUES ('Office');
INSERT INTO `Department` (`title`) VALUES ('Engineering');
INSERT INTO `Department` (`title`) VALUES ('Plumbing');
INSERT INTO `Department` (`title`) VALUES ('Roofing');
INSERT INTO `Department` (`title`) VALUES ('Carpentry');
INSERT INTO `Department` (`title`) VALUES ('Bricklaying');

INSERT INTO `Role` (`title`) VALUES ('Head');
INSERT INTO `Role` (`title`) VALUES ('Deputy Head');
INSERT INTO `Role` (`title`) VALUES ('Manager');
INSERT INTO `Role` (`title`) VALUES ('Senior member');
INSERT INTO `Role` (`title`) VALUES ('Junior member');
INSERT INTO `Role` (`title`) VALUES ('Apprentice');

INSERT INTO `Admin` (`email`, `password`, `firstname`, `lastname`, `createdat`, `updatedat`) VALUES ('admin@admin.com', 'admin', 'Administrator', '11', CURRENT_TIME, CURRENT_TIME);

INSERT INTO `Employee` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('steve@google.com', '1234', 'Steve', 'Jobs', 6, 1, '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000');
INSERT INTO `Employee` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('mark@google.com', '1234', 'Mark', 'Zuckerberg', 6, 2, CURRENT_TIME, CURRENT_TIME, CURRENT_TIME);
INSERT INTO `Employee` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('john@google.com', '1234', 'John', 'Deck', 6, 3, '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000');
INSERT INTO `Employee` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('ali@google.com', '1234', 'Ali', 'Khan', 6, 3, '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000');
INSERT INTO `Employee` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('abraham@google.com', '1234', 'John', 'Abraham', 6, 3, '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000');
INSERT INTO `Employee` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('smith@google.com', '1234', 'Mark', 'Smith', 6, 4, CURRENT_TIME, CURRENT_TIME, CURRENT_TIME);
INSERT INTO `Employee` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('will@google.com', '1234', 'Will', 'Smith', 6, 4, CURRENT_TIME, CURRENT_TIME, CURRENT_TIME);
INSERT INTO `Employee` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('chad@google.com', '1234', 'Chad', 'Son', 6, 4, CURRENT_TIME, CURRENT_TIME, CURRENT_TIME);
INSERT INTO `Employee` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('issac@google.com', '1234', 'Issac', 'Bigby', 6, 4, '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000');
INSERT INTO `Employee` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('emma@google.com', '1234', 'Emma', 'Watson', 6, 5, CURRENT_TIME, CURRENT_TIME, CURRENT_TIME);
INSERT INTO `Employee` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('elsa@google.com', '1234', 'Elsa', 'Jean', 6, 5, '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000');
INSERT INTO `Employee` (`email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`, `createdat`, `updatedat`) VALUES ('jia@google.com', '1234', 'Jia', 'Lissa', 6, 6, CURRENT_TIME, CURRENT_TIME, CURRENT_TIME);

INSERT INTO `HolidayRequest` (`title`, `datestart`, `dateend`, `duration`, `status`, `employee_id`) VALUES ('Birthday', ADDTIME(CURRENT_DATE, '1 0:0:0.000000'), ADDTIME(CURRENT_DATE, '2 0:0:0.000000'),1, 'pending', 1);
INSERT INTO `HolidayRequest` (`title`, `datestart`, `dateend`, `duration`, `status`, `employee_id`) VALUES ('Family event', ADDTIME(CURRENT_DATE, '1 0:0:0.000000'), ADDTIME(CURRENT_DATE, '2 0:0:0.000000'),1, 'pending', 2);
INSERT INTO `Role` (`title`) VALUES ('Head');
INSERT INTO `Role` (`title`) VALUES ('Deputy Head');
INSERT INTO `Role` (`title`) VALUES ('Apprentice');
INSERT INTO `Role` (`title`) VALUES ('Manager');
INSERT INTO `Role` (`title`) VALUES ('Senior member');
INSERT INTO `Role` (`title`) VALUES ('Junior member');

INSERT INTO `Department` (`title`) VALUES ('Office');
INSERT INTO `Department` (`title`) VALUES ('Engineering');
INSERT INTO `Department` (`title`) VALUES ('Bricklaying');
INSERT INTO `Department` (`title`) VALUES ('Plumbing');
INSERT INTO `Department` (`title`) VALUES ('Roofing');
INSERT INTO `Department` (`title`) VALUES ('Carpentry');

INSERT INTO `Employee` (`createdat`, `updatedat`, `email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`) VALUES ('2022-02-01 13:31:33.000000', '2022-07-09 12:21:23.000000', 'henry@gmail.com', 'asdf', 'Henry', 'C', 6, 1, '2022-04-07 14:51:43.000000');
INSERT INTO `Employee` (`createdat`, `updatedat`, `email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`) VALUES (CURRENT_TIME, CURRENT_TIME,                                 'mike@gmail.com', 'asdf', 'Mike', 'Tyson', 6, 2, CURRENT_TIME);
INSERT INTO `Employee` (`createdat`, `updatedat`, `email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`) VALUES ('2022-07-09 12:21:23.000000', '2022-07-09 12:21:23.000000', 'beckyg@gmail.com', 'asdf', 'Becky', 'G', 6, 3, '2022-02-01 13:31:33.000000');
INSERT INTO `Employee` (`createdat`, `updatedat`, `email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`) VALUES ('2022-07-09 12:21:23.000000', '2022-07-09 12:21:23.000000', 'ali@gmail.com', 'asdf', 'M', 'Ali', 6, 3, '2022-02-01 13:31:33.000000');
INSERT INTO `Employee` (`createdat`, `updatedat`, `email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`) VALUES ('2022-07-09 12:21:23.000000', '2022-07-09 12:21:23.000000', 'a.licoln@gmail.com', 'asdf', 'Lincoln', 'Abraham', 6, 3, '2022-02-01 13:31:33.000000');
INSERT INTO `Employee` (`createdat`, `updatedat`, `email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`) VALUES (CURRENT_TIME, CURRENT_TIME,                                 'luffy@gmail.com', 'asdf', 'Luffy', 'D. Monkey', 6, 4, CURRENT_TIME);
INSERT INTO `Employee` (`createdat`, `updatedat`, `email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`) VALUES (CURRENT_TIME, CURRENT_TIME,                                 'zorro@gmail.com', 'asdf', 'Roronoa', 'Zorro', 6, 4, CURRENT_TIME);
INSERT INTO `Employee` (`createdat`, `updatedat`, `email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`) VALUES (CURRENT_TIME, CURRENT_TIME,                                 'namis@gmail.com', 'asdf', 'Nami', 'Swan', 6, 4, CURRENT_TIME);
INSERT INTO `Employee` (`createdat`, `updatedat`, `email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`) VALUES ('2022-07-09 12:21:23.000000', '2022-07-09 12:21:23.000000', 'edward.newgate@gmail.com', 'asdf', 'Edward', 'Newgate', 6, 4, '2022-02-01 13:31:33.000000');
INSERT INTO `Employee` (`createdat`, `updatedat`, `email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`) VALUES (CURRENT_TIME, CURRENT_TIME,                                 'loidf@gmail.com', 'asdf', 'Loid', 'Forger', 6, 5, CURRENT_TIME);
INSERT INTO `Employee` (`createdat`, `updatedat`, `email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`) VALUES ('2022-07-09 12:21:23.000000', '2022-07-09 12:21:23.000000', 'gigac@gmail.com', 'asdf', 'Giga', 'Chad', 6, 5, '2022-02-01 13:31:33.000000');
INSERT INTO `Employee` (`createdat`, `updatedat`, `email`, `password`, `firstname`, `lastname`, `department_id`, `role_id`, `joiningdate`) VALUES (CURRENT_TIME, CURRENT_TIME,                                 'ammarf@gmail.com', 'asdf', 'Ammar', 'The F.', 6, 6, CURRENT_TIME);

INSERT INTO `Admin` (`email`, `password`, `firstname`, `lastname`, `createdat`, `updatedat`) VALUES ('admin@admin.com', 'admin', 'Administrator', '11', CURRENT_TIME, CURRENT_TIME);

INSERT INTO `HolidayRequest` (`title`, `datestart`, `dateend`, `status`, `employee_id`) VALUES ('Sick leave', ADDTIME(CURRENT_DATE, '1 0:0:0.000000'), ADDTIME(CURRENT_DATE, '2 0:0:0.000000'), 'pending', 1);
INSERT INTO `HolidayRequest` (`title`, `datestart`, `dateend`, `status`, `employee_id`) VALUES ('Paid leave', ADDTIME(CURRENT_DATE, '1 0:0:0.000000'), ADDTIME(CURRENT_DATE, '2 0:0:0.000000'), 'pending', 2);
INSERT INTO `HolidayRequest` (`title`, `datestart`, `dateend`, `status`, `employee_id`) VALUES ('feeling sick', ADDTIME(CURRENT_DATE, '1 0:0:0.000000'), ADDTIME(CURRENT_DATE, '2 0:0:0.000000'), 'pending', 3);


INSERT INTO `roles` (`title`) VALUES 

INSERT INTO `admins` (`email`, `password`, `firstname`, `lastname`, `createdat`, `updatedat`) VALUES ('admin@google.com', '1234', 'Mr. Admin', 'Panel', CURRENT_TIME, CURRENT_TIME);
INSERT INTO `employees` (`id`, `email`, `password`, `firstname`, `lastname`, `role`, `createdat`, `updatedat`) VALUES (NULL, 'test@google.com', '1234', 'Steve', 'Jobs', '', '2022-04-07 14:51:43.000000', '2022-04-07 14:51:43.000000');
INSERT INTO `wilosuser` (`wilosuser_id`, `login`, `name`, `firstname`, `emailAddress`, `password`) VALUES 
('2c90a1b21031b5b1011031b85b7c0001', 'pm', 'pm', 'pm', NULL, 'd6bc0a30ec44a6557cd8051867929628'),
('2c90a1b21031b5b1011031b8a0360002', 'pd', 'pd', 'pd', NULL, 'f424233fe38a41de39cc3542ca9b1818'),
('2c90a1b21031d7ef011031de53650001', 'parti', 'parti', 'parti', 'marti@parti.com', '4a928ea62e001b6cb3780d2137b8eb00'),
('2c90c28e1031312e01103131ecff0001', 'admin', 'admin', 'admin', 'admin@wilos.com', '85aac14e99386ee8f68c89372821b1ca');

-- admin/mdpadmin
INSERT INTO `administrator` (`administrator_id`) VALUES ('2c90c28e1031312e01103131ecff0001');
-- pd/pdpdpd
INSERT INTO `projectdirector` (`projectdirector_id`) VALUES ('2c90a1b21031b5b1011031b8a0360002');
-- pm/pmpmpm
INSERT INTO `processmanager` (`processmanager_id`) VALUES ('2c90a1b21031b5b1011031b85b7c0001');
-- parti/mdpparti
INSERT INTO `participant` (`participant_id`) VALUES ('2c90a1b21031d7ef011031de53650001');
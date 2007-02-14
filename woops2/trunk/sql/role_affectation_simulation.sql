--on positionne tous les roles de toutes les taches concretes pour le participant parti
UPDATE concreteroledescriptor
SET `participant_id` = (SELECT wilosuser_id  
						FROM wilosuser 
						WHERE login LIKE '%parti%');
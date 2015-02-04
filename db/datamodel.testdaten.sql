USE ugnm1415g2;


INSERT INTO User (idUser, elo)
VALUES
	(1, 10),
	(2, 10),
	(3, 10),
	(4, 10),
	(5, 10);

INSERT INTO Hashtag (text)
VALUES ('Java'),('Assembler'),('For-Loop'),('Analysis'),('Polynome'),('Lagrange-Restglied');

INSERT INTO UserToHashtag (idUser, idHashtag)
VALUES (1,4),(1,5),(1,6),(2,4),(2,5),(2,6),(3,1),(3,2),(3,3),(4,1),(4,3),(5,1),(5,2),(5,3),(5,4),(5,5),(5,6);

INSERT INTO Post (text, idUser)
VALUES
	('How do I write a for-loop?', 1),
	('Where can I find the toilet?', 4),
	('In the building E2, first floor.', 2),
	('How does the JFrame-constructor work?', 2),
	('I think he is right', 5),
	('You should google for it.', 3),
	('I want to recherche it...', 5),
	('I already googled, but couldn\'nt find anything :(', 2);

INSERT INTO Question (idQuestion)
VALUES (1),(2),(4);
INSERT INTO Answer (idAnswer, rating, idQuestion)
VALUES (3,100,2),(5,0,2),(6,0,4),(7,0,4),(8,0,4);
INSERT INTO QuestionToHashtag (idQuestion, idHashtag)
VALUES (1,3),(1,1),(4,1);
INSERT INTO FavouriteQuestionToUser (idUser, idQuestion)
VALUES (2,2),(4,1),(4,2),(5,4);

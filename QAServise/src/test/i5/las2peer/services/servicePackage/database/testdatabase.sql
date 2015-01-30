USE ugnm1415g2;

-- delete all:
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS User, Post, Question, Answer, Hashtag,
					QuestionToHashtag, UserToHashtag, UserToRatedAnswer, FavouriteQuestionToUser;
SET FOREIGN_KEY_CHECKS=1;

-- entities
CREATE TABLE User
(
	idUser bigint NOT NULL,
	elo int NOT NULL,
	image varchar(255),
	contact varchar(255),
	email varchar(255),
	PRIMARY KEY (idUser)
);
CREATE TABLE Post
(
	idPost int NOT NULL AUTO_INCREMENT,
	-- not timestamp for tests
	-- original: "timestamp timestamp NOT NULL,"
	timestamp timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
	text varchar(255) NOT NULL,
	idUser bigint NOT NULL,
	PRIMARY KEY (idPost),
	FOREIGN KEY (idUser) REFERENCES User(idUser)
);
CREATE TABLE Question
(
	idQuestion int NOT NULL,
	PRIMARY KEY (idQuestion),
	FOREIGN KEY (idQuestion) REFERENCES Post(idPost) ON DELETE CASCADE
);
CREATE TABLE Answer
(
	idAnswer int NOT NULL,
	rating int NOT NULL DEFAULT 0,
	idQuestion int NOT NULL,
	PRIMARY KEY (idAnswer),
	FOREIGN KEY (idAnswer) REFERENCES Post(idPost) ON DELETE CASCADE,
	FOREIGN KEY (idQuestion) REFERENCES Question(idQuestion) ON DELETE CASCADE -- has to be deleted with deletion from Post
);
CREATE TABLE Hashtag
(
	idHashtag int NOT NULL AUTO_INCREMENT,
	text varchar(45) NOT NULL UNIQUE,
	PRIMARY KEY (idHashtag)
);


-- n/m-relationships

CREATE TABLE UserToRatedAnswer
(
	idUserToRatedAnswer int NOT NULL AUTO_INCREMENT,
	idUser bigint NOT NULL,
	idAnswer int NOT NULL,
	PRIMARY KEY (idUserToRatedAnswer),
	FOREIGN KEY (idUser) REFERENCES User(idUser) ON DELETE CASCADE,
	FOREIGN KEY (idAnswer) REFERENCES Answer(idAnswer) ON DELETE CASCADE
);
CREATE TABLE UserToHashtag
(
	idUserToHashtag int NOT NULL AUTO_INCREMENT,
	idUser bigint NOT NULL,
	idHashtag int NOT NULL,
	PRIMARY KEY (idUserToHashtag),
	FOREIGN KEY (idUser) REFERENCES User(idUser) ON DELETE CASCADE,
	FOREIGN KEY (idHashtag) REFERENCES Hashtag(idHashtag) ON DELETE CASCADE
);
CREATE TABLE QuestionToHashtag (
	idQuestionToHashtag int NOT NULL AUTO_INCREMENT,
	idQuestion int NOT NULL,
	idHashtag int NOT NULL,
	PRIMARY KEY (idQuestionToHashtag),
	FOREIGN KEY (idQuestion) REFERENCES Question(idQuestion) ON DELETE CASCADE,
	FOREIGN KEY (idHashtag) REFERENCES Hashtag(idHashtag) ON DELETE CASCADE
);
CREATE TABLE FavouriteQuestionToUser (
	idFavouriteQuestionToUser int NOT NULL AUTO_INCREMENT,
	idQuestion int NOT NULL,
	idUser bigint NOT NULL,
	PRIMARY KEY (idFavouriteQuestionToUser),
	FOREIGN KEY (idQuestion) REFERENCES Question(idQuestion) ON DELETE CASCADE,
	FOREIGN KEY (idUser) REFERENCES User(idUser) ON DELETE CASCADE
);









INSERT INTO User (idUser, elo, image, contact, email)
VALUES
	(1, 10, 'http://link/to/frank/celler.jpg', 'http://mathe.de/frank/celler.html', 'f.celler@gmail.com'),
	(2, 10, 'http://link/to/mike/leblanche.png', 'http://mathe.de/micky/conta.html', 'micky.l@web.de'),
	(3, 10, 'http://imagebank/4d6age6/es.jpg', 'http://progra.com/how/jelly.html', 'howDickson@gmail.com'),
	(4, 10, 'http://dome.com/celler.jpg', 'http://student.org/isoe/random.html', 'my.M.a.i.l@rwth-aachen.de'),
	(5, 10, 'http://wow/it/is/houston.svg', 'http://alleskoenner.de/whp/huhu.html', 'another.adresse@web.com');

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
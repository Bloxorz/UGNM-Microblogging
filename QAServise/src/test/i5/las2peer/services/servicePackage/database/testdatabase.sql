USE ugnm1415g2;

-- delete all:
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS User, Post, Question, Answer, Hashtag, Expertise,
					QuestionToHashtag, HashtagToExpertise, UserToExpertise,
					UserToQuestion, FavoriteQuestionsToUser;
SET FOREIGN_KEY_CHECKS=1;

-- entities
CREATE TABLE User
(
	idUser int NOT NULL AUTO_INCREMENT,
	rating int NOT NULL,
	image varchar(255) NOT NULL,
	contact varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	pass varchar(255) NOT NULL,
	PRIMARY KEY (idUser)
);
CREATE TABLE Post
(
	idPost int NOT NULL AUTO_INCREMENT,
	timestamp timestamp NOT NULL,
	text varchar(255) NOT NULL,
	idUser int NOT NULL,
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
	rating int NOT NULL,
	idQuestion int NOT NULL,
	PRIMARY KEY (idAnswer),
	FOREIGN KEY (idAnswer) REFERENCES Post(idPost) ON DELETE CASCADE,
	FOREIGN KEY (idQuestion) REFERENCES Question(idQuestion)  -- no cascade: has to be deleted with deletion from Post
);
CREATE TABLE Hashtag
(
	idHashtag int NOT NULL AUTO_INCREMENT,
	text varchar(45) NOT NULL,
	PRIMARY KEY (idHashtag)
);
CREATE TABLE Expertise
(
	idExpertise int NOT NULL AUTO_INCREMENT,
	text varchar(45) NOT NULL,
	PRIMARY KEY (idExpertise)
);


-- n/m-relationships

CREATE TABLE UserToQuestion
(
	idUserToQuestion int NOT NULL AUTO_INCREMENT,
	idUser int NOT NULL,
	idQuestion int NOT NULL,
	PRIMARY KEY (idUserToQuestion),
	FOREIGN KEY (idUser) REFERENCES User(idUser) ON DELETE CASCADE,
	FOREIGN KEY (idQuestion) REFERENCES Question(idQuestion) ON DELETE CASCADE
);
CREATE TABLE UserToExpertise
(
	idUserToExpertise int NOT NULL AUTO_INCREMENT,
	idUser int NOT NULL,
	idExpertise int NOT NULL,
	PRIMARY KEY (idUserToExpertise),
	FOREIGN KEY (idUser) REFERENCES User(idUser) ON DELETE CASCADE,
	FOREIGN KEY (idExpertise) REFERENCES Expertise(idExpertise) ON DELETE CASCADE
);
CREATE TABLE HashtagToExpertise
(
	idHashtagΤοΕxpertise int NOT NULL AUTO_INCREMENT,
	idHashtag int NOT NULL,
	idExpertise int NOT NULL,
	PRIMARY KEY (idHashtagΤοΕxpertise),
	FOREIGN KEY (idHashtag) REFERENCES Hashtag(idHashtag) ON DELETE CASCADE,
	FOREIGN KEY (idExpertise) REFERENCES Expertise(idExpertise) ON DELETE CASCADE
);
CREATE TABLE QuestionToHashtag (
	idQuestionToHashtag int NOT NULL AUTO_INCREMENT,
	idQuestion int NOT NULL,
	idHashtag int NOT NULL,
	PRIMARY KEY (idQuestionToHashtag),
	FOREIGN KEY (idQuestion) REFERENCES Question(idQuestion) ON DELETE CASCADE,
	FOREIGN KEY (idHashtag) REFERENCES Hashtag(idHashtag) ON DELETE CASCADE
);
CREATE TABLE FavoriteQuestionsToUser (
	idQuestion int NOT NULL,
	idUser int NOT NULL,
	FOREIGN KEY (idQuestion) REFERENCES Question(idQuestion) ON DELETE CASCADE,
	FOREIGN KEY (idUser) REFERENCES User(idUser) ON DELETE CASCADE
);









INSERT INTO User (rating, image, contact, email, pass)
VALUES
	(0, 'http://link/to/frank/celler.jpg', 'http://mathe.de/frank/celler.html', 'f.celler@gmail.com', '4fd5ab'),
	(0, 'http://link/to/mike/leblanche.png', 'http://mathe.de/micky/conta.html', 'micky.l@web.de', 'ac8d90'),
	(0, 'http://imagebank/4d6age6/es.jpg', 'http://progra.com/how/jelly.html', 'howDickson@gmail.com', '56d0ef'),
	(0, 'http://dome.com/celler.jpg', 'http://student.org/isoe/random.html', 'my.M.a.i.l@rwth-aachen.de', 'f098bc'),
	(0, 'http://wow/it/is/houston.svg', 'http://alleskoenner.de/whp/huhu.html', 'another.adresse@web.com', '87ade9');

INSERT INTO Hashtag (text)
VALUES ('Java'),('Assembler'),('For-Loop'),('All'),('Analysis'),('Polynome');

INSERT INTO Expertise (text)
VALUES ('Lineare Algebra'),('Analysis für Informatiker'),('Einführung in die Programmierung'),('Praktikum Unternehmensgründung und Neue Medien');

INSERT INTO HashtagToExpertise (idHashtag, idExpertise)
VALUES (1,3),(1,4),(3,3),(3,4),(4,1),(4,2),(4,3),(4,4),(5,1),(5,2),(6,1),(6,2);

INSERT INTO UserToExpertise (idUser, idExpertise)
VALUES (1,1),(1,2),(2,1),(2,2),(3,3),(3,4),(4,1),(4,4),(5,1),(5,2),(5,3),(5,4);

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
VALUES (1,3),(1,1),(2,4),(4,1);
INSERT INTO UserToQuestion (idUser, idQuestion)
VALUES (4,1),(4,2),(5,4);
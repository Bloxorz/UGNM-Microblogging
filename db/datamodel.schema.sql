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
	FOREIGN KEY (idQuestion) REFERENCES Post(idPost)
);
CREATE TABLE Answer
(
	idAnswer int NOT NULL,
	rating int NOT NULL,
	idQuestion int NOT NULL,
	PRIMARY KEY (idAnswer),
	FOREIGN KEY (idAnswer) REFERENCES Post(idPost),
	FOREIGN KEY (idQuestion) REFERENCES Question(idQuestion)
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
	FOREIGN KEY (idUser) REFERENCES User(idUser),
	FOREIGN KEY (idQuestion) REFERENCES Question(idQuestion)
);
CREATE TABLE UserToExpertise
(
	idUserToExpertise int NOT NULL AUTO_INCREMENT,
	idUser int NOT NULL,
	idExpertise int NOT NULL,
	PRIMARY KEY (idUserToExpertise),
	FOREIGN KEY (idUser) REFERENCES User(idUser),
	FOREIGN KEY (idExpertise) REFERENCES Expertise(idExpertise)
);
CREATE TABLE HashtagToExpertise
(
	idHashtagΤοΕxpertise int NOT NULL AUTO_INCREMENT,
	idHashtag int NOT NULL,
	idExpertise int NOT NULL,
	PRIMARY KEY (idHashtagΤοΕxpertise),
	FOREIGN KEY (idHashtag) REFERENCES Hashtag(idHashtag),
	FOREIGN KEY (idExpertise) REFERENCES Expertise(idExpertise)
);
CREATE TABLE QuestionToHashtag (
	idQuestionToHashtag int NOT NULL AUTO_INCREMENT,
	idQuestion int NOT NULL,
	idHashtag int NOT NULL,
	PRIMARY KEY (idQuestionToHashtag),
	FOREIGN KEY (idQuestion) REFERENCES Question(idQuestion),
	FOREIGN KEY (idHashtag) REFERENCES Hashtag(idHashtag)
);
CREATE TABLE FavoriteQuestionsToUser (
	idQuestion int NOT NULL,
	idUser int NOT NULL,
	FOREIGN KEY (idQuestion) REFERENCES Question(idQuestion),
	FOREIGN KEY (idUser) REFERENCES User(idUser)
);


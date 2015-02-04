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
	PRIMARY KEY (idUser)
);
CREATE TABLE Post
(
	idPost int NOT NULL AUTO_INCREMENT,
	timestamp timestamp NOT NULL,
	text varchar(512) NOT NULL,
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







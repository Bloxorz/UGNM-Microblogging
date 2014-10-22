INSERT INTO User (rating, image, contact, email, pass)
VALUES 
	(0, 'http://link/to/frank/celler.jpg', 'http://cont/frank/celler.html', 'f.celler@gmail.com', '4fd5ab'),
	(0, 'http://link/to/mike/leblanche.png', 'http://cont/micky/conta.html', 'micky.l@web.de', 'ac8d90'),
	(0, 'http://imagebank/4d6age6/es.jpg', 'http://cont/how/jelly.html', 'howDickson@gmail.com', '56d0ef'),
	(0, 'http://dome.com/celler.jpg', 'http://cont.org/isoe/random.html', 'my.M.a.i.l@rwth-aachen.de', 'f098bc'),
	(0, 'http://wow/it/is/houston.svg', 'http://ops.de/whp/huhu.html', 'another.adresse@web.com', '87ade9');

INSERT INTO Hashtag (text)
VALUES ('Java'),('C++'),('basic programming'),('all');

INSERT INTO Expertise (text)
VALUES ('Java Microedition Mobile development'),('gcc compiler'),('guitar');

INSERT INTO HashtagToExpertise (idHashtag, idExpertise)
VALUES (1,1),(2,2),(3,1),(3,2),(4,1),(4,2),(4,3);

INSERT INTO UserToExpertise (idUser, idExpertise)
VALUES (1,3),(2,2),(5,1),(5,2);
	
INSERT INTO Post (text, idUser)
VALUES 
	('How do I write a for-loop?#basic programming', 1),
	('Where can I find the toilet?#all', 5),
	('In the building E2, first floor.', 3),
	('How does the JFrame-constructor work?#basic programming#Java', 2),
	('I think he is right', 4),
	('You should google for it.', 5),
	('I want to recherche it...', 5),
	('I already googled, but couldn\'nt find anything :(', 1);
	
INSERT INTO Question (idQuestion)
VALUES (1),(2),(4);
INSERT INTO Answer (idAnswer, rating, idQuestion)
VALUES (3,100,2),(5,0,2),(6,0,1),(7,0,4),(8,0,1);
INSERT INTO QuestionToHashtag (idQuestion, idHashtag)
VALUES (1,3),(2,4),(4,3),(4,1);
INSERT INTO UserToQuestion (idUser, idQuestion)
VALUES (4,1),(4,2),(5,1),(5,2),(5,4);
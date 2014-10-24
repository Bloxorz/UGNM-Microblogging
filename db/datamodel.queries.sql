USE ugnm1415g2;

-- Who is registered?
SELECT email, contact FROM User;

-- How many are registered?
SELECT Count(idUser) AS 'Anzahl der registrierten Nutzer' FROM User;

-- Wer hat eine Emailaddresse bei web?
SELECT * FROM User
WHERE email LIKE '%@web%';

-- Alle gestellten Fragen
SELECT text 'Fragen'
FROM Question INNER JOIN Post
	ON Question.idQuestion = Post.idPost;

-- Alle Fragenids mit Antwortenids
SELECT idAnswer AS 'Diese Antwort bezieht sich auf...', Question.idQuestion AS '...diese Frage'
FROM Question INNER JOIN Answer
	ON Question.idQuestion = Answer.idQuestion;

-- Jetzt mit Text
SELECT qText AS Frage, aText AS Antwort
FROM 
	(
		SELECT text AS qText, idQuestion AS qIdQ
		FROM Question INNER JOIN Post
			ON Question.idQuestion = Post.idPost
	) AS t1
	INNER JOIN
    (
		SELECT text AS aText, idQuestion AS aIdQ
		FROM Answer INNER JOIN Post
			ON Answer.idAnswer = Post.idPost
	) AS t2
	ON qIdQ = aIdQ;



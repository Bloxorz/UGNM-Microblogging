//CALLBACKS NEEDED BECAUSE ASYNC!!!
//GET
function allQuestions($http, setQuestionsCallback) {
  var buildUrl = "api/questions?access_token="+ localStorage.token;
  $http({method: "GET", url: buildUrl, headers: {
		    'Content-Type': 'application/json'}})
		      .then(function(result) {
		          setQuestionsCallback(result.data);
		      });
}


function hashtagCollection($http) {
  var buildUrl = "api/hashtag?access_token="+ localStorage.token;
  return $http({method: "GET", url: buildUrl, headers: {
        'Content-Type': 'application/json'}})
          .then(function(result) {
               return result.data;
          });
}


function getUser($http, setUserCallback) {
var buildUrl = "api/user?access_token="+ localStorage.token;
$http({method: "GET", url: buildUrl, headers: {
      'Content-Type': 'application/json'}})
        .then(function(result) {
            setUserCallback(result.data);
        });
}

function getUserExpertises($http) {
  var buildUrl = "api/user?access_token="+ localStorage.token;
  return $http({method: "GET", url: buildUrl, headers: {
        'Content-Type': 'application/json'}})
          .then(function(result) {
              return result.data.expertises;
          });

}

function getAnswers($http, questionId, cb) {
  var buildUrl = "api/answers/question/" + questionId + "?access_token="+ localStorage.token;
  return $http({method: "GET", url: buildUrl, headers: {
        'Content-Type': 'application/json'}})
          .then(function(result) {
              cb(result.data);
          });

}

//POST
function askQuestion(question) {
var buildUrl = "api/question?access_token="+ localStorage.token;
$http({method: "POST", url: buildUrl, headers: {
      'Content-Type': 'application/json'}, data : question});
}

function answerQuestion(answer) {
var buildUrl = "api/answers/question/{questionId}" + answer.id + "?access_token="+ localStorage.token;
$http({method: "POST", url: buildUrl, headers: {
      'Content-Type': 'application/json'}, data : answer.text});
}

function upvoteAnswer(answerId) {
var buildUrl = "api/answers/" + answerId + "?access_token="+ localStorage.token;
$http({method: "POST", url: buildUrl, headers: {
      'Content-Type': 'application/json'}});
}

function favourQuestion(questionId) {
  var buildUrl = "api/user/bookmark/" + questionId + "?access_token="+ localStorage.token;
  $http({method: "POST", url: buildUrl, headers: {
        'Content-Type': 'application/json'}});
}

//PUT
function updateUser($http, hashtags) {
  var buildUrl = "api/user?access_token="+ localStorage.token;
  $http({method: "PUT", url: buildUrl, headers: {
        'Content-Type': 'application/json'}, data : hashtags});
}

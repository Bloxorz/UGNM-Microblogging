var questionAnswerApp = angular.module('qaApp', ['ngRoute', 'ngTagsInput']);

questionAnswerApp.config(function($routeProvider) {
    // configure the routing rules here
    $routeProvider
        .when('/dashboard', {
            // note: manage.html is renamed to dashboard.html,
            //       the old dashboard is deprecated and will be removed
            templateUrl: 'dashboard.html',
            controller: 'DashboardCtrl'
        })
        .when('/preferences', {
            templateUrl: 'preferences.html',
            controller: 'PreferencesCtrl'
        })
        .when('/question', {
            templateUrl: 'askquestion.html',
            controller: 'AskquestionCtrl'
        })
        .when('/answers/:questionId?', {
            templateUrl: 'answers.html',
            controller: 'AnswersCtrl'
        })
        .when('/:accessToken?', {
            templateUrl: 'dashboard.html',
            controller: 'DashboardCtrl'
        })
        .when('/', {
            templateUrl: 'dashboard.html',
            controller: 'DashboardCtrl'
        })
        ;


    //routing DOESN'T work without html5Mode
    //$tagsProvider.html5Mode(true);
});

questionAnswerApp.controller('DashboardCtrl', function($rootScope, $scope, $routeParams, $route, $http) {

    $rootScope.page = {
        title: "Dashboard",
        dashboardActive: "active",
        preferencesActive: "",
        askQuestionActive: ""
    };

    $scope.questions = [];
    //set questions async
    allQuestions($http,function(data) {
        $scope.questions = data;
    });


    //reqiures function
    $scope.allTags = function() {
      return hashtagCollection($http);
    }

    $scope.favour = function(questionId) {
      favourQuestion($http, questionId, function() {
        $route.reload();
      });
    }

    $scope.usertags = {};

    $scope.isLoggedIn = function() {
      var val = !(localStorage.getItem("access_token") === null);
      return val;
    }

    getUserExpertises($http, function(data) {
      $scope.usertags = data;
    });

    $scope.userExpertises = function() {
      if(localStorage.getItem("access_token") === null) {
        alert("You must be logged in to use this feature");
        return [];
      } else {
      	 return $scope.usertags;
      }

    }

    $scope.getQuestionHeadline = function(text) {
      var n = text.indexOf('?');
      var maxLength = 60;
      if(n > 0 && n <= maxLength) {
        return text.substring(0,n);
      } else {
        return text.substring(0,maxLength) + "...";
      }
    }

});

questionAnswerApp.controller('PreferencesCtrl', function($rootScope, $scope, $routeParams, $route, $http) {
    $rootScope.page = {
        title: "Preferences",
        dashboardActive: "",
        preferencesActive: "active",
        askQuestionActive: ""
    };

    //$scope.expertises = {};

    $scope.saveUser = function() {
      if(localStorage.getItem("access_token") === null) {
        alert("You must be logged in to use this feature");
        return;
      }
        updateUser($http, $scope.expertises);
    }

    $scope.allTags = function() {
      return hashtagCollection($http);
    }

    getUser($http, function(data) {
      $scope.user.expertises = data.hashtags;
      $scope.user.elo = data.elo;
    })
  

});

questionAnswerApp.controller('AnswersCtrl', function($rootScope, $scope, $routeParams, $route, $http) {
  $scope.question = {};
  $scope.answers = {};
  $scope.answer = {};

  getAnswers($http,$routeParams.questionId, function(data) {
    $scope.question = data.question;
    $scope.answers = data.answers;
    $scope.answer.idPost = data.question.idPost;
  });

  $scope.postAnswer = function() {
    if(localStorage.getItem("access_token") === null) {
      alert("You must be logged in to use this feature");
      return;
    }
      answerQuestion($http, $scope.answer, function() {
        $route.reload();
      });
  }

  $scope.favour = function() {
    if(localStorage.getItem("access_token") === null) {
      alert("You must be logged in to use this feature");
      return;
    }
      favourQuestion($http, $scope.question.idPost);
  }

  $scope.like = function(answerId) {
    if(localStorage.getItem("access_token") === null) {
      alert("You must be logged in to use this feature");
      return;
    }
    upvoteAnswer($http, answerId, function() {
      $route.reload();
    });
  }
});
questionAnswerApp.controller('AskquestionCtrl', function($rootScope, $scope, $routeParams, $route, $http) {
  $rootScope.page = {
      title: "Ask",
      dashboardActive: "",
      preferencesActive: "",
      askQuestionActive: "active"
  };
  $scope.question = {};

  $scope.postQuestion = function () {
    if(localStorage.getItem("access_token") === null) {
      alert("You must be logged in to use this feature");
      return;
    }
    askQuestion($http, $scope.question, function(questionId) {
       document.location.href="/api/#";
      $route.reload();
    });
  }

  $scope.allTags = function() {
    return hashtagCollection($http);
  }
});

//usefull helper functions here
questionAnswerApp.filter('filterByTags', function() {
    return function(items, tags) {
        var filtered = [];
        if(!tags){
            return filtered;
        }
        if (tags.length == 0) {
            filtered = items;
        }
        for (var i = items.length - 1; i >= 0; i--) {
            for (var j = tags.length - 1; j >= 0; j--) {
                if (items[i].hashtags) {
                    for (var k = items[i].hashtags.length - 1; k >= 0; k--) {
                        if (items[i].hashtags[k].text.toUpperCase().indexOf(tags[j].text.toUpperCase()) > -1) {
                            filtered.push(items[i]);
                        }
                    }
                }
            };
        };
        return filtered;
    };
});

questionAnswerApp.filter('orderObjectBy', function(){
 return function(input, attribute) {
    if (!angular.isObject(input)) return input;

    var array = [];
    for(var objectKey in input) {
        array.push(input[objectKey]);
    }

    array.sort(function(a, b){
        a = parseInt(a[attribute]);
        b = parseInt(b[attribute]);
        return b - a;
    });
    return array;
 }
});

questionAnswerApp.filter('setdate', function($filter) {
    return function(msAgo) {

        var msNow = Date.now();

        var minutes = Math.floor((msNow - msAgo) / 60000);
        var hours = Math.floor(minutes / 60);
        var days = Math.floor(hours / 60);

        t = days > 60
            ? $filter('date')(new Date(msAgo), 'd MMM yyyy')
            : hours > 23
                ? $filter('date')(new Date(msAgo), 'd MMM')
                : minutes > 59
                    ? hours + ' hours ago'
                    : minutes + ' minutes ago';

        return t;
    };
});
/*
questionAnswerApp.filter('setdate', function($filter) {
    return function(date) {
        var dArr = date.split(/[.\s:]/), t;
        if(dArr.length === 6) {

            var msAgo = new Date(dArr[2], dArr[1] - 1, dArr[0], dArr[3], dArr[4], dArr[5]) .getTime();

            var msNow = Date.now();

            var minutes = Math.floor((msNow - msAgo) / 60000);
            var hours = Math.floor(minutes / 60);
            var days = Math.floor(hours / 60);

            t = days > 60
                ? $filter('date')(new Date(msAgo), 'd MMM yyyy')
                : hours > 23
                    ? $filter('date')(new Date(msAgo), 'd MMM')
                    : minutes > 59
                        ? hours  ' hours ago'
                        : minutes  ' minutes ago';

        }
        return t;
    };
});
*/

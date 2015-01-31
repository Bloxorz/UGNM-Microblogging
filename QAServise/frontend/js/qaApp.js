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
        .when('/answers/:questionId?', {
            templateUrl: 'answers.html',
            controller: 'AnswersCtrl'
        })
        .otherwise({
            redirectTo: '/'
        });


    //routing DOESN'T work without html5Mode
    //$tagsProvider.html5Mode(true);

});

questionAnswerApp.controller('DashboardCtrl', function($rootScope, $scope, $routeParams, $route, $http) {
    $rootScope.page = {
        title: "Dashboard",
        dashboardActive: "active",
        preferencesActive: ""
    };

    $scope.questions = [{hashtags:[{text:"Java"},{text:"Programmierung"}], favourcount:2,timestamp:"30.01.2014 23:26:14", text:"How to write a for-loop in Java", idPost:1, isFavourite:true},
    {hashtags:[{text:"Javascript"},{text:"AngularJs"}, {text:"Frontend"}, {text:"ng-view"}], favourcount:4,timestamp:"28.01.2014 12:14:14", text:"Javascript AngularJs ng-view Problem", idPost:2, isFavourite:true}];

    //set questions async
    allQuestions($http,function(data) {
        $scope.questions = data;
        alert(JSON.stringify(data));
    })


    //reqiures function
    $scope.allTags = function() {
      return hashtagCollection($http);
    }

    $scope.favour = function(questionId) {
      favourQuestion(questionId);
    }

    $scope.userExpertises = function() {
      return getUserExpertises($http);
    }

});

questionAnswerApp.controller('PreferencesCtrl', function($rootScope, $scope, $routeParams, $route, $http) {
    $rootScope.page = {
        title: "Preferences",
        dashboardActive: "",
        preferencesActive: "active"
    };
});

questionAnswerApp.controller('AnswersCtrl', function($rootScope, $scope, $routeParams, $route, $http) {
  alert($routeParams.questionId);

  $scope.question = {};
  $scope.answers = {};
  getAnswers($http, function(data) {
    $scope.question = data.question;
    $scope.answers = data.answers;
  });
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

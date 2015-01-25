var app = (function () {

    var active, widget, isActive, addQuestion, getAnswersToQuestion, client;

    client = new ServiceClient("http://localhost:8080/");

    isActive = function() {
        if (active !== undefined) {
            active.removeClass('active');
        }
    };

    getAllQuestions = function() {
        var html, tpl = 
        '<div class="my-header">' +
            '<h4>Alle Fragen</h4>' +
            '<hr>' +
        '</div>' +
        '{{#.}}' +
            '<div class="question">' +
                '<p>' +
                    '<a href="#">css</a>, ' +
                '</p>' +
                '<a href="#" class="my_links">' +
                    '{{text}}' +
                '</a>' +
                '<div>' +
                    '<span class="pull-left">{{timestamp}}</span>' +
                    '<span class="pull-right">0x favorisiert</span>' +
                '<div class="clearfix"></div>' +
                '</div>' +
                '<hr>' +
            '</div>' +
        '{{/.}}';

        client.getAllQuestions(
            function(data,type) {
                html = Mustache.render(tpl, JSON.parse(data));
                $('.content').html(html);
                
                $('.my_links').each(function () {
                    $(this).click(function(e) {
                        e.preventDefault();
                        getAnswersToQuestion();
                    });
                });
            },
            function(error) {
                $('.content').html(error);
            }
        );
    };

    addQuestion = function() {
        var html, tpl =
        '<div class="my-header">' +
            '<h4>Neue Frage</h4>' +
            '<hr>' +
        '</div>' +
        '<div>' +
        '{{#logedin}}' +
            '<form role="form">' +
                '<div class="form-group">' +
                    '<label for="hashtags">Hashtags</label>' +
                    '<div id="hashtags"></div>' +
                '</div>' +
                '<div class="form-group">' +
                    '<label for="frage">Ihre Frage</label>' +
                    '<textarea id="frage" class="form-control" rows="5"></textarea>' +
                '</div>' +
                '<p>' +
                    '<button id="submit" class="btn btn-success">Submit</button>' +
                '</p>' +
            '</form>' +
        '{{/logedin}}' +
        '{{^logedin}}' +
        '<p>' +
            'Bitte einloggen!' +
        '</p>' +
        '{{/logedin}}' +
        '</div>';

        var config = {'logedin': !client.isAnonymous()};

        html = Mustache.render(tpl, config);
        $('.content').html(html);

        if(config.logedin) {
            client.getHashtags(
                function(data, type) {
                    var list = [], hashtags;
                    hashtags = JSON.parse(data);
                    hashtags.forEach(function(hashtag) {
                        list.push(hashtag.text);
                    });
                    widget = new AutoComplete('hashtags', list);
                },
                function(error) {
                    $('.content').html(error);
            });
        }
    };

    getAnswersToQuestion = function() {
        var html, tpl =
        '<div class="my-header">' +
            '<h4>Frage</h4>' +
            '<hr>' +
            '</div>' +
        '<div class="question">' +
            '<p>' +
                '<a href="#">css</a>, <a href="#">bootstrap</a>' +
            '</p>' +
            '<p>' +
                '{{question.text}}' +
            '</p>' +
            '<div>' +
                '<span class="pull-left">{{question.timestamp}}</span>' +
                '<span class="pull-right"><a href="#">favorisieren</a></span>' +
                '<div class="clearfix"></div>' +
            '</div>' +
        '</div>' +
        '<div class="my-header">' +
            '<h4>Antworten</h4>' +
            '<hr>' +
        '</div>' +
        '{{#answers}}' +
        '<div class="answer">' +
            '<p>' +
               '{{text}}' +
            '</p>' +
            '<div>' +
                '<span class="pull-left">{{timestamp}}</span>' +
                '<span class="pull-right"><a href="#">Gefält mir | 1</a></span>' +
                '<div class="clearfix"></div>' +
            '</div>' +
            '<hr>' +
        '</div>' +
        '{{/answers}}' +
        '<div class="my-header">' +
            '<h4>Ihr Antwort</h4>' +
            '<hr>' +
        '</div>' +
        '{{#logedin}}' +
        '<form role="form">' +
            '<p>' +
                '<textarea class="form-control" rows="5"></textarea>' +
            '</p>' +
            '<p>' +
                '<button type="submit" class="btn btn-success">Submit</button>' +
            '</p>' +
        '</form>' +
        '{{/logedin}}' +
        '{{^logedin}}' +
        '<p>' +
            'Bitte einlogen!' +
        '</p>' +
        '{{/logedin}}';

//dummy data
        var data = {};
        data.question = {"text": "How old are you?", "timestamp": "heute"};
        data.answers =[{"text": "18", "timestamp": "gerade eben"},{"text": "15", "timestamp": "gerade"}];
        data.logedin = !client.isAnonymous();

        html = Mustache.render(tpl, data);
        $('.content').html(html);
    };

    return {
        init: function() {
            $('#allquestions').on('click', function(e){
                e.preventDefault();
                isActive();
                active = $(this).addClass('active');
                getAllQuestions();
            });

            $('#add_question_m, #add_question').on('click', function(e){
                e.preventDefault();
                isActive();
                addQuestion();
            });

            $('#myquestions').on('click', function(e){
                e.preventDefault();
                isActive();
                active = $(this).addClass('active');
                getAllQuestions();
            });

            $('body').on('click', '#submit', function(e) {
                e.preventDefault();

                var q = $('#frage').val(), h = [], list, json;

                list = widget.getValue();
                list.forEach(function(item) {
                    h.push({'text':item[0].value});
                });

                json = {'question':{'text': q}, 'hashtags': h};

                client.addQuestion(
                    JSON.stringify(json),
                    function(data, type) {
                        $('.content').html(data);
                    },
                    function(error) {
                        $('.content').html(error);
                    }
                );
            });
        }
    };
})();

$(function(){
    app.init();
});
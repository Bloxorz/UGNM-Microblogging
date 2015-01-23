(function () {
    // create new instance of ServiceClient, given its endpoint URL
    var client = new ServiceClient("http://localhost:8080/"),
        active, widget,

    app = {
        init: function() {
            var self = this,
                allQ = $('#allquestions'),
                addQ = $('.my-btn'),
                addQ2 = $('#addq'),
                myQ = $('#myquestions');

            allQ.click(function(e) {
                e.preventDefault();
                if (active !== undefined)
                    active.removeClass('active');
                active = $(this).addClass('active');
                self.getQuestions();
            });

            myQ.click(function(e) {
                e.preventDefault();
                if (active !== undefined)
                    active.removeClass('active');
                active = $(this).addClass('active');
                self.getQuestions();
            });

            addQ2.click(function(e) {
                e.preventDefault();
                self.addQuestion();
            });

            addQ.click(function(e) {
                e.preventDefault();
                self.addQuestion();
            });

            var tmp = $('#megasuper');
            tmp.click(function(e) {
                e.preventDefault();
                client.getMethod(
                    function(data, type) {
                        console.log(data);
                    },
                    function(error) {
                        $('.content').html(error);
                    });
            });
        },
        getQuestions: function() {
            var html, self = this,
                tpl = 
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
                            self.getAnswers();
                        });
                    });
                },
                function(error) {
                    $('.content').html(error);
                }
            );
        },
        addQuestion: function() {
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

            var data = {};
            data.logedin = !client.isAnonymous();

            html = Mustache.render(tpl, data);
            $('.content').html(html);

            if(data.logedin) {
                $('#submit').click(function(e) {
                    e.preventDefault();
                    var json = {}, list;


                    json.question = {'text': $('#frage').val()};
                    json.hashtags = [];
                    list = widget.getValue();
                    list.forEach(function(item) {
                        json.hashtags.push({'text':item[0].value});
                    });

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
                    }
                );
        }

        },
        getAnswers: function() {
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
        }
    };

    app.init();

})();

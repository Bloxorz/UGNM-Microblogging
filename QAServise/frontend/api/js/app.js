(function () {
    // create new instance of ServiceClient, given its endpoint URL
    var client = new ServiceClient("http://localhost:8080/"),
        active, 

    app = {
        init: function() {
            var self = this,
                allQ = $('#allquestions'),
                addQ = $('.my-btn'),
                addQ2 = $('#addq'),
                myQ = $('#myquestions');

                //console.log(addQ2);

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
                self.renderAddQuestinon();
            });

            addQ.click(function(e) {
                e.preventDefault();
                self.renderAddQuestinon();
            });
        },
        getQuestions: function(){
            client.getAllQuestions(
                function(data,type){
                   var questions = JSON.parse(data),
                    div, question, tag, time, p, div2, span, span2, cont, hr, heading, header;

                    header = document.createElement('div');
                    header.className = 'my-header';
                   
                    heading = document.createElement('h4');
                    $(heading).text('Alle Fragen');
                   
                    hr = document.createElement('hr');

                    $(header).append(heading);
                    $(header).append(hr);

                    cont = document.createElement('div');
                   
                    $(cont).html(header);

                    questions.forEach(function(item) {
                        div = document.createElement('div');
                        div.className = 'question';
                        p = document.createElement('p');
                        question = document.createElement('a');
                        question.href = '';
                        $(question).text(item.text);
                        div2 = document.createElement('div');
                        span = document.createElement('span');
                        span.className = 'pull-left';
                        $(span).text(item.timestamp);
                        span2 = document.createElement('span');
                        span2.className = 'pull-right';
                        $(span2).text('0x favor');
                        hr = document.createElement('hr');
                        $(div).append(question);
                        $(div2).append(span);
                        $(div2).append(span2);
                        $(div).append(div2);
                        $(div).append(hr);
                        $(cont).append(div);
                        
                    });

                    $('.col-sm-9').html(cont);
                },
                function(error) {
                    $('.col-sm-9').text('error');
                }
            );
        },
        renderAddQuestinon: function() {
            var header, heading, hr, div, form, divFormGroup, p, divFormGroup2, input, textarea, button, label;

            header = document.createElement('div');
            header.setAttribute('class', 'my-header');
           
            heading = document.createElement('h4');
            $(heading).text('New Question');
           
            hr = document.createElement('hr');

            $(header).append(heading);
            $(header).append(hr);

            div = document.createElement('div');

            form = document.createElement('form');
            form.setAttribute('role', 'form');
            //form.setAttribute('method', 'POST');



            divFormGroup = document.createElement('div');
            divFormGroup.setAttribute('class', 'form-group');

            label = document.createElement('label');
            label.setAttribute('for', 'hashtag');

            input = document.createElement('input');
            input.setAttribute('type', 'text');
            input.setAttribute('class', 'form-control');
            input.setAttribute('id', 'hashtag');
            input.setAttribute('placeholder', 'Kompetenz');

            $(divFormGroup).append(label);
            $(divFormGroup).append(input);

            divFormGroup2 = document.createElement('div');
            divFormGroup2.setAttribute('class', 'form-group');

            label2 = document.createElement('label');
            label2.setAttribute('for', 'frage');

            textarea = document.createElement('textarea');
            textarea.setAttribute   ('class', 'form-control');
            textarea.setAttribute('id', 'frage');
            textarea.setAttribute('row', '5');

            $(divFormGroup2).append(label2);
            $(divFormGroup2).append(textarea);

            $(form).append(divFormGroup);
            $(form).append(divFormGroup2)

            p = document.createElement('p');

            button = document.createElement('button');
            //button.setAttribute('type', 'submit');
            button.setAttribute('class', 'btn btn-success');
            button.setAttribute('id', 'submit');




            $(p).append(button);

            $(form).append(p);

            $(div).append(form);

            $('.col-sm-9').html(header);
            $('.col-sm-9').append(div);

            $('#submit').click(function(e) {
                e.preventDefault();
                var expertise = $('#hashtag').val();
                var question = $('#frage').val();
                //var add = {'tag': hashtag, 'question': question};
                
                client.addQuestion('{"text": "How old are you?", "userId":"5"}', 
                    function(data,type){
                    
                    $('.col-sm-9').html(data);
                },
                function(error) {
                    $('.col-sm-9').text(error);
                });
            });
        }
    };

    app.init();

})();

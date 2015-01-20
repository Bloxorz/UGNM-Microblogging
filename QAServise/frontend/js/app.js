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
                   var data = JSON.parse(data);
                   $('.col-sm-9').html('<p>' + JSON.stringify(data) +'</p>');
                },
                function(error) {
                    $('.col-sm-9').text('error');
                }
            );
        },
        renderAddQuestinon: function() {
                    $('.col-sm-9').html('<div class="my-header"> <h4>Neue Frage</h4> <hr> </div> <div> <form role="form" action="" method="post"> <div class="form-group"> <label for="hashtag">Kompetenz</label> <input type="text" class="form-control" id="hashtag" placeholder="Kompetenz"> </div> <div class="form-group"> <label for="frage">Ihre Frage</label> <textarea id="frage" class="form-control" rows="5"></textarea> </div> <p> <button type="submit" class="btn btn-success">Submit</button> </p> </form> </div>');
        }
    };

    app.init();

})();

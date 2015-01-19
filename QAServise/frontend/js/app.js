(function () {
    // create new instance of ServiceClient, given its endpoint URL
    var client = new ServiceClient("http://localhost:8080/"),
        active, 

    app = {

        init: function() {
            var self = this,
                allQ = $('#allquestions'),
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
        }
    };

    app.init();

})();

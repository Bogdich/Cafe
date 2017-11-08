

//        $('#sign-in').validate({
//            rules: {
//                number: {
//                    required: true,
//                    minlength: 9,
//                    maxlength: 9,
//                    pattern: /^(29|33|44)[0-9]*$/
//                }
//            },
//            messages: {
//                number: {
//                    required: "error1",
//                    minlength: "error2",
//                    maxlength: "error3",
//                    pattern: "error4"
//                }
//            },
//            errorClass : "invalid",
//            errorPlacement: function (error, element) {
////                if (element.attribute('login') === 'number') {
////                    element.data('wrong', error)
////                }
//                console.log(error[0].outerText);
////                console.log($(element[0]).next()[0]);
//                $($(element[0]).next()[0]).attr('data-error', error[0].outerText);
//
////                $($(element[0]).next()[0]).attr('class', "invalid");
//            }
//        });
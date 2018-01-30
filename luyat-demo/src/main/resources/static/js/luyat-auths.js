(function($){
	var publicKey = null;
	var methods = new Object();
	//初始化
	methods.init = new Object();
	methods.init.getPublicKey = function(){
		$.ajax({  
            url: "/login/rdsPwd",
            type: "post",  
            dataType: "text",  
            success: function(data) {  
                if(data){publicKey = data;}
                if(data == null){
                 	toastr.error("数据错误-获取公钥失败");
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
			 	toastr.error("ajax错误-获取公钥失败:"+textStatus);
			}
        });
	};
	methods.init.event = function(){
		$("#login").click(function(){
			methods.login();
		});
	};
	methods.init.validate = function(){
		$("#loginform").bootstrapValidator({
	        message: "This value is not valid",
	        feedbackIcons: {
	            valid: "glyphicon glyphicon-ok",
	            invalid: "glyphicon glyphicon-remove",
	            validating: "glyphicon glyphicon-refresh"
	        },
	        fields: {
	        	username:{
	        		 message: "The username is not valid",
	        		 validators:{
	        		 		notEmpty: {
		                        message: '数据不能为空'
		                   	},
		                    regexp: {
		                        regexp: /^[a-zA-Z]\w{5,17}$/,
		                        message: '以字母开头,长度在6~18之间,只能包含字符、数字和下划线'
		                    }
	        		} 
	        	},
	        	password:{
	        		 message: "The password is not valid",
	        		 validators:{
	        		 		notEmpty: {
		                        message: '数据不能为空'
		                   	},
		                    regexp: {
		                        regexp: /^[a-zA-Z]\w{5,17}$/,
		                        message: '以字母开头,长度在6~18之间,只能包含字符、数字和下划线'
		                    }
	        		} 
	        	}
	        }	
    	});
	};
	methods.login = function(){
		var validate = $("#loginform").bootstrapValidator("validate");
		var flag = $("#loginform").data("bootstrapValidator").isValid();
		if(!flag){return false;}

		var ustring = $.trim($("#username").val());  
		var pstring = $.trim($("#password").val());
		var encrypt = new JSEncrypt();
		if(publicKey != null){
			encrypt.setPublicKey(publicKey);
            var password = encrypt.encrypt(pstring);
            var username = encrypt.encrypt(ustring);
            if(password.length < 20) {
                toastr.error("登录失败，请稍后重试...");  
            }else{  
                 $.ajax({  
                	 url: "/login/login",
                	 type: "post",  
                	 data: {"username": username,"password": password},
                	 dataType: "json",
                	 success:function(result){
                		 if(result.result == "failure"){
                			 toastr.error("登录失败:"+result.text);
                		 }else{
                			 //console.log(result);
                			 //toastr.success(result);
                			 location.href="/login/index"
                		 }
                	 },
                     error: function(XMLHttpRequest, textStatus, errorThrown) {
         			 	 toastr.error(textStatus);
         			 }
                });  
		    }
		}
	};
	//初始化
	methods.init.getPublicKey();
	methods.init.event();
	methods.init.validate();
})(jQuery);
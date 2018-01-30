(function($,sr){
    var debounce = function (func, threshold, execAsap) {
    	var timeout;
        return function debounced () {
            var obj = this, args = arguments;
            function delayed () {
                if (!execAsap)
                    func.apply(obj, args); 
                timeout = null; 
            }
            if (timeout)
                clearTimeout(timeout);
            else if (execAsap)
                func.apply(obj, args);
            timeout = setTimeout(delayed, threshold || 100); 
        };
    };
    jQuery.fn[sr] = function(fn){  return fn ? this.bind('resize', debounce(fn)) : this.trigger(sr); };
})(jQuery,'smartresize');
var CURRENT_URL = window.location.href.split('#')[0].split('?')[0],
$BODY = $('body'),
$MENU_TOGGLE = $('#menu_toggle'),
$SIDEBAR_MENU = $('#sidebar-menu'),
$SIDEBAR_FOOTER = $('.sidebar-footer'),
$LEFT_COL = $('.left_col'),
$RIGHT_COL = $('.right_col'),
$NAV_MENU = $('.nav_menu'),
$FOOTER = $('footer');

//Sidebar
function init_sidebar() {
	var setContentHeight = function () {
		$RIGHT_COL.css('min-height', $(window).height());
		var bodyHeight = $BODY.outerHeight(),
			footerHeight = $BODY.hasClass('footer_fixed') ? -10 : $FOOTER.height(),
			leftColHeight = $LEFT_COL.eq(1).height() + $SIDEBAR_FOOTER.height(),
			contentHeight = bodyHeight < leftColHeight ? leftColHeight : bodyHeight;
		contentHeight -= $NAV_MENU.height() + footerHeight;
		$RIGHT_COL.css('min-height', contentHeight);
	};
  $SIDEBAR_MENU.find('a').on('click', function(ev) {
	  console.log('clicked - sidebar_menu');
        var $li = $(this).parent();

        if ($li.is('.active')) {
            $li.removeClass('active active-sm');
            $('ul:first', $li).slideUp(function() {
                setContentHeight();
            });
        } else {
            // prevent closing menu if we are on child menu
            if (!$li.parent().is('.child_menu')) {
                $SIDEBAR_MENU.find('li').removeClass('active active-sm');
                $SIDEBAR_MENU.find('li ul').slideUp();
            }else
            {
				if ( $BODY.is( ".nav-sm" ) )
				{
					$SIDEBAR_MENU.find( "li" ).removeClass( "active active-sm" );
					$SIDEBAR_MENU.find( "li ul" ).slideUp();
				}
			}
            $li.addClass('active');

            $('ul:first', $li).slideDown(function() {
                setContentHeight();
            });
        }
    });

// toggle small or large menu 
$MENU_TOGGLE.on('click', function() {
		console.log('clicked - menu toggle');
		if ($BODY.hasClass('nav-md')) {
			$SIDEBAR_MENU.find('li.active ul').hide();
			$SIDEBAR_MENU.find('li.active').addClass('active-sm').removeClass('active');
		} else {
			$SIDEBAR_MENU.find('li.active-sm ul').show();
			$SIDEBAR_MENU.find('li.active-sm').addClass('active').removeClass('active-sm');
		}
	$BODY.toggleClass('nav-md nav-sm');
	setContentHeight();
});
	// check active menu
	$SIDEBAR_MENU.find('a[href="' + CURRENT_URL + '"]').parent('li').addClass('current-page');
	$SIDEBAR_MENU.find('a').filter(function () {
		return this.href == CURRENT_URL;
	}).parent('li').addClass('current-page').parents('ul').slideDown(function() {
		setContentHeight();
	}).parent().addClass('active');
	// recompute content when resizing
	$(window).smartresize(function(){  
		setContentHeight();
	});
	setContentHeight();
	// fixed sidebar
	if ($.fn.mCustomScrollbar) {
		$('.menu_fixed').mCustomScrollbar({
			autoHideScrollbar: true,
			theme: 'minimal',
			mouseWheel:{ preventDefault: true }
		});
	}
};
//Sidebar

//动态菜单
function setMenus(data){
	$(".side-menu").html("");
	$.each(data,function(index,item){
		var parent = item.pid.split(".");
		if(parent.length == 1){
			if($("#parnet_"+parent[0]).length != 0){
				$("#parnet_"+parent[0]).find(".text").html(item.name);
			}else{
				var htmls = "<li id=\"parnet_"+item.permission_id+"\">"+
	            	"<a><i class=\"fa fa-home\"></i>"+item.name+"<span class=\"fa fa-chevron-down\"></span></a>"+
	                "<ul class=\"nav child_menu\"></ul>"+
	            "</li>";
				$(".side-menu").append(htmls);
			}
		}
		if(parent.length == 2){
			if($("#parnet_"+parent[0]).length != 0){
				$("#parnet_"+parent[0]).find("ul").append("<li><a onclick=\"javascript:setIframe('"+item.uri+"')\" href=\"javascript:void(0);\">"+item.name+"</a></li>");
			}else{
				var htmls = "<li id=\"parnet_"+parent[0]+"\">"+
				            	"<a><i class=\"fa fa-home\"></i><span class=\"text\"></span><span class=\"fa fa-chevron-down\"></span></a>"+
				                "<ul class=\"nav child_menu\">" +
				                "<li id=\"parnet_"+parent[1]+"\"><a onclick=\"javascript:setIframe('"+item.uri+"')\" href=\"javascript:void(0);\">"+item.name+"</a></li>"+
				                "</ul>"+
				            "</li>";
				$(".side-menu").append(htmls);
			}
		}
	});
}
function setIframe(url){
	$("#J_iframe").attr("src",url);
}
$(document).ready(function() {
	$.ajax({
		type: "POST",
		url: "../home/permissionMenus",
		data: {},
		dataType: "json",
		async: false,
		success: function(data) {setMenus(data);}
	});
	init_sidebar();	
});	

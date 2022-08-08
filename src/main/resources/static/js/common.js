$.fn.radioSelect = function(val) {
	this.each(function() {
		var $this = $(this);
		if($this.val() == val)
			$this.attr('checked', true);
	});
	return this;
};


jQuery(function() {

    navbar();

    jQuery(document).ready(function() {

        jQuery('body').addClass('load');

    });

	// Menu-Dropdown
    jQuery('.dropdown > li > .drop_title').on("click", function(){
        var $this = jQuery(this);
        if(!jQuery(this).parent("li").hasClass('on')) {
            $this.next('.drop_data').slideDown(300, function() {
                $this.parent("li").addClass('on');
            });
        } else {
            $this.next('.drop_data').slideUp(300, function() {
                $this.parent("li").removeClass('on');
            });
        }
    });

    // List-Dropdown
    jQuery(function() {
        jQuery('.list .dropdown_list > li > .drop_title_list').on("click", function(){
            var $this = jQuery(this).parent('li');
            var $thisIndex = jQuery(this).parent('li').index();
            if(!$this.hasClass('on')) {
                $this.addClass('on');
                jQuery('.view .dropdown_list > li').eq($thisIndex).addClass('on');
            } else {
                jQuery('.view .dropdown_list > li').eq($thisIndex).removeClass('on');
                $this.removeClass('on');
            }
        });
    });

    // Navbar
    function navbar() {
        var navbar = jQuery('.navbar');
        var toggle_button = navbar.find('.btn_close');

        toggle_button.on('click',function(){
            if (navbar.hasClass('open')){
                navbar.removeClass('open');
            }else {
                navbar.addClass('open');
            }
        });
    }

    // Navbar close
    jQuery(function() {
        jQuery(".overlay").on("click", function() {
            jQuery(".btn_close").click();
        });
    });


    // NavbarMenu
    var navbarMenu = {
        click : function (target, speed) {
            var _self = this,
            $target = jQuery(target);
            _self.speed = speed || 300;

            $target.each(function(){
                if(findChildren(jQuery(this))) {
                return;
            }
            jQuery(this).addClass('noDepth');
        });

        function findChildren(obj) {
            return obj.find('> ul').length > 0;
        }

        $target.on('click','a', function(e){
            e.stopPropagation();
            var $this = jQuery(this),
            $depthTarget = $this.next(),
            $siblings = $this.parent().siblings();

            $this.parent('li').find('ul li').removeClass('on');
            $siblings.removeClass('on');
            $siblings.find('ul').slideUp(250);

            if($depthTarget.css('display') == 'none') {
                _self.activeOn($this);
                $depthTarget.slideDown(_self.speed);
            } else {
                $depthTarget.slideUp(_self.speed);
                _self.activeOff($this);
            }

        });
        $target.find('ul > li > p > a').on('click', function(e){
            e.stopPropagation();
            var $this = jQuery(this),
            $depthTarget = $this.parent().next(),
            $siblings = $this.parent().parent().siblings();

            $this.parent().parent('li').find('ul li').removeClass('on');
            $siblings.removeClass('on');
            $siblings.find('ul').slideUp(250);

            if($depthTarget.css('display') == 'none') {
                _self.activeOn($this.parent());
                $depthTarget.slideDown(_self.speed);
            } else {
                $depthTarget.slideUp(_self.speed);
                _self.activeOff($this.parent());
            }

        });

        },
        activeOff : function($target) {
            $target.parent().removeClass('on');
        },
        activeOn : function($target) {
            $target.parent().addClass('on');
        }
    };

    // Call NavbarMenu
    navbarMenu.click('.navbar_menu li', 300)

    // Progress
    jQuery('.progress-bar').each(function() {
        jQuery(this).css({
            'width': jQuery(this).attr('data-per') + '%',
            'background-color': jQuery(this).attr('data-bgcolor')
        });
        if(jQuery(this).attr('data-label') == 'yes') {
            jQuery(this).append('<span>' + jQuery(this).attr('data-per') + '%</span>');
        }
    });

    // File Upload
    jQuery('.btn_upload').each(function() {
        var label = jQuery(this).attr('for');
        jQuery(this).prev('#'+label).change(function(e){
            var fileName = e.target.files[0].name;
            jQuery('#file-name-' + label).attr('value', fileName).css('text-align','left');
        });
    });

    // TabMenu
    jQuery("#tab_menu_content > div").hide();
    // jQuery("#tab_menu li:first").attr("class","current");
    jQuery("#tab_menu_content > div:first").fadeIn();

    jQuery('#tab_menu a').click(function(e) {
        e.preventDefault();
        jQuery("#tab_menu_content > div").hide();
        jQuery("#tab_menu li").attr("class","");
        jQuery(this).parent().attr("class","current");
        jQuery('#' + jQuery(this).attr('class')).fadeIn();
    });

    // InputCheck Label
    jQuery("#choose").on("click", function() {
        if (jQuery(this).is(":checked")) {
            jQuery(this).siblings("input:text").addClass("on").focus();
        } else {
            jQuery(this).siblings("input:text").removeClass("on");
        }
    });

    // 칸트 차트 왼쪽 메뉴 항상 열기
    jQuery('.drop_title_list > .task').each(function() {
        jQuery(this).find('.select').attr('onclick', "layer_open(this,'pop_common_view')");
    });


    // ToolTip

    // $('.summary_box').hide();

    // $('.summary').mouseover(function(){
    //     $(this).find('.summary_box').stop().fadeIn();

    // });
    // $('.summary').mouseleave(function(){
    //     $(this).find('.summary_box').stop().hide();
    // });


    // 평가 체크박스
    /*jQuery('.multi_check').on("click", function() {
        jQuery(this).parent().parent().children('li').removeClass('on');
        jQuery(this).parent().addClass('on').prevAll('li').addClass('on');
        return false;
    });*/


    // Datapicker
    // jQuery('.btn_datepicker').each(function() {
    //     jQuery(this).on('click', function(){
    //         jQuery(this).prev('input[type="text"]').datepicker({
    //             showOtherMonths: true,
    //             selectOtherMonths: true,
    //             showMonthAfterYear: true,
    //             yearSuffix: '년',
    //             prevText: '이전 달',
    //             nextText: '다음 달',
    //             dateFormat: "yy-mm-dd",
    //             monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
    //             monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
    //             dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    //             dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    //             dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    //             beforeShowDay: function(day) {
    //                 var result;
    //                 switch(day.getDay()) {
    //                     case 0:
    //                         result = [true, "date-sunday"];
    //                         break;
    //                     case 6:
    //                         result = [true, "date-saturday"];
    //                         break;
    //                     default:
    //                         result = [true, ""];
    //                         break;
    //                 }
    //                 return result;
    //             }
    //         }).datepicker("show");
    //     });
    // });

});


// LayerPopup open
function layer_open(obj, el){
    var temp = jQuery('#' + el);
    jQuery(obj).addClass('is-focus');
    temp.addClass('show');

    // 탭 메뉴 첫번째로 리셋
    $("#tab_menu > li").removeClass("current");
    $("#tab_menu > li").eq(0).addClass("current");
    $("#tab_menu_content > div").hide();
    $("#tab_menu_content > div").eq(0).show();


    temp.attr("tabindex","0").focus();
    jQuery('.btn_close').on('focus', function(){
        jQuery(this).keydown(function()  {
            if(event.which == 9) {
                temp.attr("tabindex","0").focus();
            }
        });
    });

    jQuery('.popup_bg').on('scroll touchmove mousewheel click', function(event) {
        event.preventDefault();
        event.stopPropagation();
        return false;
    });
    // bg 클릭시 팝업 닫기
    jQuery('.popup_bg').on('click', function(event) {
        jQuery(obj).removeClass('is-focus');
        temp.removeClass('show');
    });
}

// LayerPopup close
function layer_close(el) {
    var temp = jQuery('#' + el);
    temp.removeClass('show');
    jQuery('.is-focus').focus().removeClass('is-focus');
}


function inputTimeColon(time) {
	/*if(time.value.length > 4){
		alert("입력 형식을 hhmm 으로");
		time.value = "";
		return false;
	}*/
    // 먼저 기존에 들어가 있을 수 있는 콜론(:)기호를 제거한다.
    var replaceTime = time.value.replace(/\:/g, "");

    // 글자수가 4 ~ 5개 사이일때만 동작하게 고정한다.
    if(replaceTime.length >= 4 && replaceTime.length < 5) {

        // 시간을 추출
        var hours = replaceTime.substring(0, 2);

        // 분을 추출
        var minute = replaceTime.substring(2, 4);

        // 시간은 24:00를 넘길 수 없게 세팅
        if(hours + minute > 2400) {
            alert("시간은 24시를 넘길 수 없습니다.");
            time.value = "24:00";
            return false;
        }

        // 분은 60분을 넘길 수 없게 세팅
        if(minute > 60) {
            alert("분은 60분을 넘길 수 없습니다.");
            time.value = hours + ":00";
            return false;
        }

        // 콜론을 넣어 시간을 완성하고 반환한다.
        time.value = hours + ":" + minute;
    }
}

function formToJsonString(formId){
	var formSerializeArray = $(formId).serializeArray();
	var object = {};
	for (var i = 0; i < formSerializeArray.length; i++){
	    object[formSerializeArray[i]['name']] = formSerializeArray[i]['value'];
	}
 
	var json = JSON.stringify(object);
	return json;	
}
$(function() {

	navbar();

	// Navbar
    function navbar() {
        var navbar = $('.navbar');
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
    $(".overlay").on("click", function() {
        $(".btn_close").click();
    });

    // NavbarMenu
    var navbarMenu = {
        click : function (target, speed) {
            var _self = this,
            $target = $(target);
            _self.speed = speed || 300;

            $target.each(function(){
                if(findChildren($(this))) {
                return;
            }
            $(this).addClass('noDepth');
        });

        function findChildren(obj) {
            return obj.find('> ul').length > 0;
        }

        $target.on('click','a', function(e){
            e.stopPropagation();
            var $this = $(this),
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
            var $this = $(this),
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

    // Menu-Dropdown
    $('.dropdown > li > .drop_title').on("click", function(){
        var $this = $(this);
        if(!$(this).parent("li").hasClass('on')) {
            $this.next('.drop_data').slideDown(300, function() {
                $this.parent("li").addClass('on');
            });
        } else {
            $this.next('.drop_data').slideUp(300, function() {
                $this.parent("li").removeClass('on');
            });
        }
    });
    // 공지사항(첫번째 항목 메뉴 열기(메인))
    $(".dropdown").eq(0).find("li").addClass("on").find(".drop_data").slideDown(300);

    // List-Dropdown    
    $('.list .dropdown_list > li > .drop_title_list').on("click", function(){
        var $this = $(this).parent('li');
        var $thisIndex = $(this).parent('li').index();
        if(!$this.hasClass('on')) {
            $this.addClass('on');
            $('.view .dropdown_list > li').eq($thisIndex).addClass('on');
        } else {
            $('.view .dropdown_list > li').eq($thisIndex).removeClass('on');
            $this.removeClass('on');
        }
    });    

    // Datapicker
	$('.btn_datepicker').each(function() {
		$(this).on('click', function(){
			$(this).prev('input[type="text"]').datepicker({
				showOtherMonths: true,
				selectOtherMonths: true,
				showMonthAfterYear: true,
				yearSuffix: '년',
				prevText: '이전 달',  
				nextText: '다음 달',
				dateFormat: "yy-mm-dd",
				monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				dayNames: ['일', '월', '화', '수', '목', '금', '토'],
				dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
				dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
				beforeShowDay: function(day) {
					var result;
					switch(day.getDay()) {
						case 0:
							result = [true, "date-sunday"];
							break;
						case 6:
							result = [true, "date-saturday"];
							break;
						default:
							result = [true, ""];
							break;
					}					
					return result;
				}
			}).datepicker("show");
		});
	});    
});

// LayerPopup open
function layer_open(obj, el){
    var temp = $('#' + el);
    $(obj).addClass('is-focus');
    temp.addClass('show');
    temp.attr("tabindex","0").focus();
    $('.btn_close').on('focus', function(){
        $(this).keydown(function()  {
            if(event.which == 9) {
                temp.attr("tabindex","0").focus();
            }
        });
    });

    $('.popup_bg').on('scroll touchmove mousewheel click', function(event) {
        event.preventDefault();
        event.stopPropagation();
        return false;
    });
    // bg 클릭시 팝업 닫기
    $('.popup_bg').on('click', function(event) {
        $(obj).removeClass('is-focus');
        temp.removeClass('show');
    });
}

// LayerPopup close
function layer_close(el) {
    var temp = $('#' + el);
    temp.removeClass('show');
    $('.is-focus').focus().removeClass('is-focus');
}

$(function(){
	$("#rollpic").cycle({
		fx:"fadeout",
	    pauseonhover:"true",
	    slides:"> a",
	    caption:"#rollCaption span",
	    captionTemplate:"{{title}}",
	    pager:"#rollpicPager"
	});
	$("#rollCaption ").css("opacity",0.5);
	$("#rollpic").hover(function(){
		$("#rollCaption").show(1000);
	},function(){
		$("#rollCaption").hide(1000);
	});
	$("#content").height($("#content_con").height());
	$("#search_con").css("opacity",0.5);
	var searchvalue;
	$("#search_con").focus(function(){
		searchvalue=$(this).val();
		$("#search_con").css("opacity",1.0);
		$(this).val("");
	});
	$("#search_con").blur(function(){
		if($(this).val()==""){
		$(this).css("opacity",0.5);
		$(this).val(searchvalue);
	}
	});
	$("#nav_con ul li ").hover(function(){
	 $(this).addClass("nav_hover");	
	},function(){
	  $(this).removeClass("nav_hover");	
	});
	$("#cheif_keyword div span ").hover(function(){
		$(this).removeClass("keyword");
		 $(this).addClass("keyword_hover");	
		},function(){
	      $(this).addClass("keyword");
		  $(this).removeClass("keyword_hover");	
    });
	$("#cheif_keyword div span ").click(function(){
		window.location.href=$(this).attr("href")	
    });
});
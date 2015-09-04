$(document).ready(function() {
		 $("#flip").click(function(e) {
			 if($(e.currentTarget).find('i').hasClass('fa-chevron-down')) {
				 $(e.currentTarget).find('i').removeClass('fa-chevron-down').addClass('fa-chevron-up');
			 }
			 else{
				 $(e.currentTarget).find('i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
			 }
			$("#panel").slideToggle("slow");
		});
		 $("#flip1").click(function(e) {
			 if($(e.currentTarget).find('i').hasClass('fa-chevron-down')) {
				 $(e.currentTarget).find('i').removeClass('fa-chevron-down').addClass('fa-chevron-up');
			 }
			 else{
				 $(e.currentTarget).find('i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
			 }
			$("#panel1").slideToggle("slow");
		});
		 $("#flip2").click(function(e) {
			 if($(e.currentTarget).find('i').hasClass('fa-chevron-down')) {
				 $(e.currentTarget).find('i').removeClass('fa-chevron-down').addClass('fa-chevron-up');
			 }
			 else{
				 $(e.currentTarget).find('i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
			 }
			$("#panel2").slideToggle("slow");
		});
		 $("#flip3").click(function(e) {
			 if($(e.currentTarget).find('i').hasClass('fa-chevron-down')) {
				 $(e.currentTarget).find('i').removeClass('fa-chevron-down').addClass('fa-chevron-up');
			 }
			 else{
				 $(e.currentTarget).find('i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
			 }
			$("#panel3").slideToggle("slow");
		});
		 $("#flip4").click(function(e) {
			 if($(e.currentTarget).find('i').hasClass('fa-chevron-down')) {
				 $(e.currentTarget).find('i').removeClass('fa-chevron-down').addClass('fa-chevron-up');
			 }
			 else{
				 $(e.currentTarget).find('i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
			 }
			$("#panel4").slideToggle("slow");
		});
		 $("#flip5").click(function(e) {
			 if($(e.currentTarget).find('i').hasClass('fa-chevron-down')) {
				 $(e.currentTarget).find('i').removeClass('fa-chevron-down').addClass('fa-chevron-up');
			 }
			 else{
				 $(e.currentTarget).find('i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
			 }
			$("#panel5").slideToggle("slow");
		});
		$('.datePicker').datepicker({
			changeMonth: true,
		     changeYear: true,
		     maxDate: new Date
		});
		
		$('.dobdatePicker').datepicker({
			 changeMonth: true,
		     changeYear: true,
		     maxDate: "-18Y", 
		     yearRange: '1950:1996',
		});
		$('.anniversarydatePicker').datepicker({
			 changeMonth: true,
		     maxDate: new Date
		});
		
		
		if( $('#panel').is(':visible') ) {
		   
		}
		else {
		    
		}

		
		
	});


function isNumAndDecimalKey(evt) {
   var charCode = (evt.which) ? evt.which : event.keyCode;
   if (charCode != 46 && charCode > 31  && (charCode < 48 || charCode > 57)) {
     return false;  
   }
   return true;
  }
  
  var specialKeys = new Array();
  specialKeys.push(8); //Backspace
  function isNumericKey(e) {
    var keyCode = e.which ? e.which : e.keyCode;
    var ret = ((keyCode >= 48 && keyCode <= 57) || keyCode == 9 || specialKeys.indexOf(keyCode) != -1);
    return ret;
  }
  
  function isAlphabetKey(e) {
	    var keyCode = e.which ? e.which : e.keyCode;
	    var ret = ((keyCode >= 65 && keyCode <= 90) || (keyCode >= 97 && keyCode <= 122) || keyCode == 9 || specialKeys.indexOf(keyCode) != -1);
	    return ret;
  }
  
  function isAlphabetWithSpace(e) {
	    var keyCode = e.which ? e.which : e.keyCode;
	    var ret = ((keyCode >= 65 && keyCode <= 90) || (keyCode >= 97 && keyCode <= 122) || keyCode == 32 || keyCode == 9 || specialKeys.indexOf(keyCode) != -1);
	    return ret;
 }
  
  function callAPI(url) {
      var ajaxOptions = {
            type: "POST",
            url: url,
            contentType: "application/json; charset=utf-8",
            dataType : "json",
          };
      var promise = $.ajax(ajaxOptions);
      return promise;
    };
    
  function showAllPanels() {
	  $("#flip").find('i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
	  $("#panel").show("slow");
	  
	  $("#flip1").find('i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
	  $("#panel1").show("slow");
	  
	  $("#flip2").find('i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
	  $("#panel2").show("slow");
	  
	  $("#flip3").find('i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
	  $("#panel3").show("slow");
	  
	  $("#flip4").find('i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
	  $("#panel4").show("slow");
	  
	  $("#flip5").find('i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
	  $("#panel5").show("slow");
}
  
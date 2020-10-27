<template>
  <!-- multistep form -->
<form id="msform">
    <!-- progressbar -->
    <ul id="progressbar">
        <li class="active"></li>
        <li></li>
        <li></li>
<li></li>
<li></li>
<li></li>
<li></li>
<li></li>
<li></li>
<li></li>
    </ul>
    <!-- fieldsets -->
    <fieldset>
        <h2 class="fs-title">Question 1</h2>
        <h3 class="fs-subtitle">What do you consider your main strengths to be?</h3>
    <!--<p class="help-block">List your strengths here.</p>-->
    <textarea class="form-control" name="CAT_Custom_1" id="CAT_Custom_1" rows="4" onkeydown="if(this.value.length>=4000)this.value=this.value.substring(0,3999);"></textarea>
        <input type="button" name="next" class="next action-button" value="Next" />
    </fieldset>
    <fieldset>
        <h2 class="fs-title">Question 2</h2>
        <h3 class="fs-subtitle">What do your colleagues consider your main strengths to be?</h3>
<textarea class="form-control" name="CAT_Custom_2" id="CAT_Custom_2" rows="4" onkeydown="if(this.value.length>=4000)this.value=this.value.substring(0,3999);"></textarea>
        <input type="button" name="previous" class="previous action-button" value="Previous" />
        <input type="button" name="next" class="next action-button" value="Next" />
    </fieldset>
    <fieldset>
        <h2 class="fs-title">Question 3</h2>
        <h3 class="fs-subtitle">What have been your main achievements?</h3>
<textarea class="form-control" name="CAT_Custom_3" id="CAT_Custom_3" rows="4" onkeydown="if(this.value.length>=4000)this.value=this.value.substring(0,3999);"></textarea>
        <input type="button" name="previous" class="previous action-button" value="Previous" />
        <input type="button" name="next" class="next action-button" value="Next" />
    </fieldset>
    <fieldset>
        <h2 class="fs-title">Question 4</h2>
        <h3 class="fs-subtitle">What do you consider your main weaknesses to be?</h3>
<textarea class="form-control" name="CAT_Custom_4" id="CAT_Custom_4" rows="4" onkeydown="if(this.value.length>=4000)this.value=this.value.substring(0,3999);"></textarea>
        <input type="button" name="previous" class="previous action-button" value="Previous" />
        <input type="button" name="next" class="next action-button" value="Next" />
</fieldset>
    <fieldset>
        <h2 class="fs-title">Question 5</h2>
        <h3 class="fs-subtitle">What do your colleagues consider your main weaknesses to be?</h3>
<textarea class="form-control" name="CAT_Custom_5" id="CAT_Custom_5" rows="4" onkeydown="if(this.value.length>=4000)this.value=this.value.substring(0,3999);"></textarea>
        <input type="button" name="previous" class="previous action-button" value="Previous" />
        <input type="button" name="next" class="next action-button" value="Next" />
    </fieldset>
    <fieldset>
        <h2 class="fs-title">Question 6</h2>
        <h3 class="fs-subtitle">In what areas would you like to improve your clinical skills?</h3>
<textarea class="form-control" name="CAT_Custom_6" id="CAT_Custom_6" rows="4" onkeydown="if(this.value.length>=4000)this.value=this.value.substring(0,3999);"></textarea>
        <input type="button" name="previous" class="previous action-button" value="Previous" />
        <input type="button" name="next" class="next action-button" value="Next" />
    </fieldset>
    <fieldset>
        <h2 class="fs-title">Question 7</h2>
        <h3 class="fs-subtitle">In what areas would you like to improve your non-clinical skills?</h3>
<textarea class="form-control" name="CAT_Custom_7" id="CAT_Custom_7" rows="4" onkeydown="if(this.value.length>=4000)this.value=this.value.substring(0,3999);"></textarea>
        <input type="button" name="previous" class="previous action-button" value="Previous" />
        <input type="button" name="next" class="next action-button" value="Next" />
    </fieldset>
    <fieldset>
        <h2 class="fs-title">Question 8</h2>
        <h3 class="fs-subtitle">Are there any specific areas of compliance training that you need to complete?</h3>
<textarea class="form-control" name="CAT_Custom_8" id="CAT_Custom_8" rows="4" onkeydown="if(this.value.length>=4000)this.value=this.value.substring(0,3999);"></textarea>
        <input type="button" name="previous" class="previous action-button" value="Previous" />
        <input type="button" name="next" class="next action-button" value="Next" />
    </fieldset>
    <fieldset>
        <h2 class="fs-title">Question 9</h2>
        <h3 class="fs-subtitle">What postgraduate qualifications do you hold?</h3>
<textarea class="form-control" name="CAT_Custom_9" id="CAT_Custom_9" rows="4" onkeydown="if(this.value.length>=4000)this.value=this.value.substring(0,3999);"></textarea>
        <input type="button" name="previous" class="previous action-button" value="Previous" />
        <input type="button" name="next" class="next action-button" value="Next" />
    </fieldset>
    <fieldset>
        <h2 class="fs-title">Question 10</h2>
        <h3 class="fs-subtitle">What postgraduate qualifications or training do you wish to obtain?</h3>
<textarea class="form-control" name="CAT_Custom_10" id="CAT_Custom_10" rows="4" onkeydown="if(this.value.length>=4000)this.value=this.value.substring(0,3999);"></textarea>
        <input type="button" name="previous" class="previous action-button" value="Previous" />
        <input type="submit" name="submit" class="submit action-button" value="Submit" />
    </fieldset>
</form>


</template>

<script>
var current_fs, next_fs, previous_fs; //fieldsets
var left, opacity, scale; //fieldset properties which we will animate
var animating; //flag to prevent quick multi-click glitches
$(document).ready(function(){
  $(".next").click(function(){
      if(animating) return false;
      animating = true;
      
      current_fs = $(this).parent();
      next_fs = $(this).parent().next();
      
      //activate next step on progressbar using the index of next_fs
      $("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");
      
      //show the next fieldset
      next_fs.show(); 
      //hide the current fieldset with style
      current_fs.animate({opacity: 0}, {
          step: function(now, mx) {
              //as the opacity of current_fs reduces to 0 - stored in "now"
              //1. scale current_fs down to 80%
              scale = 1 - (1 - now) * 0.2;
              //2. bring next_fs from the right(50%)
              left = (now * 50)+"%";
              //3. increase opacity of next_fs to 1 as it moves in
              opacity = 1 - now;
              current_fs.css({'transform': 'scale('+scale+')'});
              next_fs.css({'left': left, 'opacity': opacity});
          }, 
          duration: 500, 
          complete: function(){
              current_fs.hide();
              animating = false;
          }, 
          //this comes from the custom easing plugin
          easing: 'easeOutQuint'
      });
  });

  $(".previous").click(function(){
      if(animating) return false;
      animating = true;
      
      current_fs = $(this).parent();
      previous_fs = $(this).parent().prev();
      
      //de-activate current step on progressbar
      $("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");
      
      //show the previous fieldset
      previous_fs.show(); 
      //hide the current fieldset with style
      current_fs.animate({opacity: 0}, {
          step: function(now, mx) {
              //as the opacity of current_fs reduces to 0 - stored in "now"
              //1. scale previous_fs from 80% to 100%
              scale = 0.8 + (1 - now) * 0.2;
              //2. take current_fs to the right(50%) - from 0%
              left = ((1-now) * 50)+"%";
              //3. increase opacity of previous_fs to 1 as it moves in
              opacity = 1 - now;
              current_fs.css({'left': left});
              previous_fs.css({'transform': 'scale('+scale+')', 'opacity': opacity});
          }, 
          duration: 500, 
          complete: function(){
              current_fs.hide();
              animating = false;
          }, 
          //this comes from the custom easing plugin
          easing: 'easeOutQuint'
      });
  });

  $(".submit").click(function(){
      return false;
  })
})
export default {

}
</script>

<style>
@import url(https://fonts.googleapis.com/css?family=Open+Sans);
 * {
	 margin: 0;
	 padding: 0;
}
 html {
	 height: 100%;
	 background: #0e0e0e;
}
 body {
	 font-family: "Open Sans", arial, verdana;
}
 #msform {
	 width: 600px;
	 margin: 50px auto;
	 text-align: center;
	 position: relative;
}
 #msform fieldset {
	 background: white;
	 border: 0 none;
	 border-radius: 3px;
	 box-shadow: 0 0 15px 1px rgba(0,0,0,0.4);
	 padding: 20px 30px;
	 box-sizing: border-box;
	 width: 80%;
	 margin: 0 10%;
	 position: absolute;
}
 #msform fieldset:not(:first-of-type) {
	 display: none;
}
 #msform input, #msform textarea {
	 padding: 15px;
	 border: 1px solid #ccc;
	 border-radius: 3px;
	 margin-bottom: 10px;
	 width: 100%;
	 box-sizing: border-box;
	 font-family: montserrat;
	 color: #2C3E50;
	 font-size: 13px;
}
 #msform .action-button {
	 width: 100px;
	 background: #67d5bf;
	 font-weight: bold;
	 color: white;
	 border: 0 none;
	 border-radius: 1px;
	 cursor: pointer;
	 padding: 10px 5px;
	 margin: 10px 5px;
}
 #msform .action-button:hover, #msform .action-button:focus {
	 box-shadow: 0 0 0 2px white, 0 0 0 3px #67d5bf;
}
 .fs-title {
	 font-size: 16px;
	 text-transform: uppercase;
	 color: #63a2cb;
	 margin-bottom: 10px;
}
 .fs-subtitle {
	 font-weight: normal;
	 font-size: 14px;
	 color: #666;
	 margin-bottom: 20px;
}
 #progressbar {
	 margin-bottom: 30px;
	 overflow: hidden;
	 counter-reset: step;
}
 #progressbar li {
	 list-style-type: none;
	 color: white;
	 text-transform: uppercase;
	 font-size: 9px;
	 width: 10%;
	 float: left;
	 position: relative;
}
 #progressbar li:before {
	 content: counter(step);
	 counter-increment: step;
	 width: 20px;
	 line-height: 20px;
	 display: block;
	 font-size: 10px;
	 color: #333;
	 background: white;
	 border-radius: 3px;
	 margin: 0 auto 5px auto;
}
 #progressbar li:after {
	 content: '';
	 width: 100%;
	 height: 2px;
	 background: white;
	 position: absolute;
	 left: -50%;
	 top: 9px;
	 z-index: -1;
}
 #progressbar li:first-child:after {
	 content: none;
}
 #progressbar li.active:before, #progressbar li.active:after {
	 background: #67d5bf;
	 color: white;
}
 .help-block {
	 font-size: .8em;
	 color: #7c7c7c;
	 text-align: left;
	 margin-bottom: .5em;
}
</style>
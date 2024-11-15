console.log("Dept Module........");

const deptService = (function (){

   // http://localhost/scott/dept/new  새로운 부서 추가
   // JSON.stringify(dept)    js객체-> JSON 
   function add(dept, callback, error){
      console.log("> deptService.add()........");
      
      $.ajax({
        type:'post',
        url:'/scott/dept/new',
        data:JSON.stringify(dept),
        contentType : "application/json; charset=utf-8",
        cache:false,
        beforeSend:function (xhr){
          console.log("add.beforeSend ...............");
        },
        success:function (result, status, xhr){          
          if( callback ){
              callback( result );
          } // if
        }, 
        error: function (xhr, status, er){           
           if( error ){
		          error( er );
		  } // if  
        } 
      }) 
      .fail(function() {
       alert( "ajax 부서 추가 실패!!!" );
      });
      
   } // add
   
   <!-- 
   // http://localhost/scott/dept/delete?deptno=50
   // GET/POST/DELETE/PUT/ 등등 + http://localhost/scott/dept/50
   // JSON.stringify(dept)    js객체-> JSON 
   function remove(deptno, callback, error){
      console.log("> deptService.remove()........");
      
      $.ajax({
        type:'get',
        url:'/scott/dept/delete',
        data:`deptno=${deptno}` ,
        cache:false,
        beforeSend:function (xhr){
          console.log("remove.beforeSend ...............");
        },
        success:function (result, status, xhr){          
          if( callback ){
              callback( result );
          } // if
        }, 
        error: function (xhr, status, er){           
           if( error ){
   		          error( er );
   		  } // if  
        } 
      }) 
      .fail(function() {
       alert( "ajax 부서 추가 실패!!!" );
      });
      
   } // remove
     -->
     
   // RESTful 활용
   // DELETE + http://localhost/scott/dept/50
   // JSON.stringify(dept)    js객체-> JSON 
   function remove(deptno, callback, error){
      console.log("> deptService.remove()........");
      
      $.ajax({
        type:'delete',
        url:`/scott/dept/${deptno}`,
        cache:false,
        beforeSend:function (xhr){
          console.log("remove.beforeSend ...............");
        },
        success:function (result, status, xhr){          
          if( callback ){
              callback( result );
          } // if
        }, 
        error: function (xhr, status, er){           
           if( error ){
		          error( er );
		  } // if  
        } 
      }) 
      .fail(function() {
       alert( "ajax 부서 추가 실패!!!" );
      });
      
   } // remove
    
    
    
    // 함수들을 객체로 내보냄
    return {
        add: add,
        remove: remove
    };

})();
 
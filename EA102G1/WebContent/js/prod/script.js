// some scripts
 var currentUser=""; 
 var path="";
 var webSocket;
 var notice;
 var content
	this.send = function (message, callback) {
	    this.waitForConnection(function () {
	    	webSocket.send(message);
	        if (typeof callback !== 'undefined') {
	          callback();
	        }
	    }, 1000);
	};

	this.waitForConnection = function (callback, interval) {
	    if (webSocket.readyState === 1) {
	        callback();
	    } else {
	        var that = this;
	        // optional: implement backoff for interval here
	        setTimeout(function () {
	            that.waitForConnection(callback, interval);
	        }, interval);
	    }
	};

// jquery ready start
$(document).ready(function() {
	// jQuery code
	//WS開始
	  currentUser = document.getElementById("currentUser").textContent;
	  path = document.getElementById("path").textContent;
	  
	  notice = document.getElementById("notice");
	  content = document.getElementById("content");
	if (document.querySelectorAll('#content a').length > 0) {
		notice.classList.add("text-danger");
		$('#content').show();
	} else {
		$('#content').hide();
	}
	notice.addEventListener("click", function(){
		this.classList.remove("text-danger");
	})
	
	var MyPoint = "/OrderNoticeSender/"+currentUser;
	var host = window.location.host; //localhost:8081
	var myPath = window.location.pathname;
	var webCtx = myPath.substring(0, myPath.indexOf('/', 1)); // WebSocketChatWeb
	var endPointURL = "ws://" + window.location.host+webCtx + MyPoint; //new WebSocket(endPointURL) 向server開啟連結;
	var reloaded = false;
	
	var connect = function () {
		webSocket = new WebSocket(endPointURL);
		webSocket.open = function(e) {
			console.log("Connect Success");
		}
		webSocket.onmessage = function(e) {
			var noticeContent="";
			var text="";
			var url="";
			var jsonObj = JSON.parse(e.data);
			if ("new" === jsonObj.type) {
				noticeContent+="你有一筆新訂單:";
				url=path+"/order/order.do?action=getDifferentStatusOrders_seller&order_status=1";
			} else if ("notice" === jsonObj.type) {
				var order_status = jsonObj.order_status;
				switch(order_status) {
					case "0":
						text="訂單已取消:";
						url=path+"/order/order.do?action=getDifferentStatusOrders_seller&order_status=0";
						break;
					case "2":
						text="訂單已出貨:";
						url=path+"/order/order.do?action=getDifferentStatusOrders_buyer&order_status=2";
						break;
					case "3":
						text="訂單已完成:";
						url=path+"/order/order.do?action=getDifferentStatusOrders_seller&order_status=3";
						break;
				}
			}
			noticeContent+=text;
			var anchorText = noticeContent+jsonObj.order_no;
			content.innerHTML+=`<a class="dropdown-item" href="${url}">${anchorText}</a>`;
			if (document.querySelectorAll('#content a').length > 0) {
				notice.classList.add("text-danger");
				$('#content').show();
			} else {
				$('#content').hide();
			}
		}
		webSocket.onclose = function(e) {
			console.log("Disconnected");
		}
	}; //endOfConnection
	
	if(currentUser.length > 0){
		connect();
	}
	
	function disconnect() {
		webSocket.close();
	}
	//WS結束

	
	//////////////////////// Prevent closing from click inside dropdown
    $(document).on('click', '.dropdown-menu', function (e) {
      e.stopPropagation();
    });

	//////////////////////// Bootstrap tooltip
	if($('[data-toggle="tooltip"]').length>0) {  // check if element exists
		$('[data-toggle="tooltip"]').tooltip()
	} // end if

	if($('[data-toggle="popover"]').length>0) {  // check if element exists
		$('[data-toggle="popover"]').popover();
	} // end if

    
}); 
// jquery end


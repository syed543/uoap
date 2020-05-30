<!doctype html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, user-scalable=no">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
                <meta name="google-site-verification" content="iLrorD2hs1bNEdFPmBF9FMQKFHlrJJg9YOfwfCFeiq4" />
		<meta name="title" content="Open Access Journals |Medical Case Reports - Unicon" />
		<meta name="description" content="Provides access to quality Case Report; Clinical; Medicine; Open Access; Peer-Reviewed; and International Journals." />
		<title>Unicon Open Access Publishers</title>
		
		<link href="../../journal/assets/js/vendors/angular-material/angular-material.css" rel="stylesheet" />
		<link href="../../journal/assets/js/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
		<link href="../../journal/assets/css/styles.css" rel="stylesheet" />
	</head>
	
	<body layout="column" flex class="uoap">
		<div class="uoap-header">
		    <!-- Static navbar -->
		    <nav class="navbar navbar-default navbar-static-top">
		        <div class="container">
		            <div class="navbar-header">
		                <a class="navbar-brand" href="#">Unicon Open Access</a>
		            </div>
		        </div>
		    </nav>
		</div>
		
		<div class="invoice-details invoice-show">
			<div class="panel panel-primary">
			  <div class="panel-heading">
			    <h3 class="panel-title">Invoice Details</h3>
			  </div>
			  <div class="panel-body">
			    
			  </div>
			  <!-- List group -->
			  <ul class="list-group">
			    <li class="list-group-item">
				    <div class="row">
					  <div class="col-xs-12 col-sm-6 col-md-6">Date: </div>
					  <div class="col-xs-6 col-md-6">${userInvoice.creationDate}</div>
					</div>
			    </li>
			    <li class="list-group-item">
			    	<div class="row">
					  <div class="col-xs-12 col-sm-6 col-md-6">Article Number: </div>
					  <div class="col-xs-6 col-md-6">${userInvoice.articleNumber}</div>
					</div>
				</li>
			    <li class="list-group-item">
			    	<div class="row">
					  <div class="col-xs-12 col-sm-6 col-md-6">Article Name: </div>
					  <div class="col-xs-6 col-md-6">${userInvoice.articleName}</div>
					</div>
				</li>
				<li class="list-group-item">
			    	<div class="row">
					  <div class="col-xs-12 col-sm-6 col-md-6">Journal Name: </div>
					  <div class="col-xs-6 col-md-6">${userInvoice.journalName}</div>
					</div>
				</li>
				<li class="list-group-item">
			    	<div class="row">
					  <div class="col-xs-12 col-sm-6 col-md-6">Corresponding Author Name: </div>
					  <div class="col-xs-6 col-md-6">${userInvoice.authorName}</div>
					</div>
				</li>
				<li class="list-group-item">
			    	<div class="row">
					  <div class="col-xs-12 col-sm-6 col-md-6">Corresponding Author Email ID: </div>
					  <div class="col-xs-6 col-md-6">${userInvoice.authorEmailId}</div>
					</div>
				</li>
				<li class="list-group-item">
			    	<div class="row">
					  <div class="col-xs-12 col-sm-6 col-md-6">Amount: </div>
					  <div class="col-xs-6 col-md-6">${userInvoice.currencyCode} ${userInvoice.amount}</div>
					</div>
				</li>
				<li class="list-group-item">
					<div><p>Pay using: </p></div>
					<div id="paypal-button-container"></div>
				</li>
			  </ul>
			</div>
		</div>
		<div class="invoice-details invoice-hide">
			<div class="panel">
			  <div class="panel-body">
			    Your payment is successfull. Thank you!
			  </div>
			</div>
		</div>
	</body>
	<script src="https://www.paypal.com/sdk/js?client-id=AXjLvGI9ZILQElZk7jeSbA5dOEYuJdUIxXkGmx23iHDrrrpPeZmDvlko1p2ZzTAThJ9QHBU07uC_OBBv&currency=${userInvoice.currencyCode}"></script>
	<script>
		var _invoiceNumber = '${userInvoice.invoiceNumber}';
		paypal.Buttons({
			style: {
			    layout:  'vertical',
			    color:   'blue',
			    shape:   'rect',
			    label:   'paypal'
			  },
		    createOrder: function(data, actions) {
		      // This function sets up the details of the transaction, including the amount and line item details.
		      return actions.order.create({
		        purchase_units: [{
		          amount: {
		            value: '${userInvoice.amount}'
		          }
		        }]
		      });
		    },
		    onApprove: function(data, actions) {
		      // This function captures the funds from the transaction.
		      return actions.order.capture().then(function(details) {
		        // This function shows a transaction success message to your buyer.
		        
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.open("POST", "/journal/router/updatePayment");
			xmlhttp.setRequestHeader("Content-Type", "application/json");
			xmlhttp.send(JSON.stringify({invoiceNumber: _invoiceNumber, paymentStatus:details.status, transactionId: details.id}));
				if(details.status.toLowerCase() == "completed" || details.status.toLowerCase() == "onhold") {
					var _invoiceRepTemplateEle = document.getElementsByClassName("invoice-hide")[0];
					_invoiceRepTemplateEle.classList.remove("invoice-hide");

					var _invoiceTemplateEle = document.getElementsByClassName("invoice-show")[0];
					_invoiceTemplateEle.parentNode.removeChild(_invoiceTemplateEle);
				}		
		      });
		    }
		  }).render('#paypal-button-container');
	    // This function displays Smart Payment Buttons on your web page.
	  </script>
</html>
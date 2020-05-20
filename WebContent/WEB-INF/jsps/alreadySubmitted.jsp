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
		
		<link href="../../WebContent/assets/js/vendors/angular-material/angular-material.css" rel="stylesheet" />
		<link href="../../WebContent/assets/js/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
		<link href="../../WebContent/assets/css/styles.css" rel="stylesheet" />
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
		
		<div class="invoice-details">
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
					  <div class="col-xs-12 col-sm-6 col-md-6">Your email: </div>
					  <div class="col-xs-6 col-md-6">syed.azher@gmail.com</div>
					</div>
			    </li>
			    <li class="list-group-item">
			    	<div class="row">
					  <div class="col-xs-12 col-sm-6 col-md-6">Your email: </div>
					  <div class="col-xs-6 col-md-6">syed.azher@gmail.com</div>
					</div>
				</li>
			    <li class="list-group-item">
			    	<div class="row">
					  <div class="col-xs-12 col-sm-6 col-md-6">Your email: </div>
					  <div class="col-xs-6 col-md-6">syed.azher@gmail.com</div>
					</div>
				</li>
				<li class="list-group-item">
			    	<div class="row">
					  <div class="col-xs-12 col-sm-6 col-md-6">Your email: </div>
					  <div class="col-xs-6 col-md-6">syed.azher@gmail.com</div>
					</div>
				</li>
				<li class="list-group-item">
			    	<div class="row">
					  <div class="col-xs-12 col-sm-6 col-md-6">Your email: </div>
					  <div class="col-xs-6 col-md-6">syed.azher@gmail.com</div>
					</div>
				</li>
				<li class="list-group-item">
			    	<div class="row">
					  <div class="col-xs-12 col-sm-6 col-md-6">Your email: </div>
					  <div class="col-xs-6 col-md-6">syed.azher@gmail.com</div>
					</div>
				</li>
				<li class="list-group-item">
			    	<div class="row">
					  <div class="col-xs-12 col-sm-6 col-md-6">Your email: </div>
					  <div class="col-xs-6 col-md-6">syed.azher@gmail.com</div>
					</div>
				</li>
				<li class="list-group-item">
					<div id="paypal-button-container"></div>
				</li>
			  </ul>
			</div>
		</div>
	</body>
	<script src="https://www.paypal.com/sdk/js?client-id=sb"></script>
	<script>
		paypal.Buttons({
		    createOrder: function(data, actions) {
		      // This function sets up the details of the transaction, including the amount and line item details.
		      return actions.order.create({
		        purchase_units: [{
		          amount: {
		            value: '0.01'
		          }
		        }]
		      });
		    },
		    onApprove: function(data, actions) {
		      // This function captures the funds from the transaction.
		      return actions.order.capture().then(function(details) {
		        // This function shows a transaction success message to your buyer.
		        alert('Transaction completed by ' + details.payer.name.given_name);
		      });
		    }
		  }).render('#paypal-button-container');
	    // This function displays Smart Payment Buttons on your web page.
	  </script>
</html>
<!doctype html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
                <meta name="google-site-verification" content="iLrorD2hs1bNEdFPmBF9FMQKFHlrJJg9YOfwfCFeiq4" />
		<title>Unicon Open Access Publishers</title>

		<style>
			/*
              Allow angular.js to be loaded in body, hiding cloaked elements until
              templates compile.  The !important is important given that there may be
              other selectors that are more specific or come later and might alter display.
             */
			[ng\:cloak], [ng-cloak], .ng-cloak {
				display: none !important;
			}
		</style>

		<link href="assets/js/vendors/angular-material/angular-material.css" rel="stylesheet" />
		<link href="assets/js/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
		<link href="assets/js/vendors/jk-carousel/jk-carousel.css" rel="stylesheet" />
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		<link href="assets/js/vendors/angular-material-data-table/dist/md-data-table.css" rel="stylesheet" />
		<link href="assets/css/styles.css" rel="stylesheet" />

<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-149502379-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-149502379-1');
</script>

	</head>
	<body ng-cloak layout="column" flex class="uoap">
		<header ng-include="'assets/includes/header.html'"></header>
		<!-- Header End -->
		<!-- Content Start -->
		<div ng-include="'assets/includes/content.html'"></div>
		<!-- Content End -->
		<!-- Footer Start -->
		<footer ng-include="'assets/includes/footer.html'"></footer>

		<script type="text/javascript" data-main="assets/js/bootstrapper.js" src="assets/js/vendors/requirejs/require.js"></script>
		<!--<script src="assets/js/vendors/jk-carousel/jk-carousel.js"></script>-->
	</body>
</html>

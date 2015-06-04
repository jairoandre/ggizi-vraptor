<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="ggizi" ng-controller="AppCtrl">
<head>
    <title ng-bind="pageTitle"></title>

    <!-- social media tags -->
    <meta charset="UTF-8">
    <meta name="twitter:card" content="summary">
    <meta name="twitter:site" content="@jairoandre">
    <meta name="twitter:title" content="huelol">
    <meta name="twitter:description"
          content="ggizi! Dados e estatísticas sobre LoL de um jeito descomplicado.">
    <meta name="twitter:creator" content="@jairoandre">
    <meta name="twitter:image:src"
          content="https://a248.e.akamai.net/assets.github.com/images/modules/logos_page/Octocat.png?1366128846">
    <meta property="og:title" content="ggizi"/>
    <meta property="og:type" content="website"/>
    <meta property="og:url" content="http://ggizi.com.br"/>
    <meta property="og:image"
          content="https://a248.e.akamai.net/assets.github.com/images/modules/logos_page/Octocat.png?1366128846"/>
    <meta property="og:description"
          content="ggizi! Dados e estatísticas sobre LoL de um jeito descomplicado.">

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- font awesome from BootstrapCDN -->
    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">

    <!-- compiled CSS -->
    <link rel="stylesheet" type="text/css" href="assets/ggizi-0.0.0.css"/>


    <!-- compiled JavaScript -->

    <script type="text/javascript" src="assets/ggizi-0.0.0.js"></script>


    <!-- it's stupid to have to load it here, but this is for the +1 button -->
    <script type="text/javascript" src="https://apis.google.com/js/plusone.js">
        {
            "parsetags"
        :
            "explicit"
        }
    </script>
</head>
<body>

<header class="navbar navbar-inverse header" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" ng-init="menuCollapsed = true"
                    ng-click="menuCollapsed = ! menuCollapsed">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <div class="navbar-brand">
                ggizi
                <small>
                    <a href="http://github.com/ngbp/ngbp/blob/v0.0.0-release/CHANGELOG.md">
                        v0.0.0
                    </a>
                </small>
            </div>
        </div>
        <div class="collapse navbar-collapse" collapse="menuCollapsed">
            <ul class="nav navbar-nav">
                <li ui-sref-active="active">
                    <a href ui-sref="home" ng-click="menuCollapsed = true ">
                        <i class="fa fa-home"></i>
                        Início
                    </a>
                </li>
                <li ui-sref-active="active" ng-click="menuCollapsed = true ">
                    <a href ui-sref="summoner">
                        <i class="fa fa-user"></i>
                        Invocador
                    </a>
                </li>
                <li ui-sref-active="active" ng-click="menuCollapsed = true">
                    <a href ui-sref="champion">
                        <i class="fa fa-book"></i>
                        Campeões
                    </a>
                </li>
            </ul>
        </div>
    </div>
</header>

<div class="container">

    <div ui-view="main" class="ggizi-content animate"></div>

</div>

<footer class="footer">

    <div class="container">

        <div class="info">
            <i class="fa fa-smile-o"></i>&nbsp;Desenvolvido por Kellthazar - 2015 - API version: ${version}
        </div>

    </div>
</footer>
</body>
</html>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Starter Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="../../dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="../../assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="starter-template.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href="#">ICD Assigner</a>
        </div>
      </div>
    </nav>

    <div class="container">

    <div class="starter-template">
    <h1>ICD Assigner</h1>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <div>
        <form action="service.php" method="post">
            <select name="behaviour">
                <option value="findICD">Assigne ICD codes</option>
                <option value="evaluateICDList">Evaluate ICD codes</option>
            </select>
            ICD codes to evaluate: <input type="text" name="icdList" placeholder="Separate by ;"></br>
            Text to assign: <input type="text" name="history" placeholder="Enter history here"></br>
            Algorithm to use : 
            <select name="algorithm">
                <option value="Basic">Basic</option>
                <option value="Sentence">Sentence detection</option>
            </select>
            <input type="submit" name="formSubmit" value="Submit" >
        </form>

    </div>
    <div class="text-left"><br/>
    <?php
        if (isset($_POST["history"])&&isset($_POST["algorithm"])){
            if($_POST["behaviour"]=='findICD'){
                $url = 'http://129.194.201.31:8686/' . $_POST["behaviour"] .  $_POST["algorithm"];
            }
            else if($_POST["behaviour"]=='evaluateICDList'){
                $url = 'http://129.194.201.31:8686/' . $_POST["behaviour"];
            }

            $data = array(  'history'=>$_POST["history"],
                            'icdlist'=>$_POST["icdList"]);


            $options = array(
                       'http' => array(
                       'header'  => "Content-type: application/x-www-form-urlencoded\r\n",
                       'method'  => 'POST',
                       'content' => json_encode($data),
                       )
                       );
            $context  = stream_context_create($options);
            $result = file_get_contents($url, false, $context);

            if($result==''){
                echo "No ICD code match for this input";
            }
            else{
                echo $result;
            }

            };

    ?>
    </div>

    </div>

    </div><!-- /.container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../../dist/js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>


<html>
<body>


<?php
	$url = 'http://129.194.201.189:8686/findICD';
	$data = $_POST["history"];
	

	$options = array('http' => array('header'  => "Content-type: application/x-www-form-urlencoded\r\n",'method'  => 'POST','content' => $data,),);
	$context  = stream_context_create($options);
	$result = file_get_contents($url, false, $context);


	var_dump($result);

?>

</body>
</html>

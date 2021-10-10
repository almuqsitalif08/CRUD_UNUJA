<?php
$server_name = "localhost";
$username = "root";
$password = "";

$database = "database";
$conn = mysqli_connect($server_name, $username, $password, $database) or die("Connection failed!");

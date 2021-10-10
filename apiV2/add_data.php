<?php
require_once('connect.php');

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nama = $_POST['nama'];
    $jenis_kelamin = $_POST['jenis_kelamin'];
    $alamat = $_POST['alamat'];
    
    $process = "INSERT INTO crud (nama, jenis_kelamin, alamat) VALUES ('$nama', '$jenis_kelamin', '$alamat')";

    if (mysqli_query($conn, $process)) {
        $response['success'] = 1;
        $response['message'] = "Data berhasil ditambahkan";
        echo json_encode($response);
    } else {
        $response['success'] = 0;
        $response['message'] = "Data gagal ditambahkan";
        echo json_encode($response);
    }
}  else {
    $request = $_SERVER['REQUEST_METHOD'];
    $response['success'] = 0;
    $response['message'] = "Request $request tidak tersedia";
    echo json_encode($response);
}
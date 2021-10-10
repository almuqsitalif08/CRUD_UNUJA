<?php
require_once('connect.php');

if($_SERVER['REQUEST_METHOD'] == 'GET'){
    $id = $_GET['id'];
    $process = mysqli_query($conn, "SELECT * FROM crud WHERE id = $id");

    if(mysqli_num_rows($process) > 0) {
        $row = mysqli_fetch_array($process);
        $response['success'] = 1;
        $result['nama'] = $row['nama'];
        $result['jenis_kelamin'] = $row['jenis_kelamin'];
        $result['alamat'] = $row['alamat'];
        $response['data'] = $result;
        echo json_encode($response);
    } else {
        $response['success'] = 0;
        $response['message'] = "Tidak ada data";
        echo json_encode($response);
    }
} else {
    $request = $_SERVER['REQUEST_METHOD'];
    $response['success'] = 0;
    $request['message'] = "Request $request tidak tersedia";
    echo json_encode($response);
}
<?php
require_once('connect.php');

if($_SERVER['REQUEST_METHOD'] == 'GET') {
    $process = mysqli_query($conn, "SELECT * FROM crud");

    if(mysqli_num_rows($process) > 0) {
        $response['success'] = 1;
        $response['data'] = array();
        while ($row = mysqli_fetch_array($process)) {
            $result['id'] = $row['id'];
            $result['nama'] = $row['nama'];
            $result['jenis_kelamin'] = $row['jenis_kelamin'];
            $result['alamat'] = $row['alamat'];
            array_push($response['data'], $result);
        }
        echo json_encode($response);
    } else {
        $response['success'] = 0;
        $response['message'] = "Tidak ada data";
        echo json_encode($response);
    }
} else {
    $request = $_SERVER['REQUEST_METHOD'];
    $response['success'] = 0;
    $response['message'] = "Request $request tidak tersedia";
    echo json_encode($response);
}
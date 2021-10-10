<?php
require_once('connect.php');

if($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST['id'];

    $process = "DELETE FROM crud WHERE id = $id";
    if(mysqli_query($conn, $process)){
        $response['success'] = 1;
        $response['message'] = "Data berhasil dihapus";
        echo json_encode($response);
    } else {
        $response['success'] = 0;
        $response['message'] = "Data gagal dihapus";
        echo json_encode($response);
    }
} else {
    $request = $_SERVER['REQUEST_METHOD'];
    $response['success'] = 0;
    $response['message'] = "Request $request tidak tersedia";
    echo json_encode($response);
}
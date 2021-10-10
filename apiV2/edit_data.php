<?php
require_once('connect.php');

if ($_SERVER['REQUEST_METHOD'] == 'POST'){
    $id = $_POST['id'];
    $nama = $_POST['nama'];
    $jenis_kelamin = $_POST['jenis_kelamin'];
    $alamat = $_POST['alamat'];

    $process = "UPDATE crud SET nama = '$nama', jenis_kelamin = '$jenis_kelamin', alamat = '$alamat' WHERE id = $id";

    if(mysqli_query($conn, $process)){
        $response['success'] = 1;
        $response['message'] = "Data berhasil diubah";
        echo json_encode($response);
    } else {
        $response['success'] = 0;
        $response['message'] = "Data gagal diubah";
        echo json_encode($response);
    }
} else {
    $request = $_SERVER['REQUEST_METHOD'];  
    $response['success'] = 0;
    $response['message'] = "Request $request tidak tersedia";
    echo json_encode($response);
}
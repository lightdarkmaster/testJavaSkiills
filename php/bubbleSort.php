<?php

function bubbleSort($arr){
    $n = count($arr);
    do {
        $swapped = false;
        for ($i = 0; $i < $n - 1; $i++) {
            if ($arr[$i] > $arr[$i + 1]) {
                list($arr[$i], $arr[$i + 1]) = array($arr[$i + 1], $arr[$i]);
                $swapped = true;
            }
        }
    } while ($swapped);
    return $arr;
}

$arr = array(64, 34, 25, 12, 22, 11, 90);
$arr = bubbleSort($arr);
print_r($arr);

?>

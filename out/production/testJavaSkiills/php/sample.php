<?php
echo "Fibonacci Sequence ";

function fib($n){
    $num1 = 0;
    $num2 = 1;
    $nextNum;
    
    for($i = 0; $i<=$n; $i++){
        echo $num1 . ", ";
        $nextNum = $num1 + $num2;
        $num1 = $num2;
        $num2 = $nextNum;
    }
}

function getAreaSquare($side){
    return $res = $side * $side;
    echo $res;
}

function areaOfTriangle($base, $height){
    $res = $base * $height;
    return $area = $res/2;
}

function areaOfTrapezoid($base1, $base2, $height){
    $baseSum = $base1 + $base2;
    $initArea = $baseSum/2;
    return $area = $initArea * $height;
}

function areaOfCircle($radius){
    $pi = 3.14;
    $radiusInitArea = $radius * $radius;
    return $area = $pi * $radiusInitArea;
}


echo fib(9);

echo "\nArea of the square is : " . getAreaSquare(5) . " square meters";

echo "\nArea of the Triangle is : " . areaOfTriangle(10,7) . " square meters";

echo "\nArea of the Trapezoid is : " . areaOfTrapezoid(10,15,9) . " square meters";

echo "\nArea of the Circle is : " . areaOfCircle(10) . " square meters";

?>
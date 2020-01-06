<?php
session_start();
$servername = "localhost";
$username = "root";
$password ="";
$conn = mysqli_connect($servername, $username, $password,"mouni");
$cat=$_POST['category'];
$que=mysqli_query($conn,"SELECT * FROM product WHERE `Product Id`='".$_POST['productid']."'") or die(mysql_error());
$res=mysqli_fetch_array($que);
if($res)
{
		header('Location:catf.php');
}
else{
$_SESSION['name']=$_POST['name'];	
$_SESSION['emailid']=$_POST['emailid'];
$_SESSION['category']=$_POST['category'];
$_SESSION['mobileno']=$_POST['mobileno'];
$_SESSION['productid']=$_POST['productid'];
$_SESSION['productname']=$_POST['productname'];
$_SESSION['price']=$_POST['price'];
$sel="SELECT * FROM register WHERE `Email Id`='".$_POST['emailid'];
$sel1=mysqli_query($sel);
$sel2=mysqli_fetch_array($sel1);
$name=$sel2['Name'];
$img = $_FILES["productimage"] ["name"];
$type = $_FILES["productimage"] ["type"];
$size = $_FILES["productimage"] ["size"];
$temp = $_FILES["productimage"] ["tmp_name"];
$error = $_FILES["productimage"] ["error"];
if($size>10000000)
{
			header('location:size.php');
}
if((($_POST['emailid']==$_SESSION['usrname'])||($_POST['emailid']==$_SESSION['mail']))&&($_POST['Name']==$name))
{
$query1 = "INSERT INTO ` Date`,`Product Image`,`Product Description`) product`(`Name`,`Email Id`,`Category`,`Mobile No`,`Product Id`,`Product Name`,`Min Bid Price`,`Start Date`,`End 
VALUES ('".$_POST['name']."', '".$_POST['emailid']."', '".$_POST['category']."', '".$_POST['mobileno']."', '".$_POST['productid']."','".$_POST['productname']."','".$_POST['minbidprice']."','".$_POST['startdate']."','".$_POST['enddate']."','$img','".$_POST['productdescription']."')";
if($_POST['category']=='Electronic gadgets')
{	
$query = "INSERT INTO `electronic gadgets`(`Name`,`Email Id`,`Category`,`Mobile No`,`Product Id`,`Product Name`,`Min Bid Price`,`Start Date`,`End Date`,`Product Image`,`Product Description`) 
VALUES ('".$_POST['name']."', '".$_POST['emailid']."', '".$_POST['category']."', '".$_POST['mobileno']."', '".$_POST['productid']."','".$_POST['productname']."','".$_POST['minbidprice']."','".$_POST['startdate']."','".$_POST['enddate']."','$img','".$_POST['productdescription']."')";
}

 if($_POST['category']=='Furniture')
{
$query = "INSERT INTO `furniture`(`Name`,`Email Id`,`Category`,`Mobile No`,`Product Id`,`Product Name`,`Min Bid Price`,`Start Date`,`End Date`,`Product Image`,`Product Description`) 
VALUES ('".$_POST['name']."', '".$_POST['emailid']."', '".$_POST['category']."', '".$_POST['mobileno']."', '".$_POST['productid']."','".$_POST['productname']."','".$_POST['minbidprice']."','".$_POST['startdate']."','".$_POST['enddate']."','$img','".$_POST['productdescription']."')";
}

if($_POST['category']=='Automobiles')
{
$query = "INSERT INTO `automobiles`(`Name`,`Email Id`,`Category`,`Mobile No`,`Product Id`,`Product Name`,`Min Bid Price`,`Start Date`,`End Date`,`Product Image`,`Product Description`) 
VALUES ('".$_POST['name']."', '".$_POST['emailid']."', '".$_POST['category']."', '".$_POST['mobileno']."', '".$_POST['productid']."','".$_POST['productname']."','".$_POST['minbidprice']."','".$_POST['startdate']."','".$_POST['enddate']."','$img','".$_POST['productdescription']."')";
}
$result=@mysqli_query($conn,$query) ;
$result1=@mysqli_query($conn,$query1) ;
$q="SELECT * FROM product  WHERE `Product Id`='".$_POST['productid']."' ";
$res=mysqli_query($conn,$q) ;
$r=mysqli_fetch_array($res);
$flag=$r['Status'];
			//header('location:seller.php');
}
//else{
//			header('location:mat.php');
//}


}
?>

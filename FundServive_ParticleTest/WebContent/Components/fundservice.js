$(document).ready(function()
{
if ($("#alertSuccess").text().trim() == "")
 {
 $("#alertSuccess").hide();
 }
 $("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
// Clear alerts---------------------
 $("#alertSuccess").text("");
 $("#alertSuccess").hide();
 $("#alertError").text("");
 $("#alertError").hide();
// Form validation-------------------
var status = validateItemForm();
if (status != true)
 {
 $("#alertError").text(status);
 $("#alertError").show();
 return;
 }
//If valid------------------------
var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
 $.ajax(
 {
 url : "FundServiceAPI",
 type : type,
 data : $("#formFund").serialize(),
 dataType : "text",
 complete : function(response, status)
 {
 onItemSaveComplete(response.responseText, status);
 }
 });
});
// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
		{
		$("#hidItemIDSave").val($(this).data("itemid"));
		 $("#fTitle").val($(this).closest("tr").find('td:eq(0)').text());
		 $("#fDesc").val($(this).closest("tr").find('td:eq(1)').text());
		 $("#fPrice").val($(this).closest("tr").find('td:eq(2)').text());
		 $("#fuName").val($(this).closest("tr").find('td:eq(3)').text());
		 $("#date").val($(this).closest("tr").find('td:eq(4)').text());
		});

//Delete

$(document).on("click", ".btnRemove", function(event)
		{
		 $.ajax(
		 {
		 url : "FundServiceAPI",
		 type : "DELETE",
		 data : "fId=" + $(this).data("itemid"),
		 dataType : "text",
		 complete : function(response, status)
		 {
		 onItemDeleteComplete(response.responseText, status);
		 }
		 });
		});
// CLIENT-MODEL================================================================
function validateItemForm()
{
// Title
if ($("#fTitle").val().trim() == "")
 {
 return "Insert Title.";
 }
// Description
if ($("#fDesc").val().trim() == "")
 {
 return "Insert Description.";
 }

//PRICE-------------------------------
if ($("#fPrice").val().trim() == "")
 {
 return "Insert Fund Price.";
 }
// is numerical value
var tmpPrice = $("#fPrice").val().trim();
if (!$.isNumeric(tmpPrice))
 {
 return "Insert a numerical value for Item Price.";
 }
// convert to decimal price
 $("#fPrice").val(parseFloat(tmpPrice).toFixed(2));
// Funder name------------------------
if ($("#fuName").val().trim() == "")
 {
 return "Insert Funder name.";
 }
if ($("#date").val().trim() == "")
 {
 return "Insert Date.";
 }
return true;
}

function onItemSaveComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully saved.");
 $("#alertSuccess").show();
 $("#divItemsGrid").html(resultSet.data);
 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while saving.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while saving..");
 $("#alertError").show();
 } 

$("#hidItemIDSave").val("");
$("#formFund")[0].reset();
}

function onItemDeleteComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully deleted.");
 $("#alertSuccess").show();
 $("#divItemsGrid").html(resultSet.data);
 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while deleting.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while deleting..");
 $("#alertError").show();
 }
}
function enterToSearch() {
	var input = document.getElementsByClassName("search-button");
	input.addEventListener("keyup", function(event) {
		event.preventDefault();
		if (event.keyCode == 13) {
			document.getElementsByClassName("search-button").click();
		}
	});
}

$(function() {
	$(".search-input").keyup(function(event) {
		var jsonData = "";
		$.ajax({
			type : "get",
			url : "suggest.jsp?term=" + document.getElementById("input").value,
			datatype : "json",
			async : true,
			error : function() {
				console.error("Load recommand data failed!");
			},
			success : function(data) {
				data = JSON.parse(data);
				$(".search-input").autocomplete({
					source : data
				});
			}
		});
	})
});
/**
 * 
 */

function addToCart(action) {
	document.addForm.action = action;
	document.addForm.submit();
	alert("도서가 장바구니에 추가되었습니다.");
}

function removeFromCart(action) {
	document.removeForm.action = action;
	document.removeForm.submit();
	//window.location.reload(); // 요청이 다 처리되기도 전에 새로고침됨
	
	// 요청 처리 이후 새로고침되도록 딜레이 주기
	setInterval(function() {
		window.location.reload();
	}, 10);
}

function clearCart() {
	document.clearForm.submit(); 
	//window.location.reload(); // 요청이 다 처리되기도 전에 새로고침됨
	
	// 요청 처리 이후 새로고침되도록 딜레이 주기
	setInterval(function() {
		window.location.reload();
	}, 10);
}

function deleteConfirm(id) {
	if(confirm("해당 도서를 삭제합니다!") == true) {
		location = "./delete?id="+id;
	} else {
		return;
	}
}
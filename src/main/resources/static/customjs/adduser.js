$(document).ready(function() {
	// add user form add-remove role //
	const firstSelect = document.querySelector("#addUserForm #sourceRoleSelect");
	const secondSelect = document.querySelector("#addUserForm #targetRoleSelect");
	const addButton = document.querySelector("#addUserForm #addRoleBtn");
	const removeButton = document.querySelector("#addUserForm #removeRoleBtn");

	// "Ekle" butonu işlevi
	        addButton.addEventListener("click", () => {
	            const selectedOptions = Array.from(firstSelect.selectedOptions);
	            selectedOptions.forEach(option => {
	                // Eğer ikinci select'te aynı value yoksa ekle
	                if (![...secondSelect.options].some(o => o.value === option.value)) {
	                    const newOption = document.createElement("option");
	                    newOption.value = option.value;
	                    newOption.textContent = option.textContent;
	                    newOption.selected = true; // Eklenen eleman seçili olsun
	                    secondSelect.appendChild(newOption);
	                    firstSelect.removeChild(option); // Birinci select'ten kaldır
	                }
	            });
	        });

	        // "Çıkar" butonu işlevi
	        removeButton.addEventListener("click", () => {
	            const selectedOptions = Array.from(secondSelect.selectedOptions);
	            selectedOptions.forEach(option => {
	                // Eğer birinci select'te aynı value yoksa ekle
	                if (![...firstSelect.options].some(o => o.value === option.value)) {
	                    const newOption = document.createElement("option");
	                    newOption.value = option.value;
	                    newOption.textContent = option.textContent;
	                    firstSelect.appendChild(newOption);
	                }
	                secondSelect.removeChild(option); // İkinci select'ten kaldır
					secondSelect.find("option").prop("selected", true);
	            });
	        });
	// /add user form add-remove role //

	// modal add role //
	$("#userEditModal #addRoleBtn").on('click', function() {
		$('#userEditModal #sourceRoleSelect option:selected').each(function() {
			$('#userEditModal #targetRoleSelect').append($(this).clone().prop('selected', true));
			$(this).remove();
		});
	});

	$("#userEditModal #removeRoleBtn").on('click', function() {
		$('#userEditModal #targetRoleSelect option:selected').each(function() {
			$('#userEditModal #sourceRoleSelect').append($(this).clone());
			$(this).remove();
		});
	});
	// /modal add role //
	// form edit  //

	$("table tbody #userFormEditBtn").on("click", function(e) {
		e.preventDefault();
		$("#addUserForm #userFormId").removeAttr("style");
		$("#addUserForm").attr("action", "/admin/update/user");

		var sourceSelect = $('#addUserForm #sourceRoleSelect');
		var targetSelect = $('#addUserForm #targetRoleSelect');
		
		var sourceSelectList = ["ADMIN", "USER", "CUSTOMER"];
		sourceSelect.empty();
		for(var i in sourceSelectList){
			sourceSelect.append('<option value=' + sourceSelectList[i] + '>' + sourceSelectList[i] + '</option>');
		};
		
		var href = $(this).attr("href");

		$.get(href, function(user, status) {
			$("#addUserForm #userid").val(user.id);
			
			$("#addUserForm #userImage").attr("src", "data:image/*;base64," + user.image);
			$("#addUserForm #firstname").val(user.firstname);
			$("#addUserForm #lastname").val(user.lastname);			
			
			$("#addUserForm #username").val(user.username);
			$("#addUserForm #password").val(user.openpassword);

			var userRolesDb = user.roles.split(",");
			targetSelect.empty();
			$.each(userRolesDb, function(i, item) {
				sourceSelect.find('option[value=' + item + ']').remove();
				targetSelect.append('<option value=' + item + '>' + item + '</option>');
			});
			targetSelect.find("option").prop("selected", true);
			$('#addUserForm #enabled').prop('checked', user.enabled);
			$('#addUserForm #accountNonExpired').prop('checked', user.accountNonExpired);
			$('#addUserForm #accountNonLocked').prop('checked', user.accountNonLocked);
			$('#addUserForm #credentialsNonExpired').prop('checked', user.credentialsNonExpired);
	
		});
		
	});

	// /form edit //

	// see pwd on table//
		$('table tbody #togglePassword').click(function(e) {
			// Şifreyi aç/kapa işlevi
				e.preventDefault();
				const passwordField = $(this).closest('td').find('#passwordField'); // Aynı satırdaki şifre alanını seç
				const type = passwordField.attr('type') === 'password' ? 'text' : 'password';
				passwordField.attr('type', type);

				// İkonu değiştir
				$(this).closest('td').find('i').toggleClass('bi-eye bi-eye-slash');
		});
	

	//  /see pwd  on table//
	
	// see pwd on form //
	$('#addUserForm #togglePassword').click(function(e) {
		// Şifreyi aç/kapa işlevi
		e.preventDefault();
		const passwordField = $('#addUserForm #password');
		const type = passwordField.attr('type') === 'password' ? 'text' : 'password';
		passwordField.attr('type', type);

		// İkonu değiştir
		$(this).find('i').toggleClass('bi-eye bi-eye-slash');
	});
	//  /see pwd  on form//
	
	// newUserBtn Click clear form //
//	$("#addUserForm #newUserBtn").click(function(e){
//		e.preventDefault();
//		$("#addUserForm #firstname").value("");
//		$("#addUserForm #lastname").value("");
//		$("#addUserForm #username").value("");
//		$("#addUserForm #password").value("");
//		
//	})
	// /newUserBtn Click clear form //
	
	/* delete modal */
	$("table#userTable #userDeleteBtn").on("click", function(e){
		e.preventDefault();
		var href = $(this).attr("href");
		$("#userDeleteModal #deleteConfirmBtn").attr("href", href);
		
	});
	/* /delete modal */
	
	// add user image to form //
	$("form#addUserForm #formUserImage").on('change', function() {
		readURLAddUserImage(this);
	});
	// /add user image to form //

});



/* add user image to form */
function readURLAddUserImage(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			$("form#addUserForm #userImage").attr('src', e.target.result);
		}
		reader.readAsDataURL(input.files[0]);
	}
}
/* /add user image to form */
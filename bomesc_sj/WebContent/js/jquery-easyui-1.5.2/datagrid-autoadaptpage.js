$.fn.extend({
	resizeDataGrid : function(form) {
		var width = $(form).width() - 18;
		$(this).datagrid('resize', {
			width : width
		});
	}
});
function resizeDataGrid(dg,form){
	$(dg).resizeDataGrid(form);
	$(window).resize(function() {
    	$(dg).resizeDataGrid(form);
	});
}
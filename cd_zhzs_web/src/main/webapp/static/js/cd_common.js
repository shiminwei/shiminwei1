/**
 * 清空表单，并重新加载navTab
 */
function cleanForm(id, type) {
	var divIndex = 0;
	$("ul.navTab-tab li").each(function() {
		if ($(this).attr('class') == 'selected') {
			divIndex = $(this).index();
		}
	})
	$("div#navTab div.navTab-panel  div.page ").each(
			function() {
				if ($(this).index() == divIndex) {
					var objForm = $(this).find('form[id="' + id + '"]');
					$(objForm).find(':input').not(
							":button, :submit, :reset, :hidden").val("")
							.removeAttr("checked").remove("selected");
					$(objForm).find(" .toolBar .search button").click();
				}
			})
}
/**
 * 根据rel刷新指定的navtTab
 */
function reloadNavTab(param) {
	navTab.reloadFlag(param.rel); // 根据rel刷新navTab
	return true;
}

/**
 * 获取当前DIV navTab的内容
 */
function getNowDiv(name) {
	var divIndex = 0;
	$("ul.navTab-tab li").each(function() {
		if ($(this).attr('class') == 'selected') {
			divIndex = $(this).index();
		}
	})
	$("div#navTab div.navTab-panel  div.page ").each(function() {
		if ($(this).index() == divIndex) {
			var value = $(this).find('name="' + name + '"');
			alert(value);
			
		}
	})
}
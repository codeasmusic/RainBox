function catch_keydown(sel) {
	switch (event.keyCode) {
	case 13:
		//Enter;
		sel.options[sel.length] = new Option("", "", false, true);
		event.returnValue = false;
		break;
	case 8:
		//Back Space;
		var s = sel.options[sel.selectedIndex].text;
		sel.options[sel.selectedIndex].text = s.substr(0, s.length - 1);
		event.returnValue = false;
		break;
	}

}

function catch_press(sel) {
	sel.options[sel.selectedIndex].text = sel.options[sel.selectedIndex].text
			+ String.fromCharCode(event.keyCode);
	event.returnValue = false;
}

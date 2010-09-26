// JavaScript Document
function clearField(element){
	element.value="";
	return;
}

function editLocated(action, idElement, a){
	if (idElement.value){
		a.href=action +".do?VirtualDispatchName=findByPK&id="+idElement.value;
		return true;
	} else return false;
}

function goToSite(Element, a){
	if (Element.value){
		a.href="http://"+Element.value;
		return true;
	} else return false;
}

function confirmar(action, msg){
	if (confirm(msg)){
		document.location=action;
		return true;
	} else return false;
}

function addText(combo, textArea){
	elementCombo = document.getElementById(combo);
	elementTextArea = document.getElementById(textArea);
	if (!elementTextArea.value==''){
		elementTextArea.value += ', ';
	}
	elementTextArea.value += elementCombo.options[elementCombo.selectedIndex].text ;
	return;
}
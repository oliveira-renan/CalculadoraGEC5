package com.calculadora.validacao;

import javafx.scene.control.TextField;

public class InputOnlyNumberTextField extends TextField {
	
	public InputOnlyNumberTextField() {
		
	}
	
	@Override
	public void replaceText(int start, int end, String text) {
		
		if (text.matches("[0-9]") || text.isEmpty())
			super.replaceText(start, end, text);
	}
	
	@Override
	public void replaceSelection(String replacement) {
		super.replaceSelection(replacement);
	}
}
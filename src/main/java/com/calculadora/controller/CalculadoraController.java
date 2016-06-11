package com.calculadora.controller;

import java.math.BigDecimal;

import com.calculadora.config.ConfigProperties;
import com.calculadora.service.OperacoesBasicasService;
import com.calculadora.service.OperacoesBasicasServiceImpl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

@SuppressWarnings("unused")
public class CalculadoraController {
	private String operador = "";
	private OperacoesBasicasService operacoesBasicasService;
	
	private AnchorPane rootLayout;
	private ConfigProperties label;
	private BigDecimal currentNumber;
	private BigDecimal isStartCurrentNumber;
	private boolean isResultado;
	
	private String memory;

	@FXML
	private Label mLabel;

	@FXML
	private Label displayField;

	@FXML
	private Label displayAnteriorField;

	@FXML
	private Button firstParenteses;

	@FXML
	private Button lastParenteses;

	@FXML
	private void initialize() {
	}

	public void show(ConfigProperties label, AnchorPane rootLayout) {
		this.rootLayout = rootLayout;
		this.label = label;
		isResultado = false;
		
		operacoesBasicasService = new OperacoesBasicasServiceImpl();

		firstParenteses.focusedProperty();
		firstParenteses.setDisable(true);
		lastParenteses.setDisable(true);
		mLabel.setVisible(false);

		rootLayout.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent keyEvent) {
				handleKeys(keyEvent);
			}
		});
	}

	private void handleKeys(KeyEvent keyEvent) {
		switch (keyEvent.getCode()) {
		case DIGIT1:
		case NUMPAD1:
			handleNumPad("1");
			break;
		case DIGIT2:
		case NUMPAD2:
			handleNumPad("2");
			break;
		case DIGIT3:
		case NUMPAD3:
			handleNumPad("3");
			break;
		case DIGIT4:
		case NUMPAD4:
			handleNumPad("4");
			break;
		case DIGIT5:
		case NUMPAD5:
			handleNumPad("5");
			break;
		case DIGIT6:
		case NUMPAD6:
			handleNumPad("6");
			break;
		case DIGIT7:
		case NUMPAD7:
			handleNumPad("7");
			break;
		case DIGIT8:
		case NUMPAD8:
			handleNumPad("8");
			break;
		case DIGIT9:
		case NUMPAD9:
			handleNumPad("9");
			break;
		case DIGIT0:
		case NUMPAD0:
			handleNumPad("0");
			break;
		case SUBTRACT:
			handleOperador("-");
			break;
		case ADD:
			handleOperador("+");
			break;
		case DIVIDE:
			handleOperador("/");
			break;
		case MULTIPLY:
			handleOperador("*");
			break;
		case DECIMAL:
			handlePonto();
			break;
		case ENTER:
			handleIgual();
			break;
		case DELETE:
			apagarTudo();
			break;
		case BACK_SPACE:
			apagarUltimaLetra();
			break;
		default:
			break;
		}
	}

	@FXML
	public void apagarTudo() {

		isStartCurrentNumber = null;
		if (displayField.getText() != null || !displayField.getText().equals("")) {
			displayField.setText("");
			displayAnteriorField.setText("");
		}
	}

	@FXML
	public void apagarUltimaLetra() {
		if (!displayField.getText().isEmpty() && displayField.getText().length() > 1) {
			displayField.setText(displayField.getText().substring(0, displayField.getText().length() - 1));
		} else if (displayField.getText().length() == 1) {
			displayField.setText("");
		} else {
			displayField.setText("");
		}
	}

	@FXML
	private void handleIgual() {

		if (displayField.getText() != null) {
			isStartCurrentNumber = calcular(isStartCurrentNumber, new BigDecimal(displayField.getText()), operador);
			displayField.setText(isStartCurrentNumber.toString());
			displayAnteriorField.setText("");
			isStartCurrentNumber = null;
		}
	}

	public BigDecimal calcular(BigDecimal valor1, BigDecimal valor2, String operador) {
		return operacoesBasicasService.calcular(valor1, valor2, operador);
	}

	@FXML
	private void handleMc() {
		memory = null;
		mLabel.setVisible(false);
	}

	@FXML
	private void handleMr() {
		if (memory != null)
			mLabel.setText(memory);
	}

	@FXML
	private void handleMs() {
		if (!displayField.getText().isEmpty()) {
			memory = displayField.getText();
			mLabel.setVisible(true);
		}
	}

	@FXML
	private void handleMplus() {
		if (!displayField.getText().isEmpty()) {
			memory = displayField.getText();
			mLabel.setVisible(true);
		}
	}

	@FXML
	private void handleMmin() {
		if (!displayField.getText().isEmpty()) {
			memory = "-" + displayField.getText();
			mLabel.setVisible(true);
		}
	}

	@FXML
	private void handleFirstParenteses() {

		if (displayField.getText() != null && !displayField.getText().equals("")) {
			if (displayField.getText().charAt(displayField.getText().length() - 1) != ')')
				displayField.setText(displayField.getText() + "(");
		} else {
			displayField.setText(displayField.getText() + "(");
		}

	}

	@FXML
	private void handleLastParenteses() {

		if (displayField.getText() != null && !displayField.getText().equals("")) {
			if (displayField.getText().charAt(displayField.getText().length() - 1) == '(')
				displayField.setText(displayField.getText() + "0)");
			else
				displayField.setText(displayField.getText() + ")");
		}
	}

	@FXML
	private void handlePonto() {

		if (displayField.getText().indexOf(".") == -1) {
			if (displayField.getText().length() > 0)
				displayField.setText(displayField.getText() + ".");
			else
				displayField.setText(displayField.getText() + "0.");
		}
	}

	@FXML
	private void handlePI() {
		displayField.setText(operacoesBasicasService.calcularPI().toString());
	}

	@FXML
	private void handleMudarSinal() {

		if (displayField.getText() != "") {
			BigDecimal novoValor = operacoesBasicasService.changeSinal(new BigDecimal(displayField.getText()));
			displayField.setText(novoValor.toString());
		}

	}

	@FXML
	private void handleEuler() {
		displayField.setText(operacoesBasicasService.valorEuler().toString());
	}

	@FXML
	private void handleOperador(ActionEvent actionEvent) {
		handleOperador(((Button) actionEvent.getSource()).getText());
	}

	private void handleOperador(String operacaoAtual) {
		String currentText = displayField.getText();
		String currentAnteriorText = displayAnteriorField.getText();
		
		if (!currentText.isEmpty()) {
			if (isStartCurrentNumber == null) {
				isStartCurrentNumber = new BigDecimal(currentText);
				displayField.setText("");
				if (currentAnteriorText.isEmpty())
					displayAnteriorField.setText(isStartCurrentNumber.toString() + " " + operacaoAtual + " ");
				else
					displayAnteriorField.setText(currentAnteriorText + " " + operacaoAtual + " ");
				operador = operacaoAtual;
			} else {
				String displayAnterior = displayAnteriorField.getText();
				displayAnteriorField.setText(displayAnterior + " " + currentText + " " + operacaoAtual);
				isStartCurrentNumber = calcular(isStartCurrentNumber, new BigDecimal(displayField.getText()), operador);
				displayField.setText(isStartCurrentNumber.toString());
				isResultado = true;
			}
		} else {
			displayAnteriorField.setText(currentText + " " + operador + " ");
		}
	}

	@FXML
	private void handleNumPad(ActionEvent actionEvent) {
		handleNumPad(((Button) actionEvent.getSource()).getText());
	}

	private void handleNumPad(String valorAtual) {
		
		if (isResultado) {
			displayField.setText(valorAtual);
			isResultado = false;
		} else {
			String oldValor = displayField.getText();
			String novoValor = oldValor + valorAtual;
			displayField.setText(novoValor); 
		}
	}

	public void setLabel(ConfigProperties label) {
		this.label = label;
	}

	public void setRootLayout(AnchorPane rootLayout) {
		this.rootLayout = rootLayout;
	}
}

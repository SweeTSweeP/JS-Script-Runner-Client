package com.morhenbigot.js_script_runner_client.pages;

import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@Route("")
@StyleSheet("my-style-sheet.css")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainPage extends VerticalLayout {

    private Button startButton;
    private TextArea codeArea;
    private TextArea resultArea;

    private RestTemplate restTemplate;

    public MainPage() {

        this.startButton = new Button();
        this.codeArea = new TextArea();
        this.resultArea = new TextArea();
        this.restTemplate = new RestTemplate();

        startButton.setIcon(VaadinIcon.PLAY.create());
        startButton.setText("Start");
        startButton.setDisableOnClick(true);
        startButton.addClickListener(e -> {
            var text = restTemplate
                    .postForObject("http://localhost:7080/api/script", codeArea.getValue(), String.class);

            resultArea.setValue(text);

            startButton.setEnabled(true);
        });

        codeArea.setLabel("Your code:");
        codeArea.setMinHeight("500px");
        codeArea.setSizeFull();
        codeArea.setMaxHeight("500px");
        codeArea.setPlaceholder("Write your script here...");

        resultArea.setLabel("Result:");
        resultArea.setMinHeight("250px");
        resultArea.setSizeFull();
        resultArea.setMaxHeight("250px");
        resultArea.setEnabled(false);


        add(startButton, codeArea, resultArea);
    }
}

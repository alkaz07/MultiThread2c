package com.example.multithread2;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class HelloController {

    @FXML
    Button btnWork1, btnWork2;

    ObservableList<String> messages = FXCollections.observableArrayList();
    ObservableList<String> messages2 = javafx.collections.FXCollections.synchronizedObservableList(messages);

    BlockingQueue<String> mesQueue = new LinkedBlockingQueue<>();

    @FXML
    TextArea log;

    @FXML
    TableView<String> table;

    public void initialize()
    {
        ComplexWorker worker1 = new ComplexWorker(500, "Гриша", messages2);
        ComplexWorker worker2 = new ComplexWorker(600, "Коля", messages2);
        btnWork1.setOnAction(actionEvent -> worker1.start());
        btnWork2.setOnAction(actionEvent -> worker2.start());

        /*messages.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                while(change.next())
                    if(change.wasAdded())
                        for (String mes : change.getAddedSubList() ) {
                            System.out.println(mes);
                        }
            }
        });*/
        messages2.addListener((ListChangeListener<String>) change -> {
            while(change.next())
                if(change.wasAdded())
                    for (String mes : change.getAddedSubList() ) {
                        System.out.println(mes);
                       // log.setText(mes+"\n"+log.getText());
                        mesQueue.add(mes);
                    }
        });

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                ArrayList<String> strings = new ArrayList<>();
                mesQueue.drainTo(strings);
                for (String s : strings) {
                    log.setText(s + "\n" + log.getText());
                }
            }
        };
        animationTimer.start();

        initTable();
    }

    private void initTable() {
        table.setItems(messages2);
        TableColumn<String, String> col1 = new TableColumn<>("сообщение");
        col1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> x) {
                return new SimpleStringProperty(x.getValue());
            }
        });
        table.getColumns().add(col1);
    }

}
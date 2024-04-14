package tn.badbud.nfttun.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.badbud.nfttun.models.NFT;
import tn.badbud.nfttun.services.NFTService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.jar.Attributes;

public class ShowNfts {

    private final NFTService ps = new NFTService();

    @FXML
    private TableColumn<NFT, String> ImageCol;

    @FXML
    private TableView<NFT> NFTTable;

    @FXML
    private TableColumn<NFT, String> NameCol;

    @FXML
    private TableColumn<NFT, Double> PriceCol;

    @FXML
    private TableColumn<NFT, String> StatusCol;


    @FXML
    void initialize() {
        addButtonToTable();

        NFTService nftService = new NFTService();
        try {
            List<NFT> nfts = nftService.getAll();
            ObservableList<NFT> observableList = FXCollections.observableList(nfts);
            NFTTable.setItems(observableList);
        } catch (RuntimeException e) {
            showAlert("Error Loading Data", "Unable to fetch data from the database. Please check your connection and try again.");
        }

        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        StatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        ImageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void addButtonToTable() {
        TableColumn<NFT, Void> actionCol = new TableColumn<>("Action");

        actionCol.setCellFactory(param -> new TableCell<NFT, Void>() {
            private final Button deleteBtn = new Button("Delete");
            private final Button updateBtn = new Button("Update");

            {
                deleteBtn.setOnAction(event -> {
                    NFT nft = getTableView().getItems().get(getIndex());
                    deleteNFT(nft);
                });
                updateBtn.setOnAction(event -> {
                    NFT nft = getTableView().getItems().get(getIndex());
                    try {
                        updateNFT(nft); // You need to implement this method
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox manageBtn = new HBox(deleteBtn, updateBtn);
                    manageBtn.setSpacing(10); // Set spacing between buttons
                    setGraphic(manageBtn);
                }
            }
        });

        NFTTable.getColumns().add(actionCol);
    }

    private void deleteNFT(NFT nft) {
        try {
            ps.delete(nft); // Assuming you have a delete method in your service
            NFTTable.getItems().remove(nft);
        } catch (RuntimeException e) {
            showAlert("Error", "Cannot delete the entry: " + e.getMessage());
        }
    }

    private void updateNFT(NFT nft) throws IOException {
        // Correctly set the resource path
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditNft.fxml"));
        if (loader.getLocation() == null) {
            showAlert("Error", "The FXML file was not found.");
            return;
        }
        Parent root = loader.load();

        // Get the controller and set the NFT
        EditNft controller = loader.getController();
        controller.initData(nft);

        // Setup the stage
        Stage stage = new Stage();
        stage.setTitle("Edit NFT");
        stage.setScene(new Scene(root));
        stage.show();
    }

}


package tn.badbud.nfttun.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.badbud.nfttun.models.NFT;
import tn.badbud.nfttun.services.NFTService;

import java.io.IOException;

public class AddNft {

    private final NFTService ps = new NFTService();

    @FXML
    private TextField NFTImageTF;

    @FXML
    private TextField NFTNameTF;

    @FXML
    private TextField NFTPriceTF;

    @FXML
    private TextField NFTStatusTF;

    @FXML
    void newNFT(ActionEvent event) {
        if (validateInput()) {
        try {
            ps.add(new NFT(NFTNameTF.getText(), NFTStatusTF.getText(), NFTImageTF.getText(), Double.parseDouble(NFTPriceTF.getText())));
            showAlert("ADD Successful", "The NFT has been Added successfully.");
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (Exception e) {
            showAlert("Update Error", "Failed to update the NFT: " + e.getMessage());
        }
        }

    }

    @FXML
    void ShowNftList(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ShowNfts.fxml"));
            NFTPriceTF.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean validateInput() {
        // Basic validation example
        if (NFTNameTF.getText().isEmpty() || NFTPriceTF.getText().isEmpty()) {
            showAlert("Validation Error", "Name and price must not be empty.");
            return false;
        }
        try {
            Double.parseDouble(NFTPriceTF.getText());
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Price must be a valid number.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

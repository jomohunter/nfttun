package tn.badbud.nfttun.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.badbud.nfttun.models.NFT;
import tn.badbud.nfttun.services.NFTService;

import java.io.IOException;

public class EditNft {


    @FXML
    private TextField NFTImageTF;

    @FXML
    private TextField NFTNameTF;

    @FXML
    private TextField NFTPriceTF;

    @FXML
    private TextField NFTStatusTF;

    @FXML
    void ShowNftList(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ShowNfts.fxml"));
            NFTPriceTF.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private NFT nft;

    void initData(NFT nft){
        this.nft = nft;
        NFTNameTF.setText(this.nft.getName());
        NFTPriceTF.setText(String.valueOf(this.nft.getPrice()));
        NFTStatusTF.setText(this.nft.getStatus());
        NFTImageTF.setText(this.nft.getImage());


    }
    private final NFTService ps = new NFTService();

    @FXML
    void editNFT(ActionEvent event) {
        nft.setStatus(NFTStatusTF.getText());
        nft.setName(NFTNameTF.getText());
        nft.setImage(NFTImageTF.getText());
        nft.setPrice(Double.parseDouble(NFTPriceTF.getText()));
        if (validateInput()) {
        try {
            ps.update(nft);
            showAlert("Update Successful", "The NFT has been updated successfully.");
        } catch (Exception e) {
            showAlert("Update Error", "Failed to update the NFT: " + e.getMessage());
        }
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

package tn.badbud.nfttun;

import tn.badbud.nfttun.models.NFT;
import tn.badbud.nfttun.services.NFTService;
import tn.badbud.nfttun.util.MaConnexion;

import java.util.List;

public class Main {
    public static void main(String[] args) {


        NFTService nftservice = new NFTService();
        NFT nft = new NFT("tawa","javafx","test",21);
        nftservice.add(nft);


        List<NFT> nfts = nftservice.getAll();
        System.out.println(nfts);
    }
}

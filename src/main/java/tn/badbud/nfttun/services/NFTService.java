package tn.badbud.nfttun.services;

import tn.badbud.nfttun.Main;
import tn.badbud.nfttun.interfaces.IService;
import tn.badbud.nfttun.models.NFT;
import tn.badbud.nfttun.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NFTService implements IService<NFT> {

    //att

    Connection cnx = MaConnexion.getInstance().getCnx();

    //actions
    @Override
    public void add(NFT nft) {

        String req = "INSERT INTO `nft`(`name`, `price`, `status`, `image`) VALUES (?,?,?,?)";
        try{
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1,nft.getName());
            ps.setDouble(2,nft.getPrice());
            ps.setString(3,nft.getStatus());
            ps.setString(4,nft.getImage());

            ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(NFT nft) {
        String req = "UPDATE `nft` SET `name` = ?, `price` = ?, `status` = ?, `image` = ? WHERE `id` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, nft.getName());
            ps.setDouble(2, nft.getPrice());
            ps.setString(3, nft.getStatus());
            ps.setString(4, nft.getImage());
            ps.setInt(5, nft.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(NFT nft) {
        String req = "DELETE FROM `nft` WHERE `id` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, nft.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<NFT> getAll() {

        List<NFT> nfts = new ArrayList<>();

        String req = "SELECT * FROM `nft`";
        try {

            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while(res.next()){

                NFT nft = new NFT();
                nft.setId(res.getInt("id"));
                nft.setName(res.getString(2));
                nft.setPrice(res.getDouble(3));
                nft.setStatus(res.getString(4));

                nfts.add(nft);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nfts;
    }

    @Override
    public NFT getOne(int id) {
        String req = "SELECT * FROM `nft` WHERE `id` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                NFT nft = new NFT();
                nft.setId(res.getInt("id"));
                nft.setName(res.getString("name"));
                nft.setPrice(res.getDouble("price"));
                nft.setStatus(res.getString("status"));
                nft.setImage(res.getString("image"));
                return nft;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

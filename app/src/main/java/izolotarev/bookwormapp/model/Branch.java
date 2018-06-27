package izolotarev.bookwormapp.model;

/**
 * Created by Игорь on 27.05.2018.
 */

public class Branch {
    private int branchId;
    private String name;
    private String imageUrl;

    public Branch(int branchId, String name, String imageUrl){
        this.branchId = branchId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

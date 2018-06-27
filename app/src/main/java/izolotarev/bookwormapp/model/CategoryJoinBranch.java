package izolotarev.bookwormapp.model;

/**
 * Created by Игорь on 09.06.2018.
 */

public class CategoryJoinBranch {
    private int categoryId;
    private String name;
    private int branchId;

    public CategoryJoinBranch(int categoryId, String name, int branchId) {
        this.categoryId = categoryId;
        this.name = name;
        this.branchId = branchId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
}

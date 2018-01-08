package classes;

public class Department {
    private int deptNum;
    private String deptName;
    private String loc;

    public Department(int deptNum, String deptName, String loc) {
        this.deptNum = deptNum;
        this.deptName = deptName;
        this.loc = loc;
    }

    public int getDeptNum() {
        return deptNum;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getLoc() {
        return loc;
    }

    public void setDeptNum(int deptNum) {
        this.deptNum = deptNum;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}

package entity;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable {

    private  Long total  ; // 总记录数

    private List  Rows  ;  //  每页显示的内容

    public PageResult() {
    }

    public PageResult(Long total, List rows) {
        this.total = total;
        Rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return Rows;
    }

    public void setRows(List rows) {
        Rows = rows;
    }
}

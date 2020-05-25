package club.jming.entity;

import java.util.List;

/**
 * 分页实体类
 */
public class PageVo<E> {
    private Integer currentPage;//当前页
    private Integer totalPage;//总页数
    private Integer rows;//每页的数据量
    private Integer total;//总数据量
    private List<E> data;//数据
    private Integer start;//每页开始查询的下标

    public PageVo(String currentPage, String rows, Integer total){
        //1从前台接受currentPage和rows，如果为空则赋予默认值
        if (currentPage == null || "".equals(currentPage)) this.currentPage = 1;
        else this.currentPage = Integer.parseInt(currentPage);

        if (rows == null || "".equals(rows)) this.rows = 10;
        else this.rows = Integer.parseInt(rows);
        //2.接受从数据库中查询出的总数据
        this.total = total;
        //3.计算总页数
        this.totalPage = this.total % this.rows == 0 ? (this.total / this.rows) : (this.total / this.rows) + 1;
        //4.计算每页开始的下标
        this.start = (this.currentPage - 1) * this.rows;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "PageVo{" +
                "currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", rows=" + rows +
                ", total=" + total +
                ", data=" + data +
                ", start=" + start +
                '}';
    }
}

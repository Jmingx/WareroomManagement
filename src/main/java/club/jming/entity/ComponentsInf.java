package club.jming.entity;

import java.io.Serializable;

/**
 * 零件信息
 */
public class ComponentsInf implements Serializable,Cloneable{
    /** 零件编号;主键 */
    private Integer id ;
    /** 供应商编号;外键 */
    private Integer supplierId ;
    /** 目前价格 */
    private Double price ;
    /** 零件名称 */
    private String name ;

    /** 零件编号;主键 */
    public Integer getId(){
        return this.id;
    }
    /** 零件编号;主键 */
    public void setId(Integer id){
        this.id = id;
    }
    /** 供应商编号;外键 */
    public Integer getSupplierId(){
        return this.supplierId;
    }
    /** 供应商编号;外键 */
    public void setSupplierId(Integer supplierid){
        this.supplierId = supplierid;
    }
    /** 目前价格 */
    public Double getPrice(){
        return this.price;
    }
    /** 目前价格 */
    public void setPrice(Double price){
        this.price = price;
    }
    /** 零件名称 */
    public String getName(){
        return this.name;
    }
    /** 零件名称 */
    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "ComponentsInf{" +
                "id=" + id +
                ", supplierId=" + supplierId +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
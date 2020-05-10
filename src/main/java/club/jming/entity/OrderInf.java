package club.jming.entity;

import java.io.Serializable;

/**
 * 订货信息
 */
public class OrderInf implements Serializable,Cloneable{
    /** 订货编号;主键 */
    private Integer id ;
    /** 零件编号;外键 */
    private Integer componentId ;
    /** 订货数量 */
    private Integer amount ;

    /** 订货编号;主键 */
    public Integer getId(){
        return this.id;
    }
    /** 订货编号;主键 */
    public void setId(Integer id){
        this.id = id;
    }
    /** 零件编号;外键 */
    public Integer getComponentId(){
        return this.componentId;
    }
    /** 零件编号;外键 */
    public void setComponentId(Integer componentid){
        this.componentId = componentid;
    }
    /** 订货数量 */
    public Integer getAmount(){
        return this.amount;
    }
    /** 订货数量 */
    public void setAmount(Integer amount){
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderInf{" +
                "id=" + id +
                ", componentId=" + componentId +
                ", amount=" + amount +
                '}';
    }
}

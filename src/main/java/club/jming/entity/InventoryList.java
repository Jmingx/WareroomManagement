package club.jming.entity;

import java.io.Serializable;

/**
 * 库存清单
 */
public class InventoryList implements Serializable,Cloneable{
    /** 库存清单编号;主键 */
    private Integer id ;
    /** 零件编号;外键 */
    private Integer componentId ;
    /** 库存量 */
    private Integer inventory ;
    /** 库存临界值 */
    private Integer criticalValue ;

    /** 库存清单编号;主键 */
    public Integer getId(){
        return this.id;
    }
    /** 库存清单编号;主键 */
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
    /** 库存量 */
    public Integer getInventory(){
        return this.inventory;
    }
    /** 库存量 */
    public void setInventory(Integer inventory){
        this.inventory = inventory;
    }
    /** 库存临界值 */
    public Integer getCriticalValue(){
        return this.criticalValue;
    }
    /** 库存临界值 */
    public void setCriticalValue(Integer criticalvalue){
        this.criticalValue = criticalvalue;
    }

    @Override
    public String toString() {
        return "InventoryList{" +
                "id=" + id +
                ", componentId=" + componentId +
                ", inventory=" + inventory +
                ", criticalValue=" + criticalValue +
                '}';
    }
}
package club.jming.entity;

import java.io.Serializable;

/**
 * 事务
 */
public class Business implements Serializable,Cloneable{
    /** 事务编号;主键 */
    private Integer id ;
    /** 零件编号;外键 */
    private Integer componentId ;
    /** 事务类型 */
    private Integer business ;

    /** 事务编号;主键 */
    public Integer getId(){
        return this.id;
    }
    /** 事务编号;主键 */
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
    /** 事务类型 */
    public Integer getBusiness(){
        return this.business;
    }
    /** 事务类型 */
    public void setBusiness(Integer business){
        this.business = business;
    }

    @Override
    public String toString() {
        return "Business{" +
                "id=" + id +
                ", componentId=" + componentId +
                ", business=" + business +
                '}';
    }
}

package club.jming.entity;

import java.io.Serializable;

/**
 * 供应商信息
 */
public class SupplierInf implements Serializable,Cloneable{
    /** 供应商编号 */
    private Integer id ;
    /** 供应商名称 */
    private String name ;
    /** 联系方式 */
    private String contact ;

    /** 供应商编号 */
    public Integer getId(){
        return this.id;
    }
    /** 供应商编号 */
    public void setId(Integer id){
        this.id = id;
    }
    /** 供应商名称 */
    public String getName(){
        return this.name;
    }
    /** 供应商名称 */
    public void setName(String name){
        this.name = name;
    }
    /** 联系方式 */
    public String getContact(){
        return this.contact;
    }
    /** 联系方式 */
    public void setContact(String contact){
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "SupplierInf{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}

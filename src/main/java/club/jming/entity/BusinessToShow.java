package club.jming.entity;

public class BusinessToShow {
    /** 事务编号;主键 */
    private Integer id ;
    /** 零件编号;外键 */
    private Integer componentId ;
    /** 事务类型 */
    private Integer business ;
    /**
     * 零件名称
     */
    private String componentName;

    public BusinessToShow() {
    }

    @Override
    public String toString() {
        return "BusinessToShow{" +
                "id=" + id +
                ", componentId=" + componentId +
                ", business=" + business +
                ", componentName='" + componentName + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public Integer getBusiness() {
        return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public BusinessToShow(Integer id, Integer componentId, Integer business, String componentName) {
        this.id = id;
        this.componentId = componentId;
        this.business = business;
        this.componentName = componentName;
    }
}

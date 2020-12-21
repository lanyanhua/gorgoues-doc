package com.lancabbage.lancodeapi.bean.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "notes_config")
public class NotesConfig {
    /**
     * ID
     */
    @Id
    private Integer id;

    /**
     * 自定义：
     * 注释 @Description:
     * classTag
     * methodTag
     * methodParamTag
     * methodReturnTag
     * fieldTag
     * 注解 @Api(tags)
     * classAnnotation
     * methodAnnotation
     * fieldAnnotation
     */
    private String type;

    /**
     * 注释
     */
    private String notes;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取自定义：
     * 注释 @Description:
     * classTag
     * methodTag
     * methodParamTag
     * methodReturnTag
     * fieldTag
     * 注解 @Api(tags)
     * classAnnotation
     * methodAnnotation
     * fieldAnnotation
     *
     * @return type - 自定义：
     * 注释 @Description:
     * classTag
     * methodTag
     * methodParamTag
     * methodReturnTag
     * fieldTag
     * 注解 @Api(tags)
     * classAnnotation
     * methodAnnotation
     * fieldAnnotation
     */
    public String getType() {
        return type;
    }

    /**
     * 设置自定义：
     * 注释 @Description:
     * classTag
     * methodTag
     * methodParamTag
     * methodReturnTag
     * fieldTag
     * 注解 @Api(tags)
     * classAnnotation
     * methodAnnotation
     * fieldAnnotation
     *
     * @param type 自定义：
     *             注释 @Description:
     *             classTag
     *             methodTag
     *             methodParamTag
     *             methodReturnTag
     *             fieldTag
     *             注解 @Api(tags)
     *             classAnnotation
     *             methodAnnotation
     *             fieldAnnotation
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取注释
     *
     * @return notes - 注释
     */
    public String getNotes() {
        return notes;
    }

    /**
     * 设置注释
     *
     * @param notes 注释
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
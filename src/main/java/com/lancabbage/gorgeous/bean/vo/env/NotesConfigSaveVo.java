package com.lancabbage.gorgeous.bean.vo.env;


import javax.validation.constraints.NotNull;

public class NotesConfigSaveVo {
    /**
     * ID
     */
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
    @NotNull
    private String type;

    /**
     * 注释
     */
    @NotNull
    private String notes;



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

}
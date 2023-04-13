package hse.leisure.voter.dao;

public class Vote {
    /**
     * Номер голосования.
     */
    private Integer VID;

    /**
     * Название голосования.
     */
    private String Title;

    /**
     * Описание.
     */
    private String Describe;

    /**
     * Режим голосования.
     */
    private String Selection;

    /**
     * Тип.
     */
    private Integer Type;

    /**
     * Лимит голосов.
     */
    private Integer Limit;

    public Integer getVID() {
        return VID;
    }

    public void setVID(Integer VID) {
        this.VID = VID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public String getSelection() {
        return Selection;
    }

    public void setSelection(String selection) {
        Selection = selection;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public Integer getLimit() {
        return Limit;
    }

    public void setLimit(Integer limit) {
        Limit = limit;
    }
}

package hse.leisure.voter.dao;

public class Select {
    /**
     * Номер.
     */
    private String num;

    /**
     * Количество голосов.
     */
    private String count;

    /**
     * Голоса.
     */
    private String selectionText;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSelectionText() {
        return selectionText;
    }

    public void setSelectionText(String selectionText) {
        this.selectionText = selectionText;
    }
}

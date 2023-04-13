package hse.leisure.voter.mapper;

import hse.leisure.voter.dao.Vote;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface VoteMapper {
    /**
     * Получить голосования по номеру.
     * @param VID Номер.
     * @return Голосование.
     */
    @Select("SELECT * FROM Voter_Vote where VID = #{VID}")
    Vote getVote(@Param("VID") Integer VID);

    /**
     * Проверить голосование по номеру.
     * @param VID Номер.
     * @return Голосование.
     */
    @Select("SELECT VID FROM Voter_Vote where VID = #{VID}")
    Vote checkVote(@Param("VID") Integer VID);

    /**
     * Проголосовать.
     * @param selection Выбор.
     * @param VID Номер.
     * @return Количество обновленных строк.
     */
    @Update("UPDATE Voter_Vote SET Selection = #{Selection} WHERE VID = #{VID}")
    int vote(@Param("Selection") String selection, @Param("VID") Integer VID);

    /**
     * Добавить голосования.
     * @param title Название.
     * @param describe Описание.
     * @param selection Выбор.
     * @param type Тип.
     * @param limit Лимит.
     */
    @Insert("INSERT INTO Voter_Vote" +
            " (Title, `Describe`, Selection, Type, `Limit`) VALUES" +
            " (#{title}, #{describe}, #{selection}, #{type}, #{limit})")
    void insertVote(@Param("title") String title, @Param("describe") String describe, @Param("selection") String selection, @Param("type") Integer type, @Param("limit") Integer limit);

    /**
     * Получить голосование по параметрам.
     * @param title Название.
     * @param describe Описание.
     * @param selection Выбор.
     * @param type Тип.
     * @param limit Лимит.
     * @return Голосование.
     */
    @Select("SELECT VID FROM Voter_Vote WHERE" +
            " Title = #{title} AND `Describe` = #{describe} AND Selection = #{selection} AND Type = #{type} AND `Limit` = #{limit}" +
            " ORDER BY VID DESC LIMIT 1")
    Integer queryVoteID(@Param("title") String title, @Param("describe") String describe, @Param("selection") String selection, @Param("type") Integer type, @Param("limit") Integer limit);
}

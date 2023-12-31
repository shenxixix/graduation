package com.shenxi.psych.mapper;

import com.shenxi.psych.entity.AskAndAnswer;
import com.shenxi.psych.entity.Question;
import com.shenxi.psych.entity.QuestionAndTag;
import com.shenxi.psych.entity.Tag;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @Author shenxi
 * @Date 2023/10/11 22:04
 * @Version 1.0
 */
public interface QuestionMapper {

    @Select("select distinct quest_id from ask_and_answer where stu_id = #{id}")
    List<Integer> getQuestionByStuId(Integer id);

    @Select("select * from question where id = #{id}")
    Question getQuestionById(Integer id);

    /**
     * 根据问题id查找问题相关信息
     * @param id
     * @return
     */
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "id",property = "patient",one = @One(select = "com.shenxi.psych.mapper.PatientMapper.getStuByQuesId",fetchType= FetchType.DEFAULT)),
            @Result(column = "id",property = "askAndAnsList",many = @Many(select = "com.shenxi.psych.mapper.QuestionMapper.getAskAndAns",fetchType=FetchType.DEFAULT)),
            @Result(column = "id",property = "questionAndTags",many = @Many(select = "com.shenxi.psych.mapper.QuestionMapper.getQuestionAndTagByQid",fetchType=FetchType.DEFAULT)),
    })
    @Select("select * from question where id = #{id}")
    Question getQuestionByIdV2(Integer id);

    //查询QuestionAndTag
    @Results({
            @Result(column = "tag_id",property = "tag",one = @One(select = "com.shenxi.psych.mapper.QuestionMapper.getTagById",fetchType=FetchType.DEFAULT)),
    })
    @Select("select * from questions_and_tags where quest_id = #{questionId}")
    List<QuestionAndTag> getQuestionAndTagByQid(Integer questionId);

    //根据标签id查询标签
    @Select("select * from tag where id = #{id}")
    Tag getTagById(Integer id);

    @Results({
            @Result(column = "doctor_id",property = "doctor",one = @One(select = "com.shenxi.psych.mapper.DoctorMapper.getDoctorNameById",fetchType=FetchType.DEFAULT))
    })
    @Select("select * from ask_and_answer where quest_id = #{questionId}")
    List<AskAndAnswer> getAskAndAns(Integer questionId);

    @Insert("insert into question(content,view_count,likes,anonymous,status,gmt_create,gmt_modified) values (#{content},#{viewCount},#{likes},#{anonymous},#{status},#{gmtCreate},#{gmtModified})")
    void insertQues(Question question);

    @Insert("insert into ask_and_answer (quest_id, stu_id,gmt_create) values (#{quesId}, #{stuId},#{createTime})")
    void insertQuesWithStu(Integer quesId, Integer stuId,Long createTime);

    @Insert("insert into dates(time) values (#{time})")
    void insertDateTime(long time);

    @Select("select distinct time from dates")
    List<Long> getDates();

    @Select("select id from question where content = #{content} and gmt_create = #{gmtCreate}")
    Integer getQuestionId(String content, Long gmtCreate);

    //得到不含"无"的标签
    @Select("select * from tag where tag_Name != '无'")
    List<Tag> getTags();

    //根据tagId找出所有的question集合
    @Select("select quest_id from questions_and_tags where tag_id = #{tagId}")
    List<Integer> getQuestionByTagId(Integer tagId);

    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "id",property = "patient",one = @One(select = "com.shenxi.psych.mapper.PatientMapper.getStuByQuesId",fetchType= FetchType.DEFAULT)),
            @Result(column = "id",property = "askAndAnsList",many = @Many(select = "com.shenxi.psych.mapper.QuestionMapper.getAskAndAns",fetchType=FetchType.DEFAULT)),
            @Result(column = "id",property = "questionAndTags",many = @Many(select = "com.shenxi.psych.mapper.QuestionMapper.getQuestionAndTagByQid",fetchType=FetchType.DEFAULT)),
    })
    @Select("select * from question where gmt_create > #{startTime} and gmt_create < #{endTime}")
    List<Question> getQuestionByDate(long startTime,long endTime);

    @Update("update question set view_count = view_count + #{viewCount} , gmt_modified = #{updateTime} where id = #{id}")
    void insertViewCount(Integer id, int viewCount, long updateTime);

    @Select("select count(*) from question")
    Integer getQuesCount();

    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "id",property = "patient",one = @One(select = "com.shenxi.psych.mapper.PatientMapper.getStuByQuesId",fetchType= FetchType.DEFAULT)),
            @Result(column = "id",property = "askAndAnsList",many = @Many(select = "com.shenxi.psych.mapper.QuestionMapper.getAskAndAns",fetchType=FetchType.DEFAULT)),
            @Result(column = "id",property = "questionAndTags",many = @Many(select = "com.shenxi.psych.mapper.QuestionMapper.getQuestionAndTagByQid",fetchType=FetchType.DEFAULT)),
    })
    @Select("select * from question order by gmt_create DESC limit #{begin},#{end}")
    List<Question> getQuestions(int begin, int end);

    @Update("update question set status = 1,gmt_modified = #{updateTime} where id = #{questionId}")
    void setQuesStatus(Integer questionId,Long updateTime);

    @Select("select distinct doctor_id from ask_and_answer where quest_id = #{questionId}")
    Integer isDoctorIdEmpty(Integer questionId);

    //如果该问题在中间表存在，但doctor_id为空，则更新中间表
    @Update("update ask_and_answer set doctor_id = #{doctor.id},answer = #{answer}, gmt_modified = #{gmtModified} where quest_id = #{questId}")
    void updateAskAndAns(AskAndAnswer askAndAns);

    //如果该问题在中间表存在，但doctor_id不空，则插入到中间表
    @Insert("insert into ask_and_answer (quest_id, stu_id, doctor_id, answer,gmt_create, gmt_modified) values (#{questId}, #{patient.id}, #{doctor.id}, #{answer},#{gmtCreate}, #{gmtModified})")
    void insertAskAndAns(AskAndAnswer askAndAns);

    @Insert("insert into questions_and_tags(quest_id, tag_id) values (#{questId}, #{tagId})")
    void insertQuesWithTag(Integer questId, Integer tagId);

    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "id",property = "patient",one = @One(select = "com.shenxi.psych.mapper.PatientMapper.getStuByQuesId",fetchType= FetchType.DEFAULT)),
            @Result(column = "id",property = "askAndAnsList",many = @Many(select = "com.shenxi.psych.mapper.QuestionMapper.getAskAndAns",fetchType=FetchType.DEFAULT)),
            @Result(column = "id",property = "questionAndTags",many = @Many(select = "com.shenxi.psych.mapper.QuestionMapper.getQuestionAndTagByQid",fetchType=FetchType.DEFAULT)),
    })
    @Select("SELECT * FROM question WHERE id IN (SELECT a.quest_id FROM ask_and_answer a WHERE a.doctor_id = #{doctorId})")
    List<Question> getQuestionByDoctorId(Integer doctorId);
}
